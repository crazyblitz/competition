package com.ley.innovation.contest.business.controller;


import java.util.*;

import com.ley.innovation.contest.business.entity.Carousel;
import com.ley.innovation.contest.business.helper.EventHelper;
import com.ley.innovation.contest.business.page.CarouselPage;
import com.ley.innovation.contest.business.page.EventPage;
import com.ley.innovation.contest.business.page.UserPage;
import com.ley.innovation.contest.business.service.CarouselService;
import com.ley.innovation.contest.business.service.UserService;
import com.ley.innovation.contest.business.vo.EventVO;
import com.ley.innovation.contest.utils.*;
import com.ley.springboot.base.page.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Event;
import com.ley.innovation.contest.business.service.EventService;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.lang.Boolean;

/**
 * <b>功能：</b>EventController<br>
 **/
@RestController
@RequestMapping("/api/business/events")
@Api(description = "|Event|")
@Slf4j
public class EventController extends BaseController<Event> {


    @Autowired
    private EventService eventService;

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private FileUploadUtils fileUploadUtils;


    @Autowired
    private ContentUtils contentUploadUtils;

    @Autowired
    private UserService userService;


    /**
     * 获取所有的大赛附带分页
     **/
    @ApiOperation(value = "|Event|分页查询(默认第一页,每页20条)")
    @GetMapping("/page")
    public ResponseMessage<PageInfo<EventVO>> page(EventPage page) throws Exception {
        List<EventVO> eventVOS = eventService.listEventVOs(page);
        List<Event> rows = eventService.queryByPage(page);
        PageInfo<EventVO> eventVOPageInfo = beanMapper.mapPage(getPageInfo(page.getPager(), rows), EventVO.class);

        //获取每个赛事的报名学生
        for (EventVO eventVO : eventVOS) {
            UserPage userPage = new UserPage();
            userPage.setEventId(eventVO.getEventId());
            eventVO.setUsers(userService.queryByList(userPage));
        }

        eventVOPageInfo.setList(eventVOS);
        //在没有异常的情况下
        if (!CollectionUtils.isEmpty(eventVOS)) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回分页数据成功",
                    eventVOPageInfo);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可分页的数据",
                    null);
        }
    }

    /**
     * 获取所有已启用的大赛
     **/
    @ApiOperation(value = "|Event|查询")
    @GetMapping("/enabledEvents")
    public ResponseMessage<List<EventVO>> list(EventPage page) throws Exception {
        page.setPageSize(50);
        page.setPage(1);
        //赛事启用
        page.setEventEnable("1");
        //赛事发布<=当前时间
        page.setEventReleaseTime2(DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
        List<EventVO> eventVOS = eventService.listEventVOs(page);
        if (!CollectionUtils.isEmpty(eventVOS)) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回数据集合成功",
                    eventVOS);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的数据集合",
                    null);
        }
    }


    /**
     * 查看赛事详情
     *
     * @param flag flag=1,前台查询赛事详情;flag=2,后台查询赛事详情
     **/
    @ApiOperation(value = "|Event|查看赛事详情")
    @GetMapping("/event/{eventId}/{flag}")
    public ResponseMessage<EventVO> find(@PathVariable String eventId, @ApiParam(name = "flag", value = "flag=2,后台查询;flag=1,前台查询;") @PathVariable String flag) throws Exception {
        Event event = eventService.selectByPrimaryKey(eventId);
        Carousel carousel = carouselService.getCarouselByEventId(eventId);
        EventVO eventVO = null;
        if (event != null) {
            //更新阅读量
            Event readCountEvent = new Event();
            readCountEvent.setEventId(eventId);
            if ("1".equals(flag) && event.getEventEnable() == 1) {//前台查询
                int readCount = ReadCountUtils.getEventReadCount(eventId, event);
                event.setEventReadCount(readCount);
                eventVO = beanMapper.map(event, EventVO.class);
            }
            if ("2".equals(flag)) {
                //设置赛事内容到eventVO
                String eventContentPath = event.getEventContentPath();
                eventVO = beanMapper.map(event, EventVO.class);
                if (StringUtils.hasText(eventContentPath)) {
                    String eventContentFilePath = fileUploadUtils.getFilePath(eventContentPath);
                    String eventContent = new String(FileUtils.readFile(eventContentFilePath), "UTF-8");
                    log.info("eventContentFilePath: {}", eventContentFilePath);
                    eventVO.setEventContent(eventContent);
                }
            }

            eventVO.setCarouselPath(carousel.getCarouselPath());
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回单条数据成功",
                    eventVO);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的单条数据",
                    null);
        }
    }


    /**
     * @param updateEventId 更新的eventId,当需要更新赛事时,需要传入该参数
     **/
    @ApiOperation(value = "|Event|新增或者修改赛事")
    @PostMapping("/event")
    public ResponseMessage<Event> create(@RequestParam(value = "eventTitle", required = false) String eventTitle, @RequestParam(value = "eventContent", required = false) String eventContent,
                                         @RequestParam(value = "eventEnable", required = false) Integer eventEnable, @RequestParam(value = "releaseTime", required = false) String releaseTime,
                                         @ApiParam(value = "更新赛事需要传入") @RequestParam(value = "updateEventId", required = false) String updateEventId,
                                         @RequestParam(value = "carouselFile", required = false) MultipartFile carouselFile) throws Exception {
        //如果updateEventId有值,这为更新
        if (StringUtils.hasText(updateEventId)) {
            Event updateEvent = new Event();
            updateEvent.setEventId(updateEventId);
            if (StringUtils.hasText(eventTitle)) {
                updateEvent.setEventTitle(eventTitle);
            }
            if (StringUtils.hasText(releaseTime)) {
                updateEvent.setEventReleaseTime(new Date(Long.parseLong(releaseTime)));
            }
            if (eventEnable != null) {
                updateEvent.setEventEnable(eventEnable);
            }

            //如果赛事内容修改
            EventHelper.updateEventContent(eventContent, updateEventId, fileUploadUtils, contentUploadUtils, updateEvent,
                    eventService);

            int updateCarouselResult = 0;
            int updateEventResult = 0;

            //如果赛事轮播图修改
            updateCarouselResult = EventHelper.updateEventCarouselFile(carouselFile, updateEventId, fileUploadUtils, carouselService);


            //判断是否需要修改赛事
            if (updateEvent.getEventEnable() != null || updateEvent.getEventTitle() != null || updateEvent.getEventReleaseTime() != null
                    || updateEvent.getEventContentPath() != null) {
                //进行修改赛事
                updateEventResult = eventService.updateByPrimaryKeySelective(updateEvent);
            }

            return EventHelper.eventUpdateResult(updateEventResult, updateCarouselResult, updateEvent);
        } else {
            Event event = new Event();
            Carousel carousel = new Carousel();
            String eventId = UUID.randomUUID().toString();
            String carouselId = UUID.randomUUID().toString();
            if (carouselFile != null) {
                String uploadCarouseName = fileUploadUtils.upload(carouselFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                //设置轮播图路径
                carousel.setCarouselPath(uploadCarouseName);
                //像这样获取带有顺序的字段,一定要从数据库获取当前最大的顺序.
                CarouselPage maxOrderPage = new CarouselPage();
                maxOrderPage.setPageSize(Integer.MAX_VALUE);
                maxOrderPage.setPageSize(1);
                //由于轮播图的开始序号从0开始
                int maxCarouselOrder = carouselService.queryByCount(maxOrderPage);
                int nextCarouselOrder = maxCarouselOrder;
                carousel.setCarouselOrder(nextCarouselOrder);
            }

            //存储赛事内容HTML文件
            if (StringUtils.hasText(eventContent)) {
                String eventContentFileName = contentUploadUtils.uploadContentHTML(UUID.randomUUID().toString(), eventContent);
                //设置远程赛事内容文件路径
                event.setEventContentPath(eventContentFileName);
            }

            //设置event对象
            event.setEventId(eventId);
            event.setCarouselId(carouselId);
            event.setEventEnable(eventEnable);
            if (StringUtils.hasText(releaseTime)) {
                event.setEventReleaseTime(new Date(Long.parseLong(releaseTime)));
            }
            event.setEventTitle(eventTitle);
            int eventResult = eventService.insertSelective(event);


            //设置carousel对象
            carousel.setCarouselId(carouselId);
            carousel.setEventId(eventId);
            int carouselResult = carouselService.insertSelective(carousel);

            return EventHelper.eventUpdateResult(eventResult, carouselResult, event);
        }
    }


    /**
     * 删除赛事和删除赛事相关联的轮播图
     **/
    @ApiOperation(value = "|Event|删除")
    @DeleteMapping("/event/{eventId}")
    public ResponseMessage<Boolean> delete(@PathVariable String eventId) throws Exception {
        int deleteEvent = eventService.deleteByPrimaryKey(eventId);
        log.info("delete from TB_EVENT where eventId = {}", eventId);

        String carouseId = carouselService.getCarouselByEventId(eventId).getCarouselId();
        int deleteCarouse = carouselService.deleteByPrimaryKey(carouseId);
        log.info("delete from TB_CAROUSEL where carouselId = {}", carouseId);
        if (deleteEvent == 1 && deleteCarouse == 1) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据成功",
                    true);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据失败",
                    true);
        }
    }
}
