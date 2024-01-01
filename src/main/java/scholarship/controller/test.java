package scholarship.controller;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class test {

	public static void main(String[] args) {
		 // Create a Random object
        Random random = new Random();

        // Generate a random integer between 1 and 100 (inclusive)
        int randomNumber = random.nextInt(1000000) ;

        System.out.println("Random Number: " + randomNumber);
	}

}
