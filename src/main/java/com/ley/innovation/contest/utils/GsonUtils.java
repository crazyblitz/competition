package com.ley.innovation.contest.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * google json utils
 * **/
public class GsonUtils {

    public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

    /**
     * get google json
     * @see Gson
     * **/
    public static Gson getGson() {
        return (new GsonBuilder()).serializeNulls().setDateFormat(GsonUtils.DATE_FORMAT).create();
    }

    /**
     * object to json
     * **/
    public static String toJson(Object obj) {
        return getGson().toJson(obj);
    }

    /**
     * json to type
     * @see Gson#toJson(Object, Type)
     * **/
    public static String toJson(Object obj, Type type) {
        return getGson().toJson(obj, type);
    }

    /**
     * generic t to json
     * **/
    public static <T> String t2Json(T t) {
        return getGson().toJson(t);
    }

    /**
     * json to generic t
     * **/
    public static <T> T json2T(String jsonString, Class<T> clazz) {
        return getGson().fromJson(jsonString, clazz);
    }

    /**
     * json to collection
     * **/
    public static <T> List<T> json2Collection(String jsonStr, Type type) {
        return (List<T>) getGson().fromJson(jsonStr, type);
    }

    /**
     * json to type
     * @see Gson#fromJson(String, Type)
     * **/
    public static <T> T fromJson(String jsonStr, Type type) {
        return getGson().fromJson(jsonStr, type);
    }

    /**
     * json to class type
     * **/
    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return getGson().fromJson(jsonStr, clazz);
    }
}
