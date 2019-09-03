package com.mobilepolice.officeMobile.http;

import java.lang.reflect.Type;

import com.google.gson.Gson;

/**
 * Gson工具类 2015-02-01<br/>
 * 1,把json转换成Object<br/>
 * 2,把Object转换成json<br/>
 * 3,该方法主要功能是将json字符串转换成指定类型的对象<br/>
 * @author lipanquan
 *
 */
public final class GsonUtils {

    /**
     * 工具类对象<br/>
     * （单例）
     */
    private static final GsonUtils gsonUtils = new GsonUtils();

    /**
     * Gson对象
     */
    private static final Gson gson = new Gson();

    /**
     * 私有构造
     */
    private GsonUtils() {
    }

    /**
     * 对外提供静态公有的方法
     *
     * @return 本类对象
     */
    public static GsonUtils getInstance() {
        return gsonUtils;
    }

    /**
     * 1，该方法主要功能是将json字符串转换成Java类对象
     *
     * @param json
     *            json字符串
     * @param cls
     *            Java类的字节码对象
     * @return 解析后的Java类对象
     * @throws Exception
     *             如果解析中出了问题，或者是json不完整都会抛出这个异常信息
     */
    public <T> T j2O(String json, Class<T> cls) throws Exception {
        return gson.fromJson(json, cls);
    }

    /**
     * 2，该方法主要功能是将Java类对象转换成json字符串
     *
     * @param obj
     *            Java对象
     * @return json字符串
     */
    public String o2J(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 3,该方法主要功能是将json字符串转换成指定类型的对象
     *
     * @param json
     *            json字符串
     * @param typeOfT
     *            指定类型
     * @return 指定类型的对象
     */
    public <T> T j2T(String json, Type typeOfT) throws Exception {
        return gson.fromJson(json, typeOfT);
    }
}