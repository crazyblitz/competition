package com.ley.innovation.contest.business.helper;

import com.ley.innovation.contest.business.entity.Website;
import com.ley.innovation.contest.utils.FileDirectoryConstant;
import com.ley.innovation.contest.utils.FileUploadUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站配置helper
 *
 * @author ley
 **/
public class WebsiteHelper {

    /**
     * 大赛简介图片配置辅助方法
     **/
    public static void insertOrUpdateImgEvent(MultipartFile eventSynopsisBkImg, FileUploadUtils fileUploadUtils, Website website) {
        //大赛简介背景图片
        if (eventSynopsisBkImg != null) {
            String eventSynopsisBkImgPath = fileUploadUtils.upload(eventSynopsisBkImg, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
            website.setEventSynopsisBkImg(eventSynopsisBkImgPath);
        }
    }


    /**
     * 承办单位图片配置辅助方法
     **/
    public static void insertOrUpdateImgCommittee(MultipartFile websiteBkImg2,
                                                  FileUploadUtils fileUploadUtils, Website website) {
        //承办方背景图片
        if (websiteBkImg2 != null) {
            String websiteBkImg2Path = fileUploadUtils.upload(websiteBkImg2, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
            website.setEventSynopsisBkImg(websiteBkImg2Path);
        }
    }

}
