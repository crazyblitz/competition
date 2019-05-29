package com.ley.innovation.contest.business.controller;


import java.util.HashMap;
import java.util.List;

import com.ley.innovation.contest.business.bo.WebsiteCommitteeBO;
import com.ley.innovation.contest.business.bo.WebsiteEventBO;
import com.ley.innovation.contest.business.bo.WebsiteProcessBO;
import com.ley.innovation.contest.business.helper.WebsiteHelper;
import com.ley.innovation.contest.utils.*;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Website;
import com.ley.innovation.contest.business.page.WebsitePage;
import com.ley.innovation.contest.business.service.WebsiteService;
import org.springframework.util.CollectionUtils;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * <b>功能：</b>WebsiteController<br>
 **/
@RestController
@RequestMapping("/api/business/websites")
@Api(description = "|Website|")
@Slf4j
public class WebsiteController extends BaseController<Website> {


    @Autowired
    private WebsiteService websiteService;


    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Autowired
    private BeanMapper beanMapper;


    @ApiOperation(value = "|Website|查询")
    @GetMapping("/{configType}")
    public ResponseMessage<Website> list(@ApiParam(value = "configType=1,网站基本配置;configType=2,大赛简介配置;" +
            "configType=3,承办单位配置;configType=4,赛事流程简介配置") @PathVariable String configType) throws Exception {
        WebsitePage page = new WebsitePage();
        page.setPageSize(1000);
        List<Website> websites = websiteService.queryByList(page);
        Website website = null;
        if (!CollectionUtils.isEmpty(websites)) {
            for (Website tmp : websites) {
                if (tmp.getConfigType() == Integer.valueOf(configType)) {
                    website = tmp;
                }
            }

            //判断网站配置是否返回成功
            if (website != null) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "返回网站配置成功",
                        website);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "返回网站配置失败",
                        null);
            }
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的网站配置",
                    null);
        }
    }


    @ApiOperation(value = "|Website|网站基本配置新增或者修改")
    @PostMapping("/website")
    public ResponseMessage<Website> createOrUpdate1(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "logoFile", required = false) MultipartFile logoFile,
                                                    @RequestParam(value = "weiXinFile", required = false) MultipartFile weiXinFile) throws Exception {
        if (!StringUtils.hasText(isInsertOrUpdateWebsite())) {
            Website insertWebsite = new Website();
            String websiteId = UUID.randomUUID().toString();
            insertWebsite.setConfigType(1);
            insertWebsite.setWebsiteId(websiteId);
            insertWebsite.setWebsiteTitle(title);
            String logoFilePath = fileUploadUtils.upload(logoFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
            insertWebsite.setWebsiteLogo(logoFilePath);
            String weiXinFilePath = fileUploadUtils.upload(weiXinFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
            insertWebsite.setWebsiteWeixinImg(weiXinFilePath);
            int result = websiteService.insertSelective(insertWebsite);
            if (result == 1) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据成功",
                        insertWebsite);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据失败",
                        insertWebsite);
            }
        } else {
            Website realUpdateWebsite = new Website();
            //获取要更新的网站基础配置websiteId
            realUpdateWebsite.setWebsiteId(isInsertOrUpdateWebsite());
            if (StringUtils.hasText(title)) {
                realUpdateWebsite.setWebsiteTitle(title);
            }
            if (logoFile != null) {
                String logoFilePath = fileUploadUtils.upload(logoFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                realUpdateWebsite.setWebsiteLogo(logoFilePath);
            }
            if (weiXinFile != null) {
                String weiXinFilePath = fileUploadUtils.upload(weiXinFile, FileDirectoryConstant.INNOVATION_CONTEST_IMG);
                realUpdateWebsite.setWebsiteWeixinImg(weiXinFilePath);
            }
            int result = websiteService.updateByPrimaryKeySelective(realUpdateWebsite);
            if (result == 1) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据成功",
                        realUpdateWebsite);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据失败",
                        null);
            }
        }
    }


    @ApiOperation(value = "|Website|网站承办单位和大赛简介配置新增或者修改")
    @PostMapping("/website2")
    public ResponseMessage<Boolean> createOrUpdate2(WebsiteEventBO websiteEventBO, WebsiteCommitteeBO websiteCommitteeBO,
                                                    WebsiteProcessBO websiteProcessBO, @ApiParam("大赛简介配置背景图") @RequestParam(value = "eventSynopsisBkImg", required = false) MultipartFile eventSynopsisBkImg,
                                                    @ApiParam("承办方配置背景图") @RequestParam(value = "websiteBkImg3", required = false) MultipartFile websiteBkImg2) throws Exception {

        Map<Integer, String> map = isInsertOrUpdateWebsite2();
        if (!CollectionUtils.isEmpty(map)) {

            int result = 0;

            //更新大赛简介网站配置
            if (StringUtils.hasText(websiteEventBO.getEventSynopsisDesc1()) || StringUtils.hasText(websiteEventBO.getEventSynopsisDesc2())) {
                Website websiteEvent = beanMapper.map(websiteEventBO, Website.class);
                websiteEvent.setWebsiteId(map.get(WebsiteConfigType.WEBSITE_EVENT_SIMPLE_CONFIG));
                WebsiteHelper.insertOrUpdateImgEvent(eventSynopsisBkImg, fileUploadUtils, websiteEvent);
                result += websiteService.updateByPrimaryKeySelective(websiteEvent);
            }

            //更新赛事流程网站配置
            if (StringUtils.hasText(websiteProcessBO.getProcessDesc())) {
                Website websiteProcess = beanMapper.map(websiteProcessBO, Website.class);
                websiteProcess.setWebsiteId(map.get(WebsiteConfigType.WEBSITE_PROCESS_CONFIG));
                result += websiteService.updateByPrimaryKeySelective(websiteProcess);
            }


            //更新承办方网站配置
            if (StringUtils.hasText(websiteCommitteeBO.getCommittee()) ||
                    StringUtils.hasText(websiteCommitteeBO.getOrganizer()) ||
                    StringUtils.hasText(websiteCommitteeBO.getHostUnit())) {
                Website websiteCommittee = beanMapper.map(websiteCommitteeBO, Website.class);
                websiteCommittee.setWebsiteId(map.get(WebsiteConfigType.WEBSITE_COMMITTEE_CONFIG));
                WebsiteHelper.insertOrUpdateImgCommittee(websiteBkImg2, fileUploadUtils, websiteCommittee);
                result += websiteService.updateByPrimaryKeySelective(websiteCommittee);
            }


            //更新结果
            if (result != 0) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据成功",
                        true);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据失败",
                        false);
            }
        } else {

            //大赛简介配置
            Website eventWebsite = beanMapper.map(websiteEventBO, Website.class);
            eventWebsite.setWebsiteId(UUID.randomUUID().toString());
            eventWebsite.setConfigType(WebsiteConfigType.WEBSITE_EVENT_SIMPLE_CONFIG);
            WebsiteHelper.insertOrUpdateImgEvent(eventSynopsisBkImg, fileUploadUtils, eventWebsite);
            int result1 = websiteService.insertSelective(eventWebsite);


            //承办单位配置
            Website committeeWebsite = beanMapper.map(websiteCommitteeBO, Website.class);
            committeeWebsite.setWebsiteId(UUID.randomUUID().toString());
            committeeWebsite.setConfigType(WebsiteConfigType.WEBSITE_COMMITTEE_CONFIG);
            WebsiteHelper.insertOrUpdateImgCommittee(websiteBkImg2, fileUploadUtils, committeeWebsite);
            int result2 = websiteService.insertSelective(committeeWebsite);


            //赛事流程配置
            Website processWebsite = beanMapper.map(websiteProcessBO, Website.class);
            processWebsite.setWebsiteId(UUID.randomUUID().toString());
            processWebsite.setConfigType(WebsiteConfigType.WEBSITE_PROCESS_CONFIG);
            int result3 = websiteService.insertSelective(processWebsite);


            //更新结果
            if (result1 == 1 || result2 == 1 || result3 == 1) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据成功",
                        true);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据失败",
                        false);
            }
        }
    }


    /**
     * 判断是否是更新承办单位或者大赛简介或者赛事流程
     **/
    private Map<Integer, String> isInsertOrUpdateWebsite2() throws Exception {
        Map<Integer, String> map = new HashMap<>(3);
        //判断是否需要更新大赛简介
        Website eventWebsite = isInsertOrUpdateWebsite2Helper("2");
        if (eventWebsite != null) {
            map.put(WebsiteConfigType.WEBSITE_EVENT_SIMPLE_CONFIG, eventWebsite.getWebsiteId());
        }

        //判断是否需要更新承办单位
        Website committeeWebsite = isInsertOrUpdateWebsite2Helper("3");
        if (committeeWebsite != null) {
            map.put(WebsiteConfigType.WEBSITE_COMMITTEE_CONFIG, committeeWebsite.getWebsiteId());
        }

        //判断是否需要更新赛事流程
        Website processWebsite = isInsertOrUpdateWebsite2Helper("4");
        if (processWebsite != null) {
            map.put(WebsiteConfigType.WEBSITE_PROCESS_CONFIG, processWebsite.getWebsiteId());
        }
        return map;
    }

    /**
     * 判断是否是更新承办单位或者大赛简介或者赛事流程辅助方法
     **/
    private Website isInsertOrUpdateWebsite2Helper(String config) throws Exception {
        WebsitePage page = new WebsitePage();
        page.setPageSize(1);
        page.setConfigType(config);
        Website website = websiteService.queryBySingle(page);
        return website;
    }


    /**
     * insert:websiteId==null;update:websiteId!=null
     **/
    private String isInsertOrUpdateWebsite() throws Exception {
        WebsitePage page = new WebsitePage();
        page.setPageSize(1);
        page.setConfigType("1");
        Website baseWebsite = websiteService.queryBySingle(page);
        if (baseWebsite != null) {
            return baseWebsite.getWebsiteId();
        } else {
            return null;
        }
    }


//    @ApiOperation(value = "|Website|修改")
//    @PutMapping("/website")
//    public ResponseMessage<Website> update(Website website) throws Exception {
//        int result = websiteService.updateByPrimaryKeySelective(website);
//        if (result == 1) {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据成功",
//                    website);
//        } else {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据失败",
//                    website);
//        }
//    }


//    @ApiOperation(value = "|Website|删除")
//    @DeleteMapping("/website/{websiteId}")
//    public ResponseMessage<Boolean> delete(@PathVariable String websiteId) throws Exception {
//        int result=websiteService.deleteByPrimaryKey(websiteId);
//        log.info("delete from TB_WEBSITE where websiteId = {}", websiteId);
//        return result==1?Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据成功",
//            true):Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据失败",
//            false);
//    }
}
