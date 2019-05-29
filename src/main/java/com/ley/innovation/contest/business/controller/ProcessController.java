package com.ley.innovation.contest.business.controller;


import java.text.ParseException;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.ley.springboot.base.web.BaseController;
import com.ley.innovation.contest.business.entity.Process;
import com.ley.innovation.contest.business.service.ProcessService;
import org.springframework.util.CollectionUtils;
import com.ley.springboot.base.utils.ResponseMessage;
import com.ley.springboot.base.utils.Result;
import com.ley.springboot.base.page.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.lang.Boolean;

/**
 * <b>功能：</b>ProcessController<br>
 **/
@RestController
@RequestMapping("/api/business/processs")
@Api(description = "|Process|")
@Slf4j
public class ProcessController extends BaseController<Process> {


    @Autowired
    private ProcessService processService;

    @ApiOperation(value = "|Process|后台分页查询(默认第一页,每页20条)")
    @GetMapping("/page")
    public ResponseMessage<PageInfo<Process>> page(ProcessPage page) throws Exception {
        page.setOrder("ASC");
        page.setOrderBy("PROCESS_ORDER");
        List<Process> rows = processService.queryByPage(page);
        //在没有异常的情况下
        if (!CollectionUtils.isEmpty(rows)) {
            rows.get(getCurrentProcessStage(rows)).setProcessStage(1);
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回分页数据成功",
                    getPageInfo(page.getPager(), rows));
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可分页的数据",
                    null);
        }
    }


    @ApiOperation(value = "|Process|前台查询")
    @GetMapping("")
    public ResponseMessage<Map<String, Object>> list() throws Exception {
        Map<String, Object> map = new HashMap<>(2);
        ProcessPage processPage = new ProcessPage();
        processPage.setProcessEnable("1");
        processPage.setOrder("ASC");
        processPage.setOrderBy("PROCESS_ORDER");
        List<Process> processes = processService.queryByList(processPage);
        if (!CollectionUtils.isEmpty(processes)) {
            int currentProcessIndex = getCurrentProcessStage(processes);
            processes.get(currentProcessIndex).setProcessStage(1);
            map.put("processList", processes);
            map.put("currentProcessStage", currentProcessIndex);
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回数据集合成功",
                    map);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的数据集合",
                    null);
        }
    }


    /**
     * get current process stage
     **/
    private int getCurrentProcessStage(List<Process> processes) throws ParseException {
        int processSize = processes.size();
        String currentTimeStr = DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd");
        long currentTime = DateUtils.parseDate(currentTimeStr, "yyyy-MM-dd").getTime();
        for (int i = 0; i < processSize; i++) {
            int prevIndex = i;
            Process prevProcess = processes.get(prevIndex);
            long prevTime = DateUtils.parseDate(prevProcess.getProcessTime(), "yyyy-MM-dd").getTime();
            //如果第一节点没有达到当前时间,第一节点是当前节点
            if (currentTime <= prevTime) {
                //返回当前节点
                return prevIndex;
            } else {
                int nextIndex = prevIndex + 1;
                //如果该节点的下一节点不是最后一个节点
                if (nextIndex < processSize) {
                    Process nextProcess = processes.get(nextIndex);
                    long nextTime = DateUtils.parseDate(nextProcess.getProcessTime(), "yyyy-MM-dd").getTime();
                    if (currentTime <= nextTime) {
                        log.info("current time: {},next time: {}", currentTime, nextTime);
                        log.info("next process: {}", nextProcess);
                        return prevIndex;
                    }
                } else {
                    //如果该节点下一节点是最后一个节点并且当前时间大于最后一个节点的时间
                    int lastIndex = processSize - 1;
                    Process lastProcess = processes.get(lastIndex);
                    long lastTime = DateUtils.parseDate(lastProcess.getProcessTime(), "yyyy-MM-dd").getTime();
                    if (lastTime < currentTime) {
                        log.info("last process: {}", lastProcess);
                        return lastIndex;
                    }
                }
            }
        }
        return 0;
    }


    @ApiOperation(value = "|Process|详情")
    @GetMapping("/process/{processId}")
    public ResponseMessage<Process> find(@PathVariable String processId) throws Exception {
        Process findOne = processService.selectByPrimaryKey(processId);
        if (findOne != null) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "返回单条数据成功",
                    findOne);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "没有可返回的单条数据",
                    null);
        }
    }


//    @ApiOperation(value = "|Process|新增")
//    @PostMapping("/process")
//    public ResponseMessage<Boolean> create(Process process) throws Exception {
//        process.setProcessId(UUID.randomUUID().toString());
//        String processTime = process.getProcessTime();
//        String realProcessTime = DateFormatUtils.format(new Date(Long.valueOf(processTime)), "yyyy-MM-dd");
//        process.setProcessTime(realProcessTime);
//        int processOrderNow = ProcessController.processOrder + 1;
//        process.setProcessOrder(processOrderNow);
//        int result = processService.insertSelective(process);
//        if (result == 1) {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据成功",
//                    true);
//        } else {
//            return Result.success(String.valueOf(HttpStatus.OK.value()), "插入数据失败",
//                    false);
//        }
//    }


    @ApiOperation(value = "|Process|修改")
    @PutMapping("/process")
    public ResponseMessage<Boolean> update(Process process) throws Exception {
        String processTime = process.getProcessTime();
        String realProcessTime = DateFormatUtils.format(new Date(Long.valueOf(processTime)), "yyyy-MM-dd");
        if (log.isDebugEnabled()) {
            log.debug("process time: {},format process time: {}", processTime, realProcessTime);
        }
        process.setProcessTime(realProcessTime);
        int result = processService.updateByPrimaryKeySelective(process);
        if (result == 1) {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据成功",
                    true);
        } else {
            return Result.success(String.valueOf(HttpStatus.OK.value()), "更新数据失败",
                    false);
        }
    }


    @ApiOperation(value = "|Process|删除")
    @DeleteMapping("/process/{processId}")
    public ResponseMessage<Boolean> delete(@PathVariable String processId) throws Exception {
        int result = processService.deleteByPrimaryKey(processId);
        log.info("delete from TB_PROCESS where processId = {}", processId);
        return result == 1 ? Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据成功",
                true) : Result.success(String.valueOf(HttpStatus.OK.value()), "删除数据失败",
                false);
    }
}
