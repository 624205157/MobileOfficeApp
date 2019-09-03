package com.mobilepolice.officeMobile.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 
* @ClassName: FastJsonHelper 
* @Description: TODO(解析和生成json的辅助类) 
* @author wanggonglei marbletech.com.cn
* @date 2014-12-16 下午1:50:10 
*
 */
public class FastJsonHelper {
	
	/**
	 * 将java类型的对象转换为JSON格式的字符串
	 * 
	 * @param object
	 *            java类型的对象
	 * @return JSON格式的字符串
	 */
	public static <T> String serialize(T object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @param clz
	 *            java类型或者java数组类型，不包括java集合类型
	 * @return java类型的对象或者java数组类型的对象，不包括java集合类型的对象
	 */
	public static <T> T deserialize(String json, Class<T> clz) {
		return JSON.parseObject(json, clz);
	}

	/**
	 * 将JSON格式的字符串转换为List<T>类型的对象
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @param clz
	 *            指定泛型集合里面的T类型
	 * @return List<T>类型的对象
	 */
	public static <T> List<T> deserializeList(String json, Class<T> clz) {
		return JSON.parseArray(json, clz);
	}

	/**
	 * 将JSON格式的字符串转换成任意Java类型的对象
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @param type
	 *            任意Java类型
	 * @return 任意Java类型的对象
	 */
	public static <T> T deserializeAny(String json, TypeReference<T> type) {
		return JSON.parseObject(json, type);
	}

	public static JSON_TYPE testIsArrayORObject(String sJSON){
    /*
     * return 0:既不是array也不是object
     * return 1：是object
     * return 2 ：是Array
     */
		try {
			JSONArray array = new JSONArray(sJSON);
			return JSON_TYPE.JSON_TYPE_ARRAY;
		} catch (JSONException e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
			try {
				JSONObject object = new JSONObject(sJSON);
				return JSON_TYPE.JSON_TYPE_OBJECT;
			} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
				System.out.println("非法的JSON字符串");
				return JSON_TYPE.JSON_TYPE_ERROR;
			}
		}

	}
	/***
	 * 
	
	/***
	 * 通过.Net MVC的JsonResult类型生成的JSON字符串中，如果数据包含日期格式的字段， 则会生成形
	 * 为”S_Date”:”\/Date(1370707200000)\/”的字符串；这种格式是无法通过FastJSON解析的 ，默认情况
	 * 下，FastJSON将Date类型序列化为long，这样使得在序列化和反序列化日期格式数据的过程不会导致时区问题。所以， 必须将
	 * /Date(1370707200000)/中的long型日期数据提取出来，才能被FastJSON转换；
	 * 在网上没有找到很好的方法来进行简单转换，我的思路是采用JAVA正则表达式的方法，先提取出/Date(1370707200000)/，
	 * 然后再提取出其中的数字，将原JSON字符串中的日期替换为提取出的数字，即可；
	 */
	private void date() {
		String resData = "";// responseData;
		try {
			Pattern p = Pattern.compile("Date\\(\\d{13}\\)");
			Matcher m = p.matcher(resData);
			while (m.find()) {
				Pattern p1 = Pattern.compile("\\d{13}");
				Matcher m1 = p1.matcher(m.group());
				// Log.e("M", m.group());
				while (m1.find()) {
					// Log.e("M1", m1.group());
					resData = resData.replace("\\/" + m.group() + "\\/",
							m1.group());
				}
				// Log.e("R", resData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// adTexts = JSON.parseArray(resData, TextInfo.class)
	}
}
