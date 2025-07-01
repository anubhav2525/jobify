package com.naukari.server.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400}") // Default 24 hours in seconds
    private int jwtExpirationInMs;

    @Value("${jwt.refresh.expiration:604800}") // Default 7 days in seconds
    private int refreshTokenExpirationInMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generate JWT token from Authentication object (existing method)
     */
    public String generateJwtToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Date expiryDate = new Date((new Date()).getTime() + jwtExpirationInMs * 1000L);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", customUserDetails.getRole());
        claims.put("type", "access");

        return Jwts.builder()
                .subject(customUserDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Generate JWT token from email (for login method)
     */
    public String generateToken(String email) {
        Date expiryDate = new Date((new Date()).getTime() + jwtExpirationInMs * 1000L);

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Generate refresh token from email
     */
    public String generateRefreshToken(String email) {
        Date expiryDate = new Date((new Date()).getTime() + refreshTokenExpirationInMs * 1000L);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("type", "refresh");

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Generate JWT token with custom claims
     */
    public String generateTokenWithClaims(String subject, Map<String, Object> claims) {
        Date expiryDate = new Date((new Date()).getTime() + jwtExpirationInMs * 1000L);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Get user name from token (existing method)
     */
    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Get email from token
     */
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Try to get email from claims first, then from subject
        String email = (String) claims.get("email");
        return email != null ? email : claims.getSubject();
    }

    /**
     * Get all claims from token
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Get specific claim from token
     */
    public Object getClaimFromToken(String token, String claimName) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get(claimName);
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Get expiration date from token
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * Get issued date from token
     */
    public Date getIssuedDateFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getIssuedAt();
    }

    /**
     * Validate JWT token (existing method with enhancements)
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("JWT token validation failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Validate token against specific user
     */
    public boolean validateToken(String token, String email) {
        try {
            String tokenEmail = getEmailFromToken(token);
            return (email.equals(tokenEmail) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate refresh token
     */
    public boolean validateRefreshToken(String refreshToken) {
        try {
            Claims claims = getAllClaimsFromToken(refreshToken);
            String tokenType = (String) claims.get("type");
            return "refresh".equals(tokenType) && !isTokenExpired(refreshToken);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Refresh access token using refresh token
     */
    public String refreshAccessToken(String refreshToken) {
        try {
            if (validateRefreshToken(refreshToken)) {
                String email = getEmailFromToken(refreshToken);
                return generateToken(email);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get token expiration time in seconds
     */
    public long getExpirationTime() {
        return jwtExpirationInMs;
    }

    /**
     * Get refresh token expiration time in seconds
     */
    public long getRefreshExpirationTime() {
        return refreshTokenExpirationInMs;
    }

    /**
     * Extract token from Authorization header
     */
    public String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    /**
     * Check if token will expire soon (within next 5 minutes)
     */
    public boolean isTokenExpiringSoon(String token) {
        try {
            Date expirationDate = getExpirationDateFromToken(token);
            Date now = new Date();
            long timeDiff = expirationDate.getTime() - now.getTime();
            // Return true if token expires within 5 minutes (300,000 milliseconds)
            return timeDiff < 300000;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Get remaining time for token in milliseconds
     */
    public long getRemainingTokenTime(String token) {
        try {
            Date expirationDate = getExpirationDateFromToken(token);
            Date now = new Date();
            return Math.max(0, expirationDate.getTime() - now.getTime());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Invalidate token (for logout functionality)
     * Note: In a production environment, you might want to maintain a blacklist
     * of invalidated tokens in Redis or database
     */
    public boolean invalidateToken(String token) {
        // For now, we just validate the token structure
        // In production, add token to blacklist
        return validateJwtToken(token);
    }

    /**
     * Generate password reset token
     */
    public String generatePasswordResetToken(String email) {
        Date expiryDate = new Date((new Date()).getTime() + 3600000); // 1 hour

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("type", "password_reset");

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Validate password reset token
     */
    public boolean validatePasswordResetToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            String tokenType = (String) claims.get("type");
            return "password_reset".equals(tokenType) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generate email verification token
     */
    public String generateEmailVerificationToken(String email) {
        Date expiryDate = new Date((new Date()).getTime() + 86400000); // 24 hours

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("type", "email_verification");

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Validate email verification token
     */
    public boolean validateEmailVerificationToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            String tokenType = (String) claims.get("type");
            return "email_verification".equals(tokenType) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}