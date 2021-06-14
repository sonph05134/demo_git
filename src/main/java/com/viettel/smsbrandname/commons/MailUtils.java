package com.viettel.smsbrandname.commons;

import com.viettel.log.util.ConstantsLog;
import com.viettel.log.util.StringUtils;
import com.viettel.smsbrandname.config.Constants;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtils extends StandardizeLogging {

    @Value("${emailConf.mail-smtp-user}")
    private String mailSmtpUser;

    @Value("${emailConf.mail-smtp-password}")
    private String password;

    @Value("${emailConf.mail-smtp-host}")
    private String host;

    @Value("${emailConf.mail-smtp-socketFactory-port}")
    private String socketPort;

    @Value("${emailConf.mail-smtp-socketFactory-class}")
    private String socketClass;

    @Value("${emailConf.mail-smtp-auth}")
    private String smtpAuth;

    @Value("${emailConf.mail-smtp-port}")
    private String port;

    @Value("${emailConf.mail-smtp-socketFactory-fallback}")
    private String socketFallback;

    @Value("${emailConf.mail-smtp-starttls-enable}")
    private String startTlsEnable;

    public MailUtils() {
        super(MailUtils.class);
    }

    public void send(String[] lstTo, String[] lstBcc, String[] lstCc, String sub, String msg, String filePart, String fileName) {
        Date date = new Date();
        Properties props = new Properties();
        props.setProperty("mail.smtp.user", mailSmtpUser);
        props.setProperty("mail.smtp.password", password);
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.socketFactory.port", socketPort);
        props.setProperty("mail.smtp.socketFactory.class", socketClass);
        props.setProperty("mail.smtp.auth", smtpAuth);
        props.setProperty("mail.smtp.port", port);
        props.setProperty("mail.smtp.socketFactory.fallback", socketFallback);
        props.setProperty("mail.smtp.starttls.enable", startTlsEnable);
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailSmtpUser, password);
                }
            });
        try {
            if ((lstTo == null || lstTo.length == 0) && (lstBcc == null || lstBcc.length == 0) && (lstCc == null || lstCc.length == 0)) {
                return;
            }
            MimeMessage message = new MimeMessage(session);
            message.setSubject(sub, "UTF-8");
            message.setFrom(new InternetAddress(mailSmtpUser));
            if (lstTo != null) {
                for (int i = 0; i < lstTo.length; i++) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(lstTo[i]));
                }
            }
            if (lstBcc != null) {
                for (int i = 0; i < lstBcc.length; i++) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(lstBcc[i]));
                }
            }
            if (lstCc != null) {
                for (int i = 0; i < lstCc.length; i++) {
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(lstCc[i]));
                }
            }
            if (StringUtils.validString(filePart)) {//attach file
                BodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();

//                messageBodyPart.setText("Here's the file");
                messageBodyPart.setContent(msg, "text/html; charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(filePart);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            } else {
                message.setContent(msg, "text/html; charset=utf-8");
//            message.setText(msg, "UTF-8");
            }
            Transport.send(message);
            writeInfoLog(ConstantsLog.TRANSACTION_STATUS.SUCCESS, Constants.RESULT.ADD_SUCCESS_STR, date);
        } catch (Exception e) {
            writeErrorLog(e, ConstantsLog.ERROR_CODE.EXCEPTION, ConstantsLog.TRANSACTION_STATUS.FAIL, date);
            throw new RuntimeException(e);
        }
    }
}
