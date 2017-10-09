package com.example.security;

import java.security.Key;
import java.util.Date;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.models.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Component
public class JwtTokenUtilility {
    
    // public (i.e. custom) tokens
	private static final String CLAIM_KEY_ROLE = "rol";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";
    
    private static final String ISSUER = "SeattleOpenData";

    private Key secret = MacProvider.generateKey();
    private Long expiration = 3600L; // 1 hour

    public String getUsernameFromToken(String token) {
        if (token == null) {
            // fired on login form
            return null;
        }

        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
        	System.out.println("Error message" + e.getMessage());
            username = null;
        }

        return username;
    }
/*
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        
        return created;
    }
*/
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        
        return expiration;
    }

/*
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        
        return audience;
    }
*/
    
    private Claims getClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /*
     * TODO: should be used
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }
    */

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        
        return audience;
    }

    public String generateToken(JwtUser userDetails, Device device) {
        String compactJws = Jwts.builder()
    			  .setIssuer(ISSUER)
    	    	  .setSubject(userDetails.getUsername())
    	    	  .setAudience(generateAudience(device))
    	    	  .setExpiration(generateExpirationDate())
    	    	  .setIssuedAt(new Date(System.currentTimeMillis()))
    	    	  .claim(CLAIM_KEY_ROLE, userDetails.getRole())
    	    	  .signWith(SignatureAlgorithm.HS512, secret)
    	    	  .compact();
  
    	return compactJws;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

    	// TODO: add more heavy lifting for token validation???
    	
    	return !isTokenExpired(token);
    	/*
    	JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        final Date expiration = getExpirationDateFromToken(token);
        
        
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        /*&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()) /);
       */
    	
    	
    }


}