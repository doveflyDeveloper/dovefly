package com.deertt.utils.helper.mail;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class DvSendMailHelper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String textContent = "hello,World!\n亲爱的，你好！";
		String htmlContent = "<html><head></head><body><p>乔媛媛：</p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！集团裘戎已将调查问卷【*******】分发给您，请尽快应答。"
				+ "<p><font color='#0000E3'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此邮件为系统自动发送，请勿回复。</font></p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>"
				+ "<p><font color='#6C6C6C' size='3'><pre>							发件人：中国联通采购管理系统（<a href='http://10.0.198.160/pur/'>http://10.0.198.160/pur/</a>）</pre></font></p>"
				+ "<p><font color='#6C6C6C' size='3'><pre>								 提示：如果上述链接地址无效，请把网页地址复制到浏览器地址栏中打开。</pre></font></p>"
				+ "<a href='http://www.baidu.com' target='_blank' ><img src='cid:gg1' width='60' height='45' border='0'></a>"
				+ "<a href='http://www.baidu.com' target='_blank' ><img src='cid:gg2' width='60' height='45' border='0'></a>"
				+ "</body><html>";

		List<String> attachFileNames = new ArrayList<String>();
		attachFileNames
				.add("C:\\Documents and Settings\\Administrator\\桌面\\格格1.jpg");
		attachFileNames
				.add("C:\\Documents and Settings\\Administrator\\桌面\\格格2.jpg");

		Map<String, String> contendIds = new HashMap<String, String>();
		contendIds.put("gg1",
				"C:\\Documents and Settings\\Administrator\\桌面\\格格1.jpg");
		contendIds.put("gg2",
				"C:\\Documents and Settings\\Administrator\\桌面\\格格2.jpg");
		try {
			sendTextMail_QQ(new String[] { "1039882808@qq.com" }, "亲爱的，我在测试……",
					textContent);
			sendHTMLMail_QQ(new String[] { "1039882808@qq.com" }, "亲爱的，我在测试……",
					htmlContent, attachFileNames, contendIds);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送邮件的基础通用方法，可发送文本邮件，HTML邮件（带图片），附件邮件等
	 * @Title: sendMail
	 * @param from 发送邮箱
	 * @param to 接收邮箱数组
	 * @param subject 邮件标题
	 * @param body 邮件正文内容
	 * @param attachFileNames 附件列表，列表内存储的是要发送附件的文件路径
	 * @param contendIds HTML邮件的图片URL映射，对应关系是：cid:url
	 * @param smtpHost 发送邮箱服务器
	 * @param userName 发送邮箱用户名
	 * @param password 用户密码
	 * @param isHtml 是否是HTML邮件
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException	
	 * @return void	
	 * @throws
	 */
	public static void sendMail(String from, String[] to, String subject,
			String body, List<String> attachFileNames,
			Map<String, String> contendIds, String smtpHost, String userName,
			String password, boolean isHtml) throws MessagingException,
			UnsupportedEncodingException {

		Properties props = System.getProperties();
		// 邮件发送服务器
		props.put("mail.smtp.host", smtpHost);
		// 通过验证
		props.put("mail.smtp.auth", "true");
		// 对话对象
		Session session = Session.getDefaultInstance(props, null);
		// 创建一个消息，并初始化该消息的各项元素
		MimeMessage msg = new MimeMessage(session);
		// 发送邮箱
		msg.setFrom(new InternetAddress(from));
		InternetAddress[] address = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			address[i] = new InternetAddress(to[i]);
		}
		// 接收邮箱
		msg.addRecipients(Message.RecipientType.TO, address);
		msg.setSubject(subject);

		if (isHtml) {

			// 后面的BodyPart将加入到此处创建的Multipart中
			Multipart mp = new MimeMultipart();
			// 如果有附件，则添加附件
			if (attachFileNames != null && attachFileNames.size() > 0) {
				for (int i = 0; i < attachFileNames.size(); i++) {
					MimeBodyPart mbp = new MimeBodyPart();

					// 选择出附件名
					String fileName = attachFileNames.get(i).toString();
					// 得到数据源
					FileDataSource fds = new FileDataSource(fileName);
					// 得到附件本身并至入BodyPart
					mbp.setDataHandler(new DataHandler(fds));
					// 得到文件名同样至入BodyPart
					mbp.setFileName(MimeUtility.encodeWord(fds.getName()));
					mp.addBodyPart(mbp);
				}

			}
			// 如果HTML邮件中有图片，则图片以隐藏附件的形式随邮件发送
			if (contendIds != null && contendIds.size() > 0) {
				for (Iterator<String> ite = contendIds.keySet().iterator(); ite
						.hasNext();) {
					String cid = ite.next();
					// 选择出附件名
					String fileName = contendIds.get(cid);

					MimeBodyPart mbp = new MimeBodyPart();

					// 得到数据源
					FileDataSource fds = new FileDataSource(fileName);
					// 得到附件本身并至入BodyPart
					mbp.setDataHandler(new DataHandler(fds));
					// 得到文件名同样至入BodyPart

					// MimeUtility.encodeWord(fds.getName());
					mbp.setFileName(cid);
					mbp.setHeader("Content-ID", cid);
					mp.addBodyPart(mbp);
				}
			}

			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(body, "text/html;charset=gb2312");
			mp.addBodyPart(mbp);
			// Multipart加入到信件
			msg.setContent(mp);
		} else {
			msg.setText(body);
		}
		// 设置信件头的发送日期
		msg.setSentDate(new Date());
		msg.saveChanges();
		// 发送信件
		Transport transport = session.getTransport("smtp");
		transport.connect(smtpHost, userName, password);
		transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		transport.close();
		System.out.println("邮件发送成功！");
	}
	
	/**
	 * 纯文本邮件发送方法
	 * @param from 发送邮箱
	 * @param to 接收邮箱数组
	 * @param subject 邮件标题
	 * @param body 邮件正文内容
	 * @param smtpHost 发送邮箱服务器
	 * @param userName 发送邮箱用户名
	 * @param password 用户密码
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException	
	 * @return void	
	 * @throws
	 */
	public static void sendTextMail(String from, String[] to, String subject,
			String body, String smtpHost, String userName, String password)
			throws MessagingException, UnsupportedEncodingException {
		sendMail(from, to, subject, body, null, null, smtpHost, userName,
				password, false);
	}

	/**
	 * HTML邮件发送方法
	 * @param from 发送邮箱
	 * @param to 接收邮箱数组
	 * @param subject 邮件标题
	 * @param body 邮件正文内容
	 * @param smtpHost 发送邮箱服务器
	 * @param userName 发送邮箱用户名
	 * @param password 用户密码
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException	
	 * @return void	
	 * @throws
	 */
	public static void sendHTMLMail(String from, String[] to, String subject,
			String body, List<String> attachFileNames,
			Map<String, String> contendIds, String smtpHost, String userName,
			String password) throws MessagingException,
			UnsupportedEncodingException {
		sendMail(from, to, subject, body, attachFileNames, contendIds,
				smtpHost, userName, password, true);
	}
	
	/**
	 * 默认用QQ发送纯文本邮件
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendTextMail_QQ(String[] to, String subject, String body)
			throws MessagingException, UnsupportedEncodingException {
		sendMail("1039882808@qq.com", to, subject, body, null, null,
				"smtp.qq.com", "1039882808", "fcm081164004", false);
	}

	/**
	 * 默认用QQ发送HTML邮件
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static void sendHTMLMail_QQ(String[] to, String subject,
			String body, List<String> attachFileNames,
			Map<String, String> contendIds) throws MessagingException,
			UnsupportedEncodingException {
		sendMail("1039882808@qq.com", to, subject, body, attachFileNames,
				contendIds, "smtp.qq.com", "1039882808", "fcm081164004", true);
	}
}