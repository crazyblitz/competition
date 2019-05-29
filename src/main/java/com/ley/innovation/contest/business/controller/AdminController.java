package com.ley.innovation.contest.business.controller;


import java.io.UnsupportedEncodingException;
import java.util.List;

import com.ley.innovation.contest.utils.ByteHexUtils;
import com.ley.innovation.contest.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Admin;
import com.ley.innovation.contest.business.page.AdminPage;
import com.ley.innovation.contest.business.service.AdminService;
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
 * <b>功能：</b>AdminController<br>
 **/
@RestController
@RequestMapping("/api/business/admins")
@Api(description = "|Admin|")
@Slf4j
public class AdminController extends BaseController<Admin> {


    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "|Admin|分页查询(默认第一页,每页20条)")
    @GetMapping("/page")
    public ResponseMessage<PageInfo<Admin>> page(AdminPage page) throws Exception {
        List<Admin> rows = adminService.queryByPage(page);
        //在没有异常的情况下
        if (!CollectionUtils.isEmpty(rows)) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回分页数据成功",
                    getPageInfo(page.getPager(), rows));
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可分页的数据",
                    null);
        }
    }


    @ApiOperation(value = "|Admin|详情")
    @GetMapping("/admin/{adminId}")
    public ResponseMessage<Admin> find(@PathVariable String adminId) throws Exception {
        Admin findOne = adminService.selectByPrimaryKey(adminId);
        if (findOne != null) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回单条数据成功",
                    findOne);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的单条数据",
                    null);
        }
    }


    @ApiOperation(value = "|Admin|登录")
    @PostMapping("/login")
    public ResponseMessage<Boolean> login(Admin admin) throws Exception {
        AdminPage adminPage = new AdminPage();
        adminPage.setAdminName(admin.getAdminName());
        Admin findOne = adminService.queryBySingle(adminPage);
        if (findOne == null) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有该用户.", false);
        } else {
            //登录
            //将16进制的md5加密密码转换成二进制
            byte[] adminPasswordBytes = ByteHexUtils.hex2Bytes(findOne.getAdminPassword());
            boolean loginResult = SecurityUtils.DigestsUtils.decodeMd5(admin.getPlainPassword().getBytes(),
                    adminPasswordBytes, findOne.getEncryptKey().getBytes(), 1);
            if (loginResult) {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "登录成功", true);
            } else {
                return Result.success(String.valueOf(HttpStatus.OK.value()), "登录密码错误", false);
            }
        }
    }


    @ApiOperation(value = "|Admin|注册")
    @PostMapping("/register")
    public ResponseMessage<Boolean> register(Admin admin) throws Exception {
        AdminPage adminPage = new AdminPage();
        adminPage.setAdminName(admin.getAdminName());
        Admin findOne = adminService.queryBySingle(adminPage);
        if (findOne == null) {
            admin.setAdminId(UUID.randomUUID().toString());
            String salt = UUID.randomUUID().toString();
            admin.setEncryptKey(salt);
            //保存16进制的md5密码,解码时,需要将从数据库获取的16进制的md5密码进行解码
            //将16进制转换二进制时,和md5算法计算二进制不同,导致验证失败
            //更新了ByteHexUtils类
            String passwordMd5 = ByteHexUtils.bytes2Hex(SecurityUtils.DigestsUtils.md5(admin.getPlainPassword().getBytes()
                    , salt.getBytes(), 1));
            admin.setAdminPassword(passwordMd5);
            adminService.insert(admin);
            return Result.success(String.valueOf(HttpStatus.OK.value()), "注册成功", true);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "管理员不能重名", false);
        }
    }


    @ApiOperation(value = "|Admin|修改")
    @PutMapping("/admin")
    public ResponseMessage<Boolean> update(Admin admin) throws Exception {
        if (StringUtils.hasText(admin.getPlainPassword())) {
            String encryptKey = UUID.randomUUID().toString();
            admin.setEncryptKey(encryptKey);
            admin.setAdminPassword(ByteHexUtils.bytes2Hex(SecurityUtils.DigestsUtils.md5(admin.getPlainPassword().getBytes("UTF-8")
                    , encryptKey.getBytes("UTF-8"))));
        }
        int result = adminService.updateByPrimaryKeySelective(admin);
        if (result == 1) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据成功",
                    true);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据失败",
                    false);
        }
    }


    @ApiOperation(value = "|Admin|删除")
    @DeleteMapping("/admin/{adminId}")
    public ResponseMessage<Boolean> delete(@PathVariable String adminId) throws Exception {
        Admin findOne = adminService.selectByPrimaryKey(adminId);
        if (findOne.getIsAdminSuper() == 1) {
            int result = adminService.deleteByPrimaryKey(adminId);
            log.info("delete from TB_ADMIN where adminId = {}", adminId);
            return result == 1 ? Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据成功",
                    true) : Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据失败",
                    false);
        } else {
            Result.success(String.valueOf(HttpStatus.OK.value()), "您不是超级管理员,不能删除普通管理员",
                    false);
        }
        return Result.success(String.valueOf(HttpStatus.OK.value()), "删除失败",
                false);
    }
}
