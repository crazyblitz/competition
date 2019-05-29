package com.ley.innovation.contest.business.controller;


import java.util.ArrayList;
import java.util.List;

import com.ley.innovation.contest.business.entity.Event;
import com.ley.innovation.contest.business.service.EventService;
import com.ley.innovation.contest.business.vo.CarouselVO;
import com.ley.innovation.contest.utils.BeanMapper;
import com.ley.innovation.contest.utils.DateUtils;
import com.ley.innovation.contest.utils.FileDirectoryConstant;
import com.ley.innovation.contest.utils.FileUploadUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Carousel;
import com.ley.innovation.contest.business.page.CarouselPage;
import com.ley.innovation.contest.business.service.CarouselService;
import org.springframework.util.CollectionUtils;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * <b>功能：</b>CarouselController<br>
 **/
@RestController
@RequestMapping("/api/business/carousels")
@Api(description = "|Carousel|")
@Slf4j
public class CarouselController extends BaseController<Carousel> {


    @Autowired
    private CarouselService carouselService;


    /**
     * file utility class
     **/
    @Autowired
    private FileUploadUtils fileUploadUtils;

    private static List<Carousel> carouselWithOrderList;


    @Autowired
    private EventService eventService;


    @Autowired
    private BeanMapper beanMapper;


    /**
     * 获取所有的轮播图
     **/
    @ApiOperation(value = "|Carousel|查询")
    @GetMapping("/unOrdered")
    public ResponseMessage<List<CarouselVO>> listUnOrdered() throws Exception {

        //轮播图排序
        CarouselPage page = new CarouselPage();
        page.setPageSize(1000); //设置为1000
        page.setOrderBy("CAROUSEL_ORDER");
        page.setOrder("ASC");


        List<Carousel> carousels = carouselService.queryByList(page);
        List<CarouselVO> carouselVOS = new ArrayList<>();


        if (!CollectionUtils.isEmpty(carousels)) {
            for (Carousel carousel : carousels) {
                //获取已启用的赛事轮播图
                Event event = eventService.selectByPrimaryKey(carousel.getEventId());
                Long releaseTimeLong = 0L;
                if (event.getEventReleaseTime() != null && event.getEventEnable() == 1) {
                    releaseTimeLong = DateUtils.date2Long(DateFormatUtils.format(event.getEventReleaseTime(), "yyyy-MM-dd HH:mm:ss"));
                }
                Long nowTimeLong = System.currentTimeMillis();
                if (releaseTimeLong <= nowTimeLong) {
                    CarouselVO carouselVO = beanMapper.map(carousel, CarouselVO.class);
                    carouselVO.setEventTitle(event.getEventTitle());
                    carouselVOS.add(carouselVO);
                }
            }
            log.info("已启用的轮播图集合: {}", carouselVOS);
            this.carouselWithOrderList = carousels;
            return Result.success(String.valueOf(HttpStatus.OK.value()), "已启用的轮播图VO集合成功",
                    carouselVOS);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的数据集合",
                    null);
        }
    }


//    /**
//     * 获取已经排完次序的轮播图列表
//     **/
//    @ApiOperation(value = "|Carousel|查询")
//    @GetMapping("/ordered")
//    public ResponseMessage<List<Carousel>> listOrdered() throws Exception {
//        if (CollectionUtils.isEmpty(carouselWithOrderList)) {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回数据集合失败",
//                    null);
//        } else {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回数据集合成功",
//                    carouselWithOrderList);
//        }
//    }


    /**
     * 上传轮播图
     **/
//    @ApiOperation(value = "|Carousel|新增")
//    @PostMapping("/carousel")
//    public ResponseMessage<String> create(@RequestParam("carouselFile") MultipartFile file) throws Exception {
//        Carousel carousel = new Carousel();
//        carousel.setCarouselId(UUID.randomUUID().toString());
//        String uploadCarouseName = fileUploadUtils.upload(file, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
//        carousel.setCarouselPath(uploadCarouseName);
//        int result = carouselService.insertSelective(carousel);
//        if (result == 1) {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据成功",
//                    uploadCarouseName);
//        } else {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据失败",
//                    null);
//        }
//    }


    /**
     * 设置轮播图的次序,返回有次序的轮播图
     **/
    @ApiOperation(value = "|Carousel|修改")
    @PostMapping(value = "/carousel/order", produces = "application/json;charset=UTF-8")
    public ResponseMessage<List<Carousel>> updateCarouselOrder(@RequestBody List<Carousel> carouselList) throws Exception {
        if (!CollectionUtils.isEmpty(carouselList)) {
            for (Carousel carousel : carouselList) {
                carouselService.updateByPrimaryKeySelective(carousel);
            }
            //更新次序完成之后,获取更新之后的列表
            CarouselPage page = new CarouselPage();
            page.setPageSize(1000);
            page.setOrderBy("CAROUSEL_ORDER");
            page.setOrder("ASC");
            List<Carousel> carouselWithOrderList = carouselService.queryByList(page);
            return Result.success(String.valueOf(HttpStatus.OK.value()), "轮播图次序修改成功",
                    carouselWithOrderList);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "轮播图次序修改失败",
                    null);
        }
    }
}
