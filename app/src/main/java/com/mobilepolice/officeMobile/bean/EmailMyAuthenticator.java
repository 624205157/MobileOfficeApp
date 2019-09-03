package com.mobilepolice.officeMobile.bean;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EmailMyAuthenticator extends Authenticator {
	String userName=null;
    String password=null;

    public EmailMyAuthenticator(){
    }
    public EmailMyAuthenticator(String username, String password) {
        this.userName = username; 
        this.password = password; 
    }
    
    /**
     * 登入校验
     */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	} 
}
