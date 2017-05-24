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
    final String host = "smtp.hushmail.com";
    final String port = "465"; 
    final String username = "chirper-notifier@hush.com";
    final String password = "chirperproject@2017";
    
    public MailHandler() {      
        this.props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", port);
	props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", port);
    }
    
    public void sendMail(String to, String subject, String text) throws MessagingException {
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
            }
        });
        
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setContent(text, "text/html");
        System.out.println("Sending");
        Transport.send(message);
        System.out.println("Sent");
    }
    
}
