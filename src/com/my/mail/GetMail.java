package com.my.mail;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.my.mail.MailBean;

public class GetMail {
	private static final Logger log = Logger.getLogger(GetMail.class);

	private static Store store;

	private static Folder folder;

	private static Message[] message;

	private static String getDisplayAddress(Address a) { // 转换RFC822为Unicode
		String pers = null;
		String addr = null;
		if(a instanceof InternetAddress && ((pers =((InternetAddress)a).getPersonal())!= null)){
			addr = pers + " " + "<" + ((InternetAddress) a).getAddress() + ">";
		} else {
			addr = a.toString();
		}
		return addr;
	}

	private static void getConnect() {
		String protocol = "pop3";// protocol为连接协议，IMAP或是POP
		String mailhost = "pop.163.com";// mailhost主机，user为用户名，passwd为密码
		// String mailhost="pop3.yicha.cn";//mailhost主机，user为用户名，passwd为密码
		String user = "dyong525";
		String passwd = "dyong525";

		Session session = Session.getInstance(System.getProperties(), null);
		session.setDebug(false);
		try {
			store = session.getStore(protocol);
			log.info("get store is OK.");
			store.connect(mailhost, -1, user, passwd);
		} catch (MessagingException e) {
			log.error("连接服务器出错：" + e.getMessage());
		}
	}

	private static void getFolder() {
		try {
			folder = store.getFolder("INBOX");
		} catch (MessagingException e) {
			log.error("get Folder err:" + e.getMessage());
		}
		try {
			folder.open(Folder.READ_WRITE);
		} catch (MessagingException ex) {
			try {
				folder.open(Folder.READ_ONLY);
			} catch (MessagingException e) {
				log.error("open Folder err:" + e.getMessage());
			}
		}

		try {
			message = folder.getMessages();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// FetchProfile fp=new FetchProfile();
		// fp.add(FetchProfile.Item.ENVELOPE);
		// fp.add(FetchProfile.Item.FLAGS);
		// fp.add("X-Mailer");
		// try {
		// folder.fetch(message,fp);
		// } catch (MessagingException e) {
		// e.printStackTrace();
		// }
	}

	public static List get(int begin) {
		int psize = 10;
		getConnect();
		log.info("连接服务器成功。");

		getFolder();
		log.info("获取服务器端的信息成功");

		List list = null;
		try {
			list = getMessage(begin,psize);
			folder.close(true);// 是否在删除操作邮件后更新Folder
			store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static List getMessage(int begin,int size) throws MessagingException, IOException {
		List list = new ArrayList();
		int end = begin+size;
		for (int j=begin; j<end; j++){
			Message msg = message[j];
			MailBean mb = new MailBean();
//			发件人
			mb.setFrom(getDisplayAddress(msg.getFrom()[0])) ;
//			收件人
			mb.setTo(msg.getReplyTo().toString()) ;
//			邮件标题
			mb.setMailTitle(msg.getSubject());
//			邮件内容
			if (message[j].isMimeType("text/plain")) { // 若其Type为tex/plain就可直接读出了。
				mb.setMailBody(msg.getContent().toString());
			} else if (message[j].isMimeType("multipart/*")) {
				Multipart mp = (Multipart) msg.getContent();
				Part part = mp.getBodyPart(0);
//				附件
				mb.setFile(part.getFileName());
//				内容
				mb.setMailBody(part.getContent().toString());
			}
			list.add(mb);
		}

		return list;
	}

	private void getAttachFile(Part messagePart,
			BufferedOutputStream writeAttachObj) throws IOException, MessagingException{
		Object content = messagePart.getContent();
		try {
			// 这种情况下的邮件都是用multi模式发送的,
			// 这种模式包括有附件的邮件和用html表示content的邮件
			if(content instanceof Multipart){
				Multipart contentTmp = (Multipart) content;
				// 如果是MULTI模式发送的，BodyPart(0).getContent()肯定就是content
				System.out.println("content=="+ contentTmp.getBodyPart(0).getContent());
				// getCount()可以得到content中bodyPart的个数，content就是第一个
				// bodyPart，其它的附件按照顺序类推。但是有的时候附件就是另外一个邮件，
				// 而这个邮件里边可能有其他的附件。下面代码用循环对嵌套情况进行解析。
				for (int i = 0; i < contentTmp.getCount(); i++) {
					if(contentTmp.getBodyPart(i).isMimeType("multipart/*")){
						Multipart multipart = (Multipart) contentTmp.getBodyPart(i).getContent();
						// 这个地方增加循环是为了解决嵌套附件的情况。
						for (int k = 0; k < multipart.getCount(); k++) {
							// content也会存在于INPUTSTREAM中。
//							saveAttacheFile(multipart.getBodyPart(k)
//									.getContentType(), multipart.getBodyPart(k)
//									.getDisposition(), multipart.getBodyPart(k)
//									.getFileName(), multipart.getBodyPart(k)
//									.getInputStream(), writeAttachObj);
						}
					} else {
//						saveAttacheFile(contentTmp.getBodyPart(i)
//								.getContentType(), contentTmp.getBodyPart(i)
//								.getDisposition(), contentTmp.getBodyPart(i)
//								.getFileName(), contentTmp.getBodyPart(i)
//								.getInputStream(), writeAttachObj);
					}
				}
			} else {// 这种情况中邮件是纯文本形式，并且没有附件
				writeAttachObj.write(("content = " + content + "	").getBytes());
				writeAttachObj.flush();
			}
		} catch (Exception ie) {
			System.out.println("exception====" + ie.getMessage());
		}
	}

	public static void main(String[] args) throws MessagingException,
			IOException {
		List list = get(20);
		for(int i=0;i<list.size();i++){
			MailBean mb = (MailBean) list.get(i);
			log.error("form:"+mb.getFrom());
			log.error("to:"+mb.getTo());
			log.error("title:"+mb.getMailTitle());
			log.error("content"+mb.getMailBody());
		}
	}

}
