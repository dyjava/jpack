package com.my.mail;

public class MailBean {
	private String mailTitle = "send mail";				//邮件标题
	private String mailBody = "this is a test mail";	//发送内容
	private String file = "";							//附件地址
	
	private String to = "";						//收件人邮箱，多收件人用逗号分割
	private String copyto = "" ;				//抄送人邮箱
	private String bcopyto = "" ;				//暗送人邮箱
	private String from = "system";				//发件人邮箱，匿名发送时显示发送人信息。
	private String usr = ""; 					//发件人用户名
	private String pwd = "";					//密码
	
	private boolean needAuth = true ;			//smtp是否需要认证，false时匿名发送。需确定服务器是否支持匿名发送。
	private static String mailServ = "mail.umessage.com.cn";	//发件人邮箱服务
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	public String getMailServ() {
		return mailServ;
	}
	public String getCopyto() {
		return copyto;
	}
	public void setCopyto(String copyto) {
		this.copyto = copyto;
	}
	public void setMailServ(String Serv) {
		mailServ = Serv;
	}
	public String getMailTitle() {
		return mailTitle;
	}
	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}
	public boolean isNeedAuth() {
		return needAuth;
	}
	public void setNeedAuth(boolean needAuth) {
		this.needAuth = needAuth;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getUsr() {
		return usr;
	}
	public void setUsr(String usr) {
		this.usr = usr;
	}
	public String getBcopyto() {
		return bcopyto;
	}
	public void setBcopyto(String bcopyto) {
		this.bcopyto = bcopyto;
	}
	
}
