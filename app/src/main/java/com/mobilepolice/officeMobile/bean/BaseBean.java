package com.mobilepolice.officeMobile.bean;

import java.io.Serializable;

public class BaseBean implements Cloneable,Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String sdPath;//sd卡路径
	
	public BaseBean() {
	}

	public String getSdPath() {
		return sdPath;
	}

	public void setSdPath(String sdPath) {
		this.sdPath = sdPath;
	}

}
