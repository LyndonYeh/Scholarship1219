package scholarship.test;

import javax.mail.MessagingException;

import scholarship.service.EmailService;
import scholarship.util.RandomNumberGenerator;

public class TestMain {
	public static void main(String[] args) {
		// Replace these values with an actual email address and verification code
		try {
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
