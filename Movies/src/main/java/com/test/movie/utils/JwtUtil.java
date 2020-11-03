package com.test.movie.utils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Fernando
 *
 */

@Slf4j
@Component
public class JwtUtil {

   @Getter(value = AccessLevel.PRIVATE)
   @Value("${jwt.secret.key}")
   private String jwtSecretKey;

   // Generate Token
   public String generateJwt(String username) {

      final String jwtToken = Jwts.builder().setId(UUID.randomUUID().toString().replace("-", "")).setSubject(username).setIssuer("HEB Card API v1")
            .setIssuedAt(new Date())
           
            .signWith(SignatureAlgorithm.HS256, this.getJwtSecretKey()).compact();
      return jwtToken;
   }

   // ValidateJWT
   public Map<String, Object> validateJwt(final String jwtToken) {
      try {
         final Claims claims = Jwts.parser().setSigningKey(this.getJwtSecretKey()).parseClaimsJws(jwtToken).getBody();
         log.debug( "Claims: {}", claims);
         Map<String, Object> mapClaims = new LinkedHashMap<>();
         mapClaims.put("username", claims.getSubject());
         log.info( "Token valid for values: {}", mapClaims);

         return mapClaims;
      } catch (final SignatureException ex) {
         log.error( "Invalid JWT signature: {}", ex.getMessage());
         throw ex;
      } catch (final MalformedJwtException ex) {
         log.error( "Invalid JWT token: {}", ex.getMessage());
         throw ex;
      } catch (final ExpiredJwtException ex) {
         log.error( "Expired JWT token: {}", ex.getMessage());
         throw ex;
      } catch (final UnsupportedJwtException ex) {
         log.error( "Unsupported JWT token: {}", ex.getMessage());
         throw ex;
      } catch (final IllegalArgumentException ex) {
         log.error( "JWT claims string is empty: {}", ex.getMessage());
         throw ex;
      }
   }


}
