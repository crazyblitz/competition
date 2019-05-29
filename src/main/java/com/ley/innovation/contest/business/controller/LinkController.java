package com.ley.innovation.contest.business.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Link;
import com.ley.innovation.contest.business.page.LinkPage;
import com.ley.innovation.contest.business.service.LinkService;
import org.springframework.util.CollectionUtils;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import com.ley.springboot.base.page.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.lang.Boolean;
import java.util.UUID;

/**
 * <b>功能：</b>LinkController<br>
 **/
@RestController
@RequestMapping("/api/business/links")
@Api(description = "|Link|")
@Slf4j
public class LinkController extends BaseController<Link> {


    @Autowired
    private LinkService linkService;

//	@ApiOperation(value = "|Link|分页查询(默认第一页,每页20条)")
//    @GetMapping("/page")
//    public ResponseMessage<PageInfo<Link>> page(LinkPage page) throws Exception {
//        List<Link> rows = linkService.queryByPage(page);
//        //在没有异常的情况下
//        if (!CollectionUtils.isEmpty(rows)) {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回分页数据成功",
//                    getPageInfo(page.getPager(), rows));
//        } else {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可分页的数据",
//                    null);
//        }
//    }

    /**
     * 获取联系我们
     **/
    @ApiOperation(value = "|Link|联系我们查询")
    @GetMapping("/link1")
    public ResponseMessage<Link> link1() throws Exception {
        LinkPage page = new LinkPage();
        Link link = linkService.queryBySingle(page);
        if (link != null) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回数据成功",
                    link);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的数据",
                    null);
        }
    }


//    @ApiOperation(value = "|Link|详情")
//    @GetMapping("/link/{linkId}")
//    public ResponseMessage<Link> find(@PathVariable String linkId) throws Exception {
//        Link findOne = linkService.selectByPrimaryKey(linkId);
//        if (findOne != null) {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回单条数据成功",
//                    findOne);
//        } else {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的单条数据",
//                    null);
//        }
//    }


    @ApiOperation(value = "|Link|新增")
    @PostMapping("/link")
    public ResponseMessage<Boolean> create(Link link) throws Exception {
        LinkPage page = new LinkPage();
        Link findOne = linkService.queryBySingle(page);
        if (findOne == null) {
            link.setLinkId(UUID.randomUUID().toString());
            int result = linkService.insertSelective(link);
            if (result == 1) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据成功",
                        true);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据失败",
                        false);
            }
        } else {
            link.setLinkId(findOne.getLinkId());
            int result = linkService.updateByPrimaryKeySelective(link);
            if (result == 1) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据成功",
                        true);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据失败",
                        false);
            }
        }
    }


    @ApiOperation(value = "|Link|删除")
    @DeleteMapping("/link/{linkId}")
    public ResponseMessage<Boolean> delete(@PathVariable String linkId) throws Exception {
        int result = linkService.deleteByPrimaryKey(linkId);
        log.info("delete from TB_LINK where linkId = {}", linkId);
        return result == 1 ? Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据成功",
                true) : Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据失败",
                false);
    }
}
