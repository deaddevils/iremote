package com.iremote.common.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class MailSendVo {

    private String mailServerHost;
    private String mailServerPort;
    private String fromAddress;
    private List<String> toAddress = new ArrayList<>();
    private String userName;
    private String passWord;
    //Authentication
    private boolean validate = false;
    private String subject;
    private String content;
    private String[] attachFileNames;

    public Properties getProperties(){
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.transport.protocol", "smtp");
        p.put("mail.smtp.auth", validate ? "true" : "false");
        //Use SSL, qq mailbox is necessary!
//        MailSSLSocketFactory sf = null;
//        try {
//            sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//        } catch (GeneralSecurityException e1) {
//            e1.printStackTrace();
//        }
        //Open security protocol
        p.put("mail.smtp.ssl.enable", "true");
//        p.put("mail.smtp.ssl.socketFactory", sf);
        return p;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPost() {
        return mailServerPort;
    }

    public void setMailServerPost(String mailServerPost) {
        this.mailServerPort = mailServerPost;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public List<String> getToAddress() {
        return toAddress;
    }

    public void setToAddress(List<String> toAddress) {
        this.toAddress = toAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    @Override
    public String toString() {
        return "MailSendVo{" +
                "mailServerHost='" + mailServerHost + '\'' +
                ", mailServerPort='" + mailServerPort + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress=" + toAddress +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", validate=" + validate +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", attachFileNames=" + Arrays.toString(attachFileNames) +
                '}';
    }
}
