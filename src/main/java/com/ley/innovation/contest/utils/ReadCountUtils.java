package com.ley.innovation.contest.utils;

import com.ley.innovation.contest.business.entity.Event;
import com.ley.innovation.contest.business.entity.Information;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 阅读量工具类
 *
 * @author ley
 **/
@Slf4j
public class ReadCountUtils {

    /**
     * read count cache
     **/
    private static ConcurrentHashMap<String, Integer> readCountCache = new ConcurrentHashMap<>(64);

    /**
     * 是否是第一次启动项目
     **/
    private static boolean isFirstStartProject = true;


    /**
     * put value into cache
     **/
    public static void put(String key, Integer value) {
        readCountCache.put(key, value);
    }


    /**
     * get value
     **/
    public static Integer get(String key) {
        return readCountCache.get(key);
    }


    /**
     * contains key
     **/
    public static boolean containsKey(String key) {
        return readCountCache.containsKey(key);
    }


    /**
     * get event read count
     **/
    public static int getEventReadCount(String eventId, Event event) {
        int readCount = doGetReadCount0(event.getEventReadCount(), eventId);
        return readCount;
    }


    /**
     * get info read count
     **/
    public static int getInfoReadCount(String infoId, Information information) {
        int readCount = doGetReadCount0(information.getInfoReadCount(), infoId);
        return readCount;
    }


    private static int doGetReadCount0(Integer readCount, String id) {

        int readCountReal;
        try {

            //如果是第一次启动项目,则从数据库拿数据
            if (isFirstStartProject) {
                //如果数据库中有,则先从数据中取(数值型字段,需要有默认值,一般为0),防止空指针异常
                //特别是集合,要确定里面的存在的值是否能为null.
                if (readCount != 0) {
                    ReadCountUtils.put(id, readCount);
                } else {
                    ReadCountUtils.put(id, 1);
                }
                isFirstStartProject = false;

            } else {
                //否则,从缓存中阅读量
                if (ReadCountUtils.containsKey(id)) {
                    //如果缓存有,从缓存中取
                    int readCountCache = ReadCountUtils.get(id);
                    readCountCache = readCountCache + 1;
                    ReadCountUtils.put(id, readCountCache);
                } else {
                    //新添加的,防止抛出NPE异常
                    ReadCountUtils.put(id, 1);
                }
            }

            //获取最新的阅读量
            readCountReal = ReadCountUtils.get(id);

        } catch (Exception e) {
            //发生异常则从数据库读取15秒以前的阅读量
            readCountReal = readCount;
            log.warn("更新阅读量失败: {}", e);
        }
        return readCountReal;
    }


}
