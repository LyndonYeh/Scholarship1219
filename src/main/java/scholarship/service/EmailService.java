package scholarship.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    public void sendVerificationCode(String toEmail, String verificationCode) throws MessagingException {
     
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "465"); 
      
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dave.wenyu@gmail.com", "kzznliehhentsans"); // Replace with your email credentials
            }
        });

        // Create a MimeMessage object
        Message message = new MimeMessage(session);

        
        message.setFrom(new InternetAddress("dave.wenyu@gmail.com")); // Replace with your email address
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

      
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        // Send the email
        Transport.send(message);
    }
}
