package com.deertt.utils.helper.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FromStringTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.deertt.utils.helper.string.DvStringHelper;

public class DvReceiveMailHelper {
	private Properties props = null;
	
	/**
	 * 改变配置参数
	 * @param key
	 * @param newValue
	 */
	public void changeProperties(Object key, Object newValue){
		props.put(key, newValue);
	}
	
	/**
	 * 获得配置参数
	 * @param key
	 * @return
	 */
	public Object getProperties(Object key){
		return props.get(key);
	}
	
	/**
	 * 创建一个RmReceiveMailHelper对象
	 */
	public DvReceiveMailHelper(){
		props = System.getProperties();
		InputStream in = getClass().getResourceAsStream("/mail.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 自定义配置参数，而不使用默认的参数文件配置的参数
	 * @param protocol
	 * @param host
	 * @param userName
	 * @param password
	 * @param readStyle
	 * @param baseSaveAttchPath
	 * @param onlyUnread
	 * @param setSeen
	 */
	public DvReceiveMailHelper(String protocol, String host, String userName, String password, String readStyle, String baseSaveAttchPath, boolean onlyUnread, boolean setSeen){
		props = System.getProperties();
		props.put("mail.receive.transport.protocol",protocol);
		props.put("mail.receive.host",host);
		props.put("mail.receive.username",userName);
		props.put("mail.receive.password",password);
		props.put("mail.receive.readStyle",readStyle);
		props.put("mail.receive.baseSaveAttchPath",baseSaveAttchPath);
		props.put("mail.receive.onlyUnread",onlyUnread);
		props.put("mail.receive.setSeen",setSeen);
		
	}
	
	/**
	 * 打开Folder	
	 * @return
	 * @throws MessagingException
	 */
	private Folder openFolder() throws MessagingException {
		Session session = Session.getInstance(props);
		Store store = session.getStore(props.getProperty("mail.receive.transport.protocol"));
		store.connect(props.getProperty("mail.receive.host"), props.getProperty("mail.receive.username"), props.getProperty("mail.receive.password"));
		Folder folder = store.getFolder("INBOX");//获取收件箱文件夹
		folder.open("READ_ONLY".equals(props.getProperty("mail.receive.readStyle").toUpperCase()) ? Folder.READ_ONLY : Folder.READ_WRITE);// 以只读方式打开文件夹	
		return folder;
	}
	
	/**
	 * 关闭Folder，同时关闭其Store
	 * @param folder
	 * @param closeStyle
	 * @throws MessagingException
	 */
	private void closeFolder(Folder folder, boolean closeStyle) throws MessagingException {
		if(folder != null){
			folder.close(closeStyle);
			if(folder.getStore() != null){
				folder.getStore().close();
			}
		}
	}
	
	/**
	 * 所有邮件数量
	 * @return
	 * @throws MessagingException
	 */
	public int getMessageCount() throws MessagingException {
		Folder folder = openFolder();
		int numberOfTotal = folder.getMessageCount();// 所有邮件数量
		closeFolder(folder, true);
		return numberOfTotal;
	}
	
	/**
	 * 未读邮件数量
	 * @return
	 * @throws MessagingException
	 */
	public int getUnreadMessageCount() throws MessagingException {
		Folder folder = openFolder();
		int numberOfUnread = folder.getUnreadMessageCount();// 未读邮件数量
		closeFolder(folder, true);
		return numberOfUnread;
	}
	
	/**
	 * 未读邮件数量
	 * @return
	 * @throws MessagingException
	 */
	public int getNewMessageCount() throws MessagingException {
		Folder folder = openFolder();
		int numberOfNew = folder.getNewMessageCount();// 未读邮件数量
		closeFolder(folder, true);
		return numberOfNew;
	}
			
	/**
	 * 按照发件人邮箱地址查找未读邮件
	 * @param froms 邮箱地址数组
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public List<MimeMessage> receiveMessagesByFrom(String[] froms) throws MessagingException, IOException {
		SearchTerm searchTerm = null;
		if(froms != null && froms.length>0){
			// 组拼发件人过滤条件
			SearchTerm[] searchTerms = new SearchTerm[froms.length];
			for(int i = 0;i<froms.length;i++){
				searchTerms[i] = new FromStringTerm(froms[i]);
			}
			searchTerm = new OrTerm(searchTerms);  
		}
		return receiveMessages(searchTerm, false);
	}
	
	/**
	 * 按照发件人邮箱地址查找未读邮件
	 * @param froms 邮箱地址数组
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public List<MimeMessage> receiveUnreadMessagesByFrom(String[] froms) throws MessagingException, IOException {
		SearchTerm searchTerm = null;
		if(froms != null && froms.length>0){
			// 组拼发件人过滤条件
			SearchTerm[] searchTerms = new SearchTerm[froms.length];
			for(int i = 0;i<froms.length;i++){
				searchTerms[i] = new FromStringTerm(froms[i]);
			}
			searchTerm = new OrTerm(searchTerms);  
		}
		return receiveMessages(searchTerm, true);
	}
	
	/**
	 * 按照标题关键词查找邮件
	 * @param subjects 邮件标题包含的关键词
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */	
	public List<MimeMessage> receiveMessagesBySubject(String[] subjects) throws MessagingException, IOException {
		SearchTerm searchTerm = null;
		if(subjects != null && subjects.length>0){
			// 组拼发件人过滤条件
			SearchTerm[] searchTerms = new SearchTerm[subjects.length];
			for(int i = 0;i<subjects.length;i++){
				searchTerms[i] = new SubjectTerm(subjects[i]);
			}
			searchTerm = new OrTerm(searchTerms);  
		}
		return receiveMessages(searchTerm, false);
	}
	
	/**
	 * 按照标题关键词查找未读邮件
	 * @param subjects 邮件标题包含的关键词
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */	
	public List<MimeMessage> receiveUnreadMessagesBySubject(String[] subjects) throws MessagingException, IOException {
		SearchTerm searchTerm = null;
		if(subjects != null && subjects.length>0){
			// 组拼发件人过滤条件
			SearchTerm[] searchTerms = new SearchTerm[subjects.length];
			for(int i = 0;i<subjects.length;i++){
				searchTerms[i] = new SubjectTerm(subjects[i]);
			}
			searchTerm = new OrTerm(searchTerms);  
		}
		return receiveMessages(searchTerm, true);
	}
		
	/**
	 * 收取所有邮件
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public List<MimeMessage> receiveAllMessages() throws MessagingException, IOException {
		return receiveMessages(null, false);
	}
	
	/**
	 * 收取所有未读邮件
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public List<MimeMessage> receiveUnreadMessages() throws MessagingException, IOException {
		return receiveMessages(null, true);
	}
	
	/**
	 * 收取邮件基础方法
	 * @param searchTerm	搜索邮件过滤条件
	 * @param onlyUnread	是否只收取未读邮件
	 * @return
	 * @throws MessagingException
	 * @throws IOException 
	 */
	public List receiveMessages(SearchTerm searchTerm, boolean onlyUnread) throws MessagingException, IOException {
		List list = new ArrayList();
		Folder folder = openFolder();
		Message[] msgs = null;
		if(searchTerm != null){
			// 按条件搜索Message对象
			msgs = folder.search(searchTerm);
		}else{
			// 获得邮件夹Folder内的所有邮件Message对象
			msgs = folder.getMessages();
		}
		if(msgs != null){
			for (int i = 0; i < msgs.length; i++) {
				if(onlyUnread){//只收取未读邮件
					if(isUnread(msgs[i])) {//此邮件是未读邮件
						if(Boolean.valueOf(props.getProperty("mail.receive.setSeen"))){// 是否标记为已读
							msgs[i].setFlag(Flags.Flag.SEEN, true);
							// 删除邮件
							//msgs[i].setFlag(Flags.Flag.DELETED, true);
						}
						saveAttchMent((MimeMessage)msgs[i]);
						list.add(msgs[i]);
					}
				}else{
					if(isUnread(msgs[i])) {//此邮件是未读邮件
						if(Boolean.valueOf(props.getProperty("mail.receive.setSeen"))){// 是否标记为已读
							msgs[i].setFlag(Flags.Flag.SEEN, true);
							// 删除邮件
							//msgs[i].setFlag(Flags.Flag.DELETED, true);
						}
					}
					saveAttchMent((MimeMessage)msgs[i]);
					list.add(msgs[i]);
				}
			}
		}
		// 关闭连接时设置了删除标记的邮件才会被真正删除，相当于"QUIT"命令
		closeFolder(folder, true);
		return list;
	}
	/**
	 * 下载保存邮件信息的附件（如果有的话）
	 * @param msg
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void saveAttchMent(MimeMessage msg) throws IOException, MessagingException{
		//解析邮件内容
		Object content = msg.getContent();
		System.out.println(msg.getSubject());
		if (content instanceof MimeMultipart) {
			MimeMultipart multipart = (MimeMultipart) content;
			String saveAttchPath = props.getProperty("mail.receive.baseSaveAttchPath") + File.separator + filterChar(getSendEmail(msg)) + File.separator + createSaveDir(msg);
			parseMultipart(multipart, saveAttchPath);
		}
	}
	
	/**
	 * 根据邮件信息创建相应附件存放的唯一目录
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private String createSaveDir(MimeMessage msg) throws UnsupportedEncodingException, MessagingException{
		return DvStringHelper.prt(filterChar(getSubject(msg)),20,"...")+"("+filterChar(getMessageId(msg))+")";
	}
	
	/**
	 * 过滤掉Window下创建文件夹时的非法字符
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	private String filterChar(String s) throws UnsupportedEncodingException, MessagingException{
		return DvStringHelper.prt(s).replaceAll("[\\\\/:*?\"<>|]", "");
	}
	
	/**
	 * 对复杂邮件的解析
	 * @param multipart
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void parseMultipart(Multipart multipart, String saveAttchPath)
			throws MessagingException, IOException {
		int count = multipart.getCount();
		for (int idx = 0; idx < count; idx++) {
			BodyPart bodyPart = multipart.getBodyPart(idx);
			if (bodyPart.isMimeType("text/plain")) {
			} else if (bodyPart.isMimeType("text/html")) {
			} else if (bodyPart.isMimeType("multipart/*")) {
				Multipart mpart = (Multipart) bodyPart.getContent();
				parseMultipart(mpart, saveAttchPath);
			} else if (bodyPart.isMimeType("application/octet-stream")) {
				String disposition = bodyPart.getDisposition();
				if (disposition.equalsIgnoreCase(BodyPart.ATTACHMENT) || disposition.equalsIgnoreCase(BodyPart.INLINE)) {
					String fileName = MimeUtility.decodeText(bodyPart.getFileName());
					InputStream is = bodyPart.getInputStream();
					saveFile(is, saveAttchPath + File.separator + fileName);
				}
			}else {
				String contentType = bodyPart.getContentType();
				if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
					String fileName = MimeUtility.decodeText(bodyPart.getFileName());
					InputStream is = bodyPart.getInputStream();
					saveFile(is, saveAttchPath + File.separator + fileName);
				}
			}
		}
	}
	
	/**
	 * 文件创建保存
	 * @param is
	 * @param toSaveFilePath
	 * @throws IOException
	 */
	private void saveFile(InputStream is, String toSaveFilePath) throws IOException {
		File file = new File(toSaveFilePath);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream os = new FileOutputStream(file);		
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = is.read(bytes)) != -1) {
			os.write(bytes, 0, len);
		}
		if (os != null)
			os.close();
		if (is != null)
			is.close();
	}

	/**
	 * 获取发送邮件者信息
	 * @return 追梦<593450804@qq.com>
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		InternetAddress[] address = (InternetAddress[]) msg.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		String personal = MimeUtility.decodeText(address[0].getPersonal());
		if (personal == null) {
			personal = "";
		}
		String fromaddr = personal + "<" + from + ">";
		return fromaddr;
	}

	public String getSendEmail(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		InternetAddress[] address = (InternetAddress[]) msg.getFrom();
		String fromEmail = address[0].getAddress();
		if (fromEmail == null) {
			fromEmail = "_";
		}
		return fromEmail;
	}
	/**
	 * 获取邮件收件人，抄送，密送的地址和信息。根据所传递的参数不同 "to"-->收件人,"cc"-->抄送人地址,"bcc"-->密送地址
	 * @param type
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public String getMailAddress(MimeMessage msg, String type) throws MessagingException,
			UnsupportedEncodingException {
		String mailaddr = "";
		String addrType = type.toUpperCase();
		InternetAddress[] address = null;

		if (addrType.equals("TO") || addrType.equals("CC")
				|| addrType.equals("BCC")) {
			if (addrType.equals("TO")) {
				address = (InternetAddress[]) msg
						.getRecipients(MimeMessage.RecipientType.TO);
			}
			if (addrType.equals("CC")) {
				address = (InternetAddress[]) msg
						.getRecipients(MimeMessage.RecipientType.CC);
			}
			if (addrType.equals("BCC")) {
				address = (InternetAddress[]) msg
						.getRecipients(MimeMessage.RecipientType.BCC);
			}

			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String mail = address[i].getAddress();
					if (mail == null) {
						mail = "";
					} else {
						mail = MimeUtility.decodeText(mail);
					}
					String personal = address[i].getPersonal();
					if (personal == null) {
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + mail + ">";
					mailaddr += "," + compositeto;
				}
				mailaddr = mailaddr.substring(1);
			}
		} else {
			throw new RuntimeException("Error email Type!");
		}
		return mailaddr;
	}

	/**
	 * 获取邮件主题
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
		return MimeUtility.decodeText(msg.getSubject());
	}

	/**
	 * 获取邮件发送日期
	 * @return
	 * @throws MessagingException
	 */
	public String getSendDate(MimeMessage msg) throws MessagingException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(msg.getSentDate());
	}

	/**
	 * 获取邮件正文内容
	 * @return
	 * @throws MessagingException 
	 * @throws IOException 
	 */
	public String getBodyText(MimeMessage msg) throws IOException, MessagingException {
		return MimeUtility.decodeText(msg.getContent().toString());
	}

	/**
	 * 判断邮件是否需要回执，如需回执返回true，否则返回false
	 * @return
	 * @throws MessagingException
	 */
	public boolean getReplySign(MimeMessage msg) throws MessagingException {
		return msg.getHeader("Disposition-Notification-TO")==null?false:true;
	}

	/**
	 * 获取此邮件的message-id
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException 
	 */
	public String getMessageId(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		return MimeUtility.decodeText(msg.getMessageID());
	}

	/**
	 * 判断此邮件是否已读，如果未读则返回false，已读返回true
	 * @return
	 * @throws MessagingException
	 */
	public boolean isUnread(Message msg) throws MessagingException {
		boolean isnew = false;
		Flags flags = msg.getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isnew = true;
				break;
			}
		}
		return isnew;
	}

}

