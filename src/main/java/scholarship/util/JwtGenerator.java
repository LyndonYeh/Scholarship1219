package scholarship.util;
import io.jsonwebtoken.Jwts;
import java.util.Date;

public class JwtGenerator {

    private static final String SECRET_KEY = "test"; 
    private static final long EXPIRATION_TIME_MS = 1000*5; 

    public static String generateJwt(String subject) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .compact();
    }

    // 測試用
    public static void main(String[] args) {
        String username = "john.doe"; 
        String jwt = JwtGenerator.generateJwt(username);
        System.out.println("Generated JWT: " + jwt);
    }
    
}