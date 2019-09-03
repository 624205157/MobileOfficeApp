package com.mobilepolice.officeMobile.utils;

/**
 * Created by jiabaohan on 2018/1/20.
 */

/// <summary>
/// 判断字符串是JSONObject还是JSONArray
/// </summary>
public enum JSON_TYPE
{
    /**JSONObject*/
    JSON_TYPE_OBJECT,
    /**JSONArray*/
    JSON_TYPE_ARRAY,
    /**不是JSON格式的字符串*/
    JSON_TYPE_ERROR
}