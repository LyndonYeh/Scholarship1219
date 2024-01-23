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
import scholarship.util.RandomNumberGenerator;


public class EmailService {
	
    public static void sendVerificationCode(String toEmail, String verificationCode) throws MessagingException {
    	
    	final String username = "scholarshipplatforminfo@gmail.com";
        final String password = "knvvsrrpmbnhxevv";

        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "587"); // 設定 port 587

        // 要再設定把 credential 存入安全地方
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); // Replace with your email credentials
            }
        });

        Message message = new MimeMessage(session); 
        message.setFrom(new InternetAddress("scholarshipplatforminfo@gmail")); 
        if (toEmail != null) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        } else {
            // 錯誤處理
            System.out.println("toEmail is null");      
        }
        // 標題與內文設定
        message.setSubject("信箱驗證");
        message.setText("您的信箱驗證碼為: " + verificationCode);
        Transport.send(message);
    }
    
    // 測試用方法, 帶入收信者到 toEmai, 直接執行 application 
    public static void main(String[] args) throws MessagingException {
		  try {
	            // Replace these values with an actual email address and verification code
	            String toEmail = "dave.wenyu@gmail.com";
	            String verificationCode = RandomNumberGenerator.generateRandomCode();
	      
	            EmailService.sendVerificationCode(toEmail, verificationCode);

	            System.out.println("Email sent successfully.");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	}
}
