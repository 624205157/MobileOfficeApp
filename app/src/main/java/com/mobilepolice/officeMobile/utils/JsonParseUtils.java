package com.mobilepolice.officeMobile.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * json解析的工具类，用于解析按照标准格式封装的列表型、对象型json 2015-06-19 15:12:36
 */
public class JsonParseUtils {

    // 把json转换为boolean返回
    public static boolean jsonToBoolean(String json) {
        try {
            if (!TextUtils.isEmpty(json)) {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject != null && jsonObject.getBoolean("success")) {
                    return true;
                }
            }
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static boolean jsonToBooleanBind(String value, String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null
                    && value.equals(jsonObject.getString("success"))) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static String jsonToMsg(String json) {
        try {
            if (!TextUtils.isEmpty(json)) {
                JSONObject jsonObject = new JSONObject(json);
                return jsonObject.getString("msg");
            } else {
                return "";
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public static String jsonToResult(String json, String msg) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(msg);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    public static boolean jsonToLogin(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null
                    && "21".equals(jsonObject.getString("status"))) {
                return true;
            }
            if (jsonObject != null
                    && "22".equals(jsonObject.getString("status"))) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static boolean jsonToInsurance(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null
                    && "1".equals(jsonObject.getString("state"))) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static <T> String serialize(T object) {
        return JSON.toJSONString(object);
    }

    /**
     * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
     *
     * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
     * @param(JSON格式的字符串)
     * @param(java类型或者java数组类型，不包括java集合类型)
     */
    public static <T> T deserialize(String json, Class<T> clz) {
        return JSON.parseObject(json, clz);
    }
}
