package utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Alberto G. Lagos
 */
public class MailHandler {
    private String to;
    private String from;
    private Properties props;
    private String host;
    private String port; 
    private String username;
    private String password;
    
    public MailHandler() {      
        props = new Properties();
        host = System.getenv("EMAIL_NOTIFIER_HOST");
        port = System.getenv("EMAIL_NOTIFIER_PORT");
        username = System.getenv("EMAIL_NOTIFIER_USER");
        password = System.getenv("EMAIL_NOTIFIER_PASS"); 
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", port);
	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", port);
               
    }
    
    public void sendMail(String to, String subject, String text) throws MessagingException {
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(text, "text/html");
        Transport.send(message);
    }
    
}
