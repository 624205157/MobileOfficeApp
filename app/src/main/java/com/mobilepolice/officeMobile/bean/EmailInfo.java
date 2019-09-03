package com.mobilepolice.officeMobile.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * 邮件的基本信息
 * @author Administrator
 *
 */
public class EmailInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 发送邮件的服务器的IP和端口
	private String mailServerHost;
	private String mailServerPort = "25";

	private String imapServerHost;
	private String imapServerPort = "143";
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	// 是否需要身份验证
	private boolean validate = false;
	// 邮件发送者的地址
	private String fromAddress;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的路径
	private List<EmailAttachment> attachmentInfos;
	// 邮件的接收者，可以有多个
	private String[] receivers;
	private String toAddress;   // 邮件接收者的地址
	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getImapServerHost() {
		return imapServerHost;
	}

	public void setImapServerHost(String imapServerHost) {
		this.imapServerHost = imapServerHost;
	}

	public String getImapServerPort() {
		return imapServerPort;
	}

	public void setImapServerPort(String imapServerPort) {
		this.imapServerPort = imapServerPort;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String[] getReceivers() {
		return receivers;
	}

	public void setReceivers(String[] receivers) {
		this.receivers = receivers;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}


	public List<EmailAttachment> getAttachmentInfos() {
		return attachmentInfos;
	}

	public void setAttachmentInfos(List<EmailAttachment> attachmentInfos) {
		this.attachmentInfos = attachmentInfos;
	}

	

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}
}
