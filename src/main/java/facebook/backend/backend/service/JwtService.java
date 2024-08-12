package facebook.backend.backend.service;


import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static String secretKey = "";

    // here we are creating a constructor to generate the key then set it to the
    // secret key
    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGenerator.generateKey();
        secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
    }

    public String generateToken(String username) {

        // here we are generating the token
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30)).and().signWith(generateKey())
                .compact();
    }

    // here we are generating the key
    public static SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String extractUserName(String token) {
        // Extract the username (subject) from the JWT
        return extractClaim(token, Claims::getSubject);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // Extract specific claims from the JWT
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        // Parse and verify the JWT, then extract all claims
        return Jwts.parser()
                .verifyWith(generateKey()) // Verify the signature with the secret key
                .build()
                .parseSignedClaims(token)
                .getPayload(); // Get the claims from the JWT payload
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        // Validate the token by checking the username and if it has expired
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private static boolean isTokenExpired(String token) {
        // Check if the token has expired
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        // Extract the expiration date from the JWT
        return extractClaim(token, Claims::getExpiration);
    }
}
