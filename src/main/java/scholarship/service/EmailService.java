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

        //
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your email host
        properties.put("mail.smtp.port", "587"); // Replace with your email port 587

        // Set up a Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("scholarshipplatforminfo@gmail.com", "knvvsrrpmbnhxevv"); // Replace with your email credentials
            }
        });

        // Create a MimeMessage object
        Message message = new MimeMessage(session);

        // Set the sender and recipient addresses
        message.setFrom(new InternetAddress("dave.wenyu@gmail.com")); // Replace with your email address
        if (toEmail != null) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        } else {
            // Handle the case where toEmail is null
            System.out.println("toEmail is null");
           
        }

        // Set the email subject and content
        message.setSubject("信箱驗證");
        message.setText("您的信箱驗證碼為: " + verificationCode);

        // Send the email
        Transport.send(message);
    }
    
    public static void main(String[] args) throws MessagingException {
		  try {
	            // Replace these values with an actual email address and verification code
	            String toEmail = "dave.wenyu@gmail.com";
	            String verificationCode = RandomNumberGenerator.generateRandomCode();

	            // Call the sendVerificationCode method
	            EmailService.sendVerificationCode(toEmail, verificationCode);

	            // Log a message indicating successful email sending
	            System.out.println("Email sent successfully.");
	        } catch (MessagingException e) {
	            // Log an error message if there is an exception
	            e.printStackTrace();
	        }
	}
}
