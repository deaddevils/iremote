package com.iremote.common.mail;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailUtil {
    private static Log log = LogFactory.getLog(EmailUtil.class);

    private static Message getMessage(MailSendVo mailInfo) throws Exception {
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassWord());
        }
        Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
        Message mailMessage = new MimeMessage(sendMailSession);
        Address from = new InternetAddress(mailInfo.getFromAddress());
        mailMessage.setFrom(from);
        List<String> address = mailInfo.getToAddress();
        Address[] to = new InternetAddress[address.size()];
        for(int i = 0; i < address.size(); i++){
            to[i] = new InternetAddress(address.get(i));
        }
        mailMessage.addRecipients(Message.RecipientType.TO,to);
        mailMessage.setSubject(mailInfo.getSubject());
        mailMessage.setSentDate(new Date());
        return mailMessage;
    }


    public static boolean sendHtmlMail(MailSendVo info){
        try {
            Message message = getMessage(info);
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(info.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            message.setContent(mainPart);
            Transport.send(message);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static boolean sendTextMail(MailSendVo info){
        try {
            Message message = getMessage(info);
            message.setText(info.getContent());
            Transport.send(message);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}