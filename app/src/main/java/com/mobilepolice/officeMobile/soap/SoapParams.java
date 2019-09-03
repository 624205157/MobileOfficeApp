package com.mobilepolice.officeMobile.soap;


import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Soap请求参数
 * 
 */
public class SoapParams {

	/** 参数. */
	protected LinkedHashMap<String, Object> params;

	/**
	 * Instantiates a new ab soap params.
	 */
	public SoapParams() {
		init();
	}

	/**
	 * Instantiates a new ab soap params.
	 * 
	 * @param obj
	 *            the obj
	 */
	public SoapParams(Object obj) {
		try {
			init();
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				field.setAccessible(true);
				Object fieldValue =  field.get(obj);
				params.put(fieldName, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用一个map构造请求参数.
	 * 
	 * @param source
	 *            the source
	 */
	public SoapParams(Map<String, Object> source) {
		init();

		for (Map.Entry<String, Object> entry : source.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 初始化.
	 */
	private void init() {
		params = new LinkedHashMap<String, Object>();
	}

	/**
	 * 增加一对请求参数.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void put(String key, Object value) {
		if (key != null && value != null) {
			params.put(key, value);
		}
	}

	/**
	 * 删除一个请求参数.
	 * 
	 * @param key
	 *            the key
	 */
	public void remove(String key) {
		params.remove(key);
	}

	/**
	 * 获取参数列表.
	 * 
	 * @return the params list
	 */
	public LinkedHashMap<String,Object> getParamsList() {
		LinkedHashMap<String,Object> paramsList = new LinkedHashMap<String,Object>();
		return params;
	}
}
