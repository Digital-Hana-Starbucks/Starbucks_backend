package com.hanaro.starbucks.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long expireTime;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes()); // secret key 암호화
    }

    public String createToken(String userId, List<String> roleList) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roleList);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities()
        ); // 사용자 정보를 기반으로 토큰 생성
    }

    // http 요청에서 "TOKEN" 헤더 값을 읽어오는 메소드
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().before(new Date()) == false;
        }
        catch (Exception e){
            return false;
        }
    }

}
