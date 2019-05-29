package com.ley.innovation.contest.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * excel data listener
 **/
@Slf4j
public class ExcelDataListener extends AnalysisEventListener<Object> {

    private List<Object> dataList;


    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理
        dataList.add(o);
        log.info("o: {}", o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        //dataList.clear();
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }
}
