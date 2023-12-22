package scholarship.controller;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class test {

	public static void main(String[] args) {
		String abc="132";
		String a = BCrypt.hashpw(abc,BCrypt.gensalt(10));
		
		System.out.println(a);
	}

}
