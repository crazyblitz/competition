package com.ley.innovation.contest.business.helper;

import com.ley.innovation.contest.business.bo.InformationBO;
import com.ley.innovation.contest.business.entity.Information;
import com.ley.innovation.contest.utils.*;
import com.ley.springboot.base.utils.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * information helper
 **/
@Slf4j
public class InformationHelper {


    /**
     * information update {@link InformationType#INFO_IMPORTANT_TYPE} helper
     *
     * @see InformationType#INFO_IMPORTANT_TYPE
     **/
    public static void updateInfo2Helper(Information information, Information findOne, MultipartFile infoImgBigFile, MultipartFile infoImgSmallFile,
                                         FileUploadUtils fileUploadUtils) {
        if (InformationType.INFO_IMPORTANT_TYPE == information.getInfoType()) {
            if (infoImgBigFile != null) {

                //删除旧的图片
                String oldInfoBigImgPath = fileUploadUtils.getFilePath(findOne.getInfoImgBig());
                boolean resultOldBigImg = FileUtils.delFile(oldInfoBigImgPath);
                if (resultOldBigImg) {
                    log.info("删除旧的赛事资讯大图片: {} 成功", oldInfoBigImgPath);
                }

                String infoBigImgPath = fileUploadUtils.upload(infoImgBigFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                information.setInfoImgBig(infoBigImgPath);
                log.info("update info big img path: {}", fileUploadUtils.getFilePath(FileDirectoryConstant.INNOVATION_CONTEST_IMG));
            }
            if (infoImgSmallFile != null) {

                //删除旧的图片
                String oldInfoSmallImgPath = fileUploadUtils.getFilePath(findOne.getInfoImgSmall());
                boolean resultOldSmallImg = FileUtils.delFile(oldInfoSmallImgPath);
                if (resultOldSmallImg) {
                    log.info("删除旧的赛事资讯小图片: {} 成功", oldInfoSmallImgPath);
                }


                String infoSmallImgPath = fileUploadUtils.upload(infoImgSmallFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                information.setInfoImgSmall(infoSmallImgPath);
                log.info("update info small img path: {}", fileUploadUtils.getFilePath(FileDirectoryConstant.INNOVATION_CONTEST_IMG));
            }
        }
    }


    public static void updateInfoContent(InformationBO informationSave, Information findOne, FileUploadUtils fileUploadUtils, ContentUtils contentUtils,
                                         Information information) throws IOException {
        if (StringUtils.hasText(informationSave.getInfoContent())) {
            String oldInfoContentPath = fileUploadUtils.getFilePath(findOne.getInfoContentPath());
            boolean resultOldContentHtml = FileUtils.delFile(oldInfoContentPath);
            if (resultOldContentHtml) {
                log.info("删除旧的内容HTML: {} 成功", oldInfoContentPath);
            }

            String infoContentFileName = contentUtils.uploadContentHTML(UUID.randomUUID().toString(), informationSave.getInfoContent());
            information.setInfoContentPath(infoContentFileName);
        }

    }


    /**
     * information update {@link InformationType#INFO_DOWNLOAD} helper
     *
     * @see InformationType#INFO_DOWNLOAD
     **/
    public static void updateInfo4Helper(Information information, Information findOne, MultipartFile resourceFile,
                                         FileUploadUtils fileUploadUtils) {
        if (InformationType.INFO_DOWNLOAD == information.getInfoType()) {
            if (resourceFile != null) {
                //删除旧的图片
                String oldResourceFilePath = fileUploadUtils.getFilePath(findOne.getInfoResourcePath());
                boolean resultOldResourceFile = FileUtils.delFile(oldResourceFilePath);
                if (resultOldResourceFile) {
                    log.info("删除旧的资源文件: {} 成功", oldResourceFilePath);
                }

                String resourceFilePath = fileUploadUtils.upload(resourceFile, FileDirectoryConstant.INNOVATION_CONTEST_DOC);
                information.setInfoImgBig(resourceFilePath);
                log.info("update info resource file path: {}", fileUploadUtils.getFilePath(FileDirectoryConstant.INNOVATION_CONTEST_DOC));
            }
        }
    }


    /**
     * information insert {@link InformationType#INFO_IMPORTANT_TYPE} helper
     *
     * @see InformationType#INFO_IMPORTANT_TYPE
     **/
    public static void insertInfo2Helper(Information information, MultipartFile infoImgBigFile, MultipartFile infoImgSmallFile,
                                         FileUploadUtils fileUploadUtils) {
        if (InformationType.INFO_IMPORTANT_TYPE == information.getInfoType()) {
            if (infoImgBigFile != null) {
                String infoBigImgPath = fileUploadUtils.upload(infoImgBigFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                information.setInfoImgBig(infoBigImgPath);
                log.info("info big img path: {}", fileUploadUtils.getFilePath(FileDirectoryConstant.INNOVATION_CONTEST_IMG));
            }
            //插入赛事资讯小图片
            if (infoImgSmallFile != null) {
                String infoSmallImgPath = fileUploadUtils.upload(infoImgSmallFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                information.setInfoImgSmall(infoSmallImgPath);
                log.info("info small img path: {}", fileUploadUtils.getFilePath(FileDirectoryConstant.INNOVATION_CONTEST_IMG));
            }
        }
    }


    /**
     * information insert {@link InformationType#INFO_DOWNLOAD} helper
     *
     * @see InformationType#INFO_DOWNLOAD
     **/
    public static void insertInfo4Helper(Information information, MultipartFile resourceFile, FileUploadUtils fileUploadUtils) {
        if (InformationType.INFO_DOWNLOAD == information.getInfoType()) {
            if (resourceFile != null) {
                String resourceFilePath = fileUploadUtils.upload(resourceFile, FileDirectoryConstant.INNOVATION_CONTEST_DOC);
                information.setInfoResourcePath(resourceFilePath);
                log.info("info resource file path: {}", fileUploadUtils.getFilePath(FileDirectoryConstant.INNOVATION_CONTEST_DOC));
            }
        }
    }


    /**
     * update information or create information helper
     **/
    public static ResponseMessage<Boolean> updateOrCreateResultHelper(int result) {
        if (result == 1) {
            return new ResponseMessage<>(String.valueOf(HttpStatus.OK.value()), "插入|更新 数据成功",
                    true, true);
        } else {
            return new ResponseMessage<>(String.valueOf(HttpStatus.OK.value()), "插入|更新 数据失败",
                    false, true);
        }
    }


}
