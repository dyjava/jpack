package com.my.test.mail;

import java.util.List;

import org.apache.log4j.Logger;

import com.my.mail.GetMail;
import com.my.mail.MailBean;
import com.my.mail.SendMail;

public class MailTest {
	private static final Logger log = Logger.getLogger(MailTest.class);
	public static void main(String[] args) {
		send() ;
	}

	public static void send(){
		MailBean mb = new MailBean();

		String mailbody = "<meta http-equiv=Content-Type content=text/html; charset=gb2312>"+
		"<div align=center><a href=http://www.csdn.net> csdn ffffffff </a></div>";
		mb.setMailBody(mailbody);
		new SendMail().sendMail(mb);
	}
	
	private static void get(){
		GetMail getMail = new GetMail() ;
		List list = getMail.get(20);
		for(int i=0;i<list.size();i++){
			MailBean mb = (MailBean) list.get(i);
			log.error("form:"+mb.getFrom());
			log.error("to:"+mb.getTo());
			log.error("title:"+mb.getMailTitle());
			log.error("content"+mb.getMailBody());
		}
	}
}
