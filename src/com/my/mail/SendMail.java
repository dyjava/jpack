package com.my.mail;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.log4j.Logger;

import java.util.Properties;

public class SendMail {
	private static final Logger log = Logger.getLogger(SendMail.class);
	private MimeMessage mimeMsg; 	//MIME邮件对象

	private Session session; 		//邮件会话对象
	private Properties props; 		//系统属性
	private boolean needAuth ; 		//smtp是否需要认证
	private String username = ""; 		//smtp认证用户名和密码
	private String password = "";
	private Multipart mp; 			//Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	public SendMail() {
//		setSmtpHost(getConfig.mailHost);	//如果没有指定邮件服务器,就从getConfig类中获取
		createMimeMessage();
	}

	public SendMail(String smtp){
		setSmtpHost(smtp);
		createMimeMessage();
	}

	public void setSmtpHost(String hostName) {
		log.info("设置系统属性：mail.smtp.host = "+hostName);
		if(props == null){
			props = System.getProperties(); //获得系统属性对象
		}
		props.put("mail.smtp.host",hostName); //设置SMTP主机
	}

	public boolean createMimeMessage(){
		try{
			log.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props,null); //获得邮件会话对象
		}catch(Exception e){
			log.error("获取邮件会话对象时发生错误！"+e);
			return false;
		}
		log.info("准备创建MIME邮件对象！");
		try{
			mimeMsg = new MimeMessage(session); //创建MIME邮件对象
			mp = new MimeMultipart();
			return true;
		}catch(Exception e){
			log.error("创建MIME邮件对象失败！"+e);
			return false;
		}
	}
	
	public void setNeedAuth(boolean need) {
		log.info("设置smtp身份认证：mail.smtp.auth = "+need);
		if(props == null){
			props = System.getProperties();
		}
		if(need){
			props.put("mail.smtp.auth","true");
		}else{
			props.put("mail.smtp.auth","false");
		}
		needAuth = need ;
	}

	public void setNamePass(String name,String pass) {
		username = name;
		password = pass;
	}

	public boolean setSubject(String mailSubject) {
		log.info("设置邮件主题！");
		try{
			mimeMsg.setSubject(mailSubject);
			return true;
		}catch(Exception e) {
			log.error("设置邮件主题发生错误！");
			return false;
		}
	}

	public boolean setBody(String mailBody) {
		try{
			BodyPart bp = new MimeBodyPart();
			bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+mailBody,"text/html;charset=GB2312");
			mp.addBodyPart(bp);
			return true;
		}catch(Exception e){
			log.error("设置邮件正文时发生错误！"+e);
			return false;
		}
	}

	public boolean addFileAffix(String filename) {
		log.info("增加邮件附件："+filename);
		try{
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			mp.addBodyPart(bp);
			return true;
		}catch(Exception e){
			log.error("增加邮件附件："+filename+"发生错误！"+e);
			return false;
		}
	}

	public boolean setFrom(String from) {
		log.info("设置发信人！");
		try{
			mimeMsg.setFrom(new InternetAddress(from)); //设置发信人
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean setTo(String to){
		if(to == null){
			return false;
		}
		try{
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean setCopyTo(String copyto){
		if(copyto == null){
			return false;
		}
		try{
			mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean setBCopyTo(String bcopyto){
		if(bcopyto == null){
			return false;
		}
		try{
			mimeMsg.setRecipients(Message.RecipientType.BCC,(Address[])InternetAddress.parse(bcopyto));
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean sendout(){
		try{
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			Session mailSession = Session.getInstance(props,null);
			Transport transport = mailSession.getTransport("smtp");
			log.info("邮箱服务链接成功");
			transport.connect((String)props.get("mail.smtp.host"),username,password);
			log.info("登录邮箱成功！");
			
			transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO));
			log.info("发送邮件成功！");
			if(mimeMsg.getRecipients(Message.RecipientType.BCC)!=null){
				transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.BCC));
				log.info("抄送邮件成功！");
			}
			if(mimeMsg.getRecipients(Message.RecipientType.CC)!=null){
				transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.CC));
				log.info("抄送成功！");
			}
			transport.close();
			return true;
		}catch(Exception e)	{
			log.error("邮件发送失败！"+e);
			e.printStackTrace() ;
			return false;
		}
	}

	public static boolean sendMail(MailBean mb){
		
		SendMail themail = new SendMail(mb.getMailServ());
		themail.setNeedAuth(mb.isNeedAuth());
		
		themail.setSubject(mb.getMailTitle());
		themail.setBody(mb.getMailBody());
		
		if(mb.getFile().trim().length()>0){
			themail.addFileAffix(mb.getFile());
		}
		
		themail.setFrom(mb.getFrom());
		themail.setTo(mb.getTo());
		themail.setCopyTo(mb.getCopyto());
		themail.setBCopyTo(mb.getBcopyto());
		themail.setNamePass(mb.getUsr(),mb.getPwd());
		
		themail.sendout();
		log.info("OK！");
		return true ;
	}
	public static void main(String[] args){
		MailBean mb = new MailBean();

		String mailbody = "<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+
		"<div align=center><a href=http://www.csdn.net> csdn ffffffff </a></div>";
		mb.setMailBody(mailbody);
		sendMail(mb);
		
	}


}
