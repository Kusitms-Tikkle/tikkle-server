package com.kusitms.tikkle.configure.security.jwt;

import com.kusitms.tikkle.account.entity.enumtypes.RoleType;
import com.kusitms.tikkle.configure.response.exception.CustomExceptionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    private long tokenValidity = 100 * 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret}")
    private String secretKey;

    public String createToken(String email, RoleType role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);
        Date now = new Date();

        Date validity = new Date(now.getTime() + tokenValidity);

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }

    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserName(token));
        if(userDetails == null) return null;
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-ACCESS-TOKEN");
    }

    public boolean validateToken(String jwtToken, HttpServletRequest req) {
        try{
            if(jwtToken.isEmpty())
                throw new JwtException("empty jwtToken");
            Claims claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken).getBody();
            return !claimsJws.getExpiration().before(new Date());
        } catch (JwtException e) {
            if(jwtToken.isEmpty()) {
                req.setAttribute("exception", CustomExceptionStatus.EMPTY_JWT.getMessage());
            }
            else req.setAttribute("exception", CustomExceptionStatus.INVALID_JWT.getMessage());
            return false;
        }
    }
}
