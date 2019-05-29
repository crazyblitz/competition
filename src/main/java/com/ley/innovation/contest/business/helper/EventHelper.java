package com.ley.innovation.contest.business.helper;

import com.ley.innovation.contest.business.entity.Carousel;
import com.ley.innovation.contest.business.entity.Event;
import com.ley.innovation.contest.business.service.CarouselService;
import com.ley.innovation.contest.business.service.EventService;
import com.ley.innovation.contest.utils.ContentUtils;
import com.ley.innovation.contest.utils.FileDirectoryConstant;
import com.ley.innovation.contest.utils.FileUploadUtils;
import com.ley.innovation.contest.utils.FileUtils;
import com.ley.springboot.base.utils.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 赛事更新或者插入的辅助类
 *
 * @author ley
 **/
@Slf4j
public class EventHelper {

    /**
     * 赛事更新或者插入响应封装
     **/
    public static ResponseMessage<Event> eventUpdateResult(int eventResult, int carouselResult, Event event) {
        if (eventResult == 1 || carouselResult == 1) {
            return new ResponseMessage<>(String.valueOf(HttpStatus.OK.value()), "插入|更新数据成功", event, true);
        } else {
            return new ResponseMessage<>(String.valueOf(HttpStatus.OK.value()), "插入|更新数据失败", null, true);
        }
    }

    /**
     * update event content
     **/
    public static void updateEventContent(String eventContent, String updateEventId,
                                          FileUploadUtils fileUploadUtils, ContentUtils contentUploadUtils,
                                          Event updateEvent, EventService eventService) throws Exception {
        //如果赛事内容修改
        if (StringUtils.hasText(eventContent)) {
            Event findOne = eventService.selectByPrimaryKey(updateEventId);
            //获取旧的赛事内容
            String eventContentFilePath = fileUploadUtils.getFilePath(findOne.getEventContentPath());

            //删除服务器上旧的赛事文章HTML文件
            boolean deleteEventContentFile = FileUtils.delFile(eventContentFilePath);
            if (deleteEventContentFile) {
                log.info("删除旧的文章: {} 成功", eventContentFilePath);
            }

            //增加新的赛事文章
            String eventContentFileName = contentUploadUtils.uploadContentHTML(UUID.randomUUID().toString(), eventContent);

            //设置新的赛事文章远程路径
            updateEvent.setEventContentPath(eventContentFileName);
        }
    }

    /**
     * update event carousel file
     **/
    public static int updateEventCarouselFile(MultipartFile carouselFile, String updateEventId,
                                              FileUploadUtils fileUploadUtils, CarouselService carouselService) throws Exception {
        int updateCarouselResult = 0;
        if (carouselFile != null) {
            Carousel updateCarousel = new Carousel();
            Carousel findCarousel = carouselService.getCarouselByEventId(updateEventId);
            updateCarousel.setCarouselId(findCarousel.getCarouselId());

            //删除旧的图片
            String oldCarousePath = fileUploadUtils.getFilePath(findCarousel.getCarouselPath());
            log.info("oldCarousePath: {}",oldCarousePath);
            FileUtils.delFile(oldCarousePath);

            //上传轮播图图片
            String uploadCarouseName = fileUploadUtils.upload(carouselFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
            updateCarousel.setCarouselPath(uploadCarouseName);
            updateCarouselResult = carouselService.updateByPrimaryKeySelective(updateCarousel); //进行修改赛事轮播图
        }
        return updateCarouselResult;
    }

}
