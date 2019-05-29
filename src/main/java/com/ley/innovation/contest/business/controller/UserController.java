package com.ley.innovation.contest.business.controller;


import java.util.List;

import com.ley.innovation.contest.business.bo.UserBO;
import com.ley.innovation.contest.utils.excel.ExcelExportConstants;
import com.ley.innovation.contest.utils.excel.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.User;
import com.ley.innovation.contest.business.service.UserService;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.lang.Boolean;
import java.util.UUID;

/**
 * <b>功能：</b>UserController<br>
 **/
@RestController
@RequestMapping("/api/business/users")
@Api(description = "|User|")
@Slf4j
public class UserController extends BaseController<User> {


    @Autowired
    private UserService userService;


    @ApiOperation(value = "|User|新增")
    @PostMapping("/user")
    public ResponseMessage<Boolean> create(User user) throws Exception {
        user.setUserId(UUID.randomUUID().toString());
        int result = userService.insertSelective(user);
        if (result == 1) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据成功",
                    true);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据失败",
                    false);
        }
    }


    @ApiOperation(value = "|User|导出数据到Excel")
    @GetMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        List<UserBO> userVOS = UserService.getUserBOS();
        System.out.println(userVOS);
        ExcelUtils.writeExcel(response, userVOS, ExcelExportConstants.EXCEL_FILE_NAME, ExcelExportConstants.SHELL_NAME,
                new UserBO());
    }




}
