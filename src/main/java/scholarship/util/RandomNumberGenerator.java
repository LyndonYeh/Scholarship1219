package scholarship.util;

import java.security.SecureRandom;

//信箱驗證 secureRandom
public class RandomNumberGenerator {
	/*
	  public static void main(String[] args) {
	        String randomCode = generateRandomCode();
	        System.out.println("Secure Random Code: " + randomCode);
	    }
	    */
	  public static String generateRandomCode() {
	        SecureRandom secureRandom = new SecureRandom();
	        // Generates a random integer between 0 and 999999 (inclusive)
	        int randomInt = secureRandom.nextInt(1000000);
	        // Formats the integer as a 6-digit code with leading zeros if necessary
	        // %06 0 - 補 0 / 6 - 6 格
	        return String.format("%06d", randomInt);
	    }
}
