package com.ley.innovation.contest.business.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ley.innovation.contest.business.bo.InformationBO;
import com.ley.innovation.contest.business.helper.InformationHelper;
import com.ley.innovation.contest.business.vo.InfoTypeVO;
import com.ley.innovation.contest.business.vo.InformationVO;
import com.ley.innovation.contest.utils.*;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Information;
import com.ley.innovation.contest.business.page.InformationPage;
import com.ley.innovation.contest.business.service.InformationService;
import org.springframework.util.CollectionUtils;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import com.ley.springboot.base.page.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.lang.Boolean;
import java.util.UUID;

/**
 * <b>功能：</b>InformationController<br>
 **/
@RestController
@RequestMapping("/api/business/informations")
@Api(description = "|Information|")
@Slf4j
public class InformationController extends BaseController<Information> {


    @Autowired
    private InformationService informationService;


    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Autowired
    private ContentUtils contentUtils;


    @ApiOperation(value = "|Information|分页查询(默认第一页,每页20条)")
    @GetMapping("/page/{flag}")
    public ResponseMessage<PageInfo<Information>> page(InformationPage page, @ApiParam(value = "flag=1,前台查询;flag=2,后台查询") @PathVariable String flag) throws Exception {
        List<Information> rows = null;
        if ("1".equals(flag)) {
            //发布时间<=当前时间
            page.setInfoReleaseTime2(DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));

            //发布时间降序排序
            page.setOrder("DESC");
            page.setOrderBy("INFO_RELEASE_TIME");

            //doc下载需要根据赛事来分组
            if (StringUtils.hasText(page.getEventId()) && String.valueOf(InformationType.INFO_DOWNLOAD).equals(page.getInfoType())) {
                //do nothing
                
            } else {
                //其他文章类型不需要根据赛事来分组
                page.setEventId(null);
            }
            rows = informationService.queryByPage(page);

        }

        if ("2".equals(flag)) {

            //发布时间降序排序
            page.setOrder("DESC");
            page.setOrderBy("INFO_RELEASE_TIME");

            //当infoType=0时,返回全部的内容管理
            if ("0".equals(page.getInfoType())) {
                page.setInfoType(null);
                rows = informationService.queryByPage(page);
            } else {
                rows = informationService.queryByPage(page);
            }
        }

        if (!CollectionUtils.isEmpty(rows)) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回分页数据成功",
                    getPageInfo(page.getPager(), rows));
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可分页的数据",
                    null);
        }
    }


    @ApiOperation(value = "|Information|详情")
    @GetMapping("/information/{infoId}/{flag}")
    public ResponseMessage<InformationVO> find(@PathVariable String infoId, @ApiParam(value = "flag=1,前台查询;flag=2,后台查询") @PathVariable String flag) throws Exception {
        Information findOne = informationService.selectByPrimaryKey(infoId);
        InformationVO informationVO = null;
        //前台查询
        if ("1".equals(flag)) {
            //防止空指针异常
            int readCount = ReadCountUtils.getInfoReadCount(infoId, findOne);
            findOne.setInfoReadCount(readCount);
            informationVO = beanMapper.map(findOne, InformationVO.class);
        }
        if ("2".equals(flag)) {
            String infoContentPath = findOne.getInfoContentPath();
            informationVO = beanMapper.map(findOne, InformationVO.class);
            //设置赛事资讯内容
            if (StringUtils.hasText(infoContentPath)) {
                String infoContentFilePath = fileUploadUtils.getFilePath(infoContentPath);
                String infoContent = new String(FileUtils.readFile(infoContentFilePath), "UTF-8");
                log.info("infoContentFilePath: {}", infoContentFilePath);
                informationVO.setInfoContent(infoContent);
            }
        }

        if (findOne != null) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回单条数据成功",
                    informationVO);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的单条数据",
                    null);
        }
    }


    @ApiOperation("判断信息类型是否可用")
    @GetMapping("/listInfoTypeVos")
    public ResponseMessage<List<InfoTypeVO>> listInfoTypeVos() throws Exception {
        InformationPage page = new InformationPage();
        List<InfoTypeVO> infoTypeVOS = new ArrayList<>(4);
        infoTypeVOS.add(new InfoTypeVO(InformationType.INFO_IMPORTANT_TYPE, true, InfoName.INFO_TYPE_EVENT));
        infoTypeVOS.add(new InfoTypeVO(InformationType.INFO_DOWNLOAD, true, InfoName.INFO_TYPE_DOWNLOAD));

        //判断关于大赛详解是否可用
        page.setInfoType("1");
        List<Information> informationList = informationService.queryByList(page);
        if (CollectionUtils.isEmpty(informationList)) {
            infoTypeVOS.add(new InfoTypeVO(InformationType.INFO_ABOUT_EVENT, true, InfoName.INFO_TYPE_ABOUT_EVENT));
        } else {
            infoTypeVOS.add(new InfoTypeVO(InformationType.INFO_ABOUT_EVENT, false, InfoName.INFO_TYPE_ABOUT_EVENT));
        }


        return Result.success(String.valueOf(HttpStatus.OK.value()), "返回数据成功", infoTypeVOS);
    }


    @ApiOperation(value = "|Information|新增或者修改赛事资讯")
    @PostMapping(value = "/information")
    public ResponseMessage<Boolean> create(InformationBO informationSave, @RequestParam(value = "infoImgBigFile", required = false) MultipartFile infoImgBigFile,
                                           @RequestParam(value = "infoImgSmallFile", required = false) MultipartFile infoImgSmallFile,
                                           @RequestParam(value = "resourceFile", required = false) MultipartFile resourceFile) throws Exception {
        //如果传入infoId,则为更新
        if (StringUtils.hasText(informationSave.getInfoId())) {


            Information information = beanMapper.map(informationSave, Information.class);
            Information findOne = informationService.selectByPrimaryKey(information.getInfoId());

            //发布时间插入
            if (StringUtils.hasText(informationSave.getInfoReleaseTimeLong())) {
                information.setInfoReleaseTime(new Date(Long.valueOf(informationSave.getInfoReleaseTimeLong())));
            } else {
                //否则发布时间为当前时间
                information.setInfoReleaseTime(new Date(System.currentTimeMillis()));
            }


            //如果内容类型是赛事资讯 infoType=2
            InformationHelper.updateInfo2Helper(information, findOne, infoImgBigFile, infoImgSmallFile, fileUploadUtils);

            //如果内容类型是资源下载,infoType=4
            InformationHelper.updateInfo4Helper(information, findOne, resourceFile, fileUploadUtils);

            //如果内容类型是1,2;均更改内容
            InformationHelper.updateInfoContent(informationSave, findOne, fileUploadUtils, contentUtils, information);

            int result = informationService.updateByPrimaryKeySelective(information);
            return InformationHelper.updateOrCreateResultHelper(result);

        } else {

            Information information = beanMapper.map(informationSave, Information.class);
            information.setInfoId(UUID.randomUUID().toString());
            information.setInfoReadCount(0);
            log.info("information save: {}", information);

            //发布时间更新
            if (StringUtils.hasText(informationSave.getInfoReleaseTimeLong())) {
                information.setInfoReleaseTime(new Date(Long.valueOf(informationSave.getInfoReleaseTimeLong())));
            }


            //赛事资讯文件(当infoType=2)
            //插入赛事资讯大图片
            InformationHelper.insertInfo2Helper(information, infoImgBigFile, infoImgSmallFile, fileUploadUtils);


            //当赛事类型=1,2;均有写入内容
            if (StringUtils.hasText(informationSave.getInfoContent())) {
                String infoContentFileName = contentUtils.uploadContentHTML(UUID.randomUUID().toString(), informationSave.getInfoContent());
                information.setInfoContentPath(infoContentFileName);
            }


            //资源下载的文件上传(当infoType=4)
            //上传doc文件
            InformationHelper.insertInfo4Helper(information, resourceFile, fileUploadUtils);

            int result = informationService.insertSelective(information);
            return InformationHelper.updateOrCreateResultHelper(result);
        }
    }


    @ApiOperation(value = "|Information|删除")
    @DeleteMapping("/information/{infoId}")
    public ResponseMessage<Boolean> delete(@PathVariable String infoId) throws Exception {
        int result = informationService.deleteByPrimaryKey(infoId);
        log.info("delete from TB_INFORMATION where infoId = {}", infoId);
        return result == 1 ? Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据成功",
                true) : Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据失败",
                false);
    }
}
