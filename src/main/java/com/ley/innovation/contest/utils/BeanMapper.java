package com.ley.innovation.contest.utils;

import com.ley.springboot.base.page.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * bean mapper
 *
 * @see Mapper
 **/

public class BeanMapper {

    @Resource
    private Mapper dozerMapper;

    /**
     * @see Mapper#map(Object, Class)
     **/
    public <T> T map(Object source, Class<T> destinationClass) {
        Assert.notNull(dozerMapper, "dozerMapper must not be null");
        return source == null ? null : this.dozerMapper.map(source, destinationClass);
    }

    /**
     * map list to destination class
     *
     * @param sourceList source object list
     * @param destClass  destination class
     * @return destination list
     **/
    public <S, D> List<D> mapList(Collection<S> sourceList, Class<D> destClass) {
        Assert.notNull(dozerMapper, "dozerMapper must not be null");
        ArrayList<D> destinationList = new ArrayList<>();
        if (sourceList == null) {
            return destinationList;
        } else {
            Iterator<S> sourceIterator = sourceList.iterator();
            while (sourceIterator.hasNext()) {
                S source = sourceIterator.next();
                D destination = this.dozerMapper.map(source, destClass);
                destinationList.add(destination);
            }
            return destinationList;
        }
    }

    /**
     * @see Mapper#map(Object, Object)
     **/
    public void copy(Object source, Object destination) {
        Assert.notNull(dozerMapper, "dozerMapper must not be null");
        this.dozerMapper.map(source, destination);
    }

    /**
     * 适用于使用{@link PageInfo}和{@link com.ley.springboot.base.page.BasePage}进行分页
     **/
    public <T> PageInfo<T> mapPage(PageInfo source, Class<T> destinationClass) {
        PageInfo pageInfo = new PageInfo();
        //当前页
        pageInfo.setPageNo(source.getPageNo());
        //每页记录数
        pageInfo.setPageSize(source.getPageSize());
        //总记录数
        pageInfo.setCount(source.getCount());
        //总页数
        pageInfo.setPageCount(source.getPageCount());
        //当前页数据集合
        pageInfo.setList(this.mapList(source.getList(), destinationClass));
        return pageInfo;
    }
}
