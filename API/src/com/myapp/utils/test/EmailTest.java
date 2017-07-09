package com.myapp.utils.test;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;

public class EmailTest {
	public static void main(String[] args) throws Exception {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("d:/CrackCaptcha.log");
		// attachment.setURL(new
		// URL("http://www.apache.org/images/asf_logo_wide.gif"));
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("Apache logo");
		attachment.setName("Apache logo");

		// Create the mail message
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName("localhost");// 你的邮件服务器的地址
		htmlEmail.setSmtpPort(5000);

		// htmlEmail.setAuthentication("", "");//如果你的邮件服务器设置了密码，请输入密码，否则此语句可以忽略
		htmlEmail.setTLS(false);
		htmlEmail.setFrom("lenovo.orders@lenovo.com");// 发件人
		htmlEmail.addTo("sangyue1@lenovo.com", "test");// 设置收件人，如果想添加多个收件人，将此语句多写几次即可。其中参数1，代表收件人邮件地址；参数2，用于收件人收到邮件时看到的收件人姓名
		// htmlEmail.setFrom("test0@test0.com", "system test");// 发件人

		htmlEmail.setSubject("this is system test, do not reply!");
		htmlEmail.setMsg("this is system test, do not reply!");

		// htmlEmail.setHtmlMsg(testEditor);//testEditor 变量包含html内容
		htmlEmail.attach(attachment);// 附件

		htmlEmail.send();// 发送邮件
	}
}
