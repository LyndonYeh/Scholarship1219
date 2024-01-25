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

        
        // Gmail smtp 設置        
        Properties properties = new Properties();
        // 設定使用 Gmail SMTP 伺服器來發送郵件，並且創建一個郵件的 Session 和 Message 物件
        properties.put("mail.smtp.auth", "true");
        // 啟用 SMTP 身份驗證 (mail.smtp.auth 設為 "true")
        properties.put("mail.smtp.starttls.enable", "true");
        // 啟用 STARTTLS 加密 (mail.smtp.starttls.enable 設為 "true") STARTTLS，Mail 加密的一種，能夠讓明文的通訊連線直接成為加密連線（使用SSL或TLS加密），而不需要使用另一個特別的埠來進行加密通訊，屬於機會性加密。
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        // SMTP 伺服器的主機名 
        properties.put("mail.smtp.port", "587"); 
        // 設定 port 587

     
        // 建立 Session 的原因是，把驗證資訊和參數都設定進去
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password); 
            }
        });
        //getPasswordAuthentication 方法返回一個 PasswordAuthentication 物件，其中包含使用者的帳號和密碼。這樣就確保了在建立 Session 時能夠提供正確的身份驗證

        //使用 MimeMessage 類別來創建一個郵件的 Message 物件
        Message message = new MimeMessage(session); 
        // 寄件人地址 (setFrom) 和收件人地址 (setRecipients)
        message.setFrom(new InternetAddress("scholarshipplatforminfo@gmail.com")); 
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
	            String toEmail = "dave.wenyu@gmail.com";
	            String verificationCode = RandomNumberGenerator.generateRandomCode();
	      
	            EmailService.sendVerificationCode(toEmail, verificationCode);

	            System.out.println("Email sent successfully.");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	}
}
