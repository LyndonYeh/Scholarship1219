package scholarship.util;

import java.security.SecureRandom;

//信箱驗證亂數產生器
public class RandomNumberGenerator {

	// 測試用 main methid
	  public static void main(String[] args) {
	        String randomCode = generateRandomCode();
	        System.out.println("Secure Random Code: " + randomCode);
	    }
	   
	// 使用 SecureRandom 相較一般的 Random 目的在於更複雜的算 / 更難預測的隨機數, 但同時也會比較慢
	  public static String generateRandomCode() {
	        SecureRandom secureRandom = new SecureRandom();
	        // 產生亂數 0 - 999999 
	        int randomInt = secureRandom.nextInt(1000000);
	        // 產生0填補空格: %06 0 - 補 0 / 6 - 6 格
	        return String.format("%06d", randomInt);
	    }
}
