package com.test.movie.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.test.movie.utils.JwtUtil;
import com.test.movie.utils.StringUtil;

import io.jsonwebtoken.JwtException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationApiFilter extends GenericFilterBean {

   @Getter(value = AccessLevel.PRIVATE)
   @Autowired
   private JwtUtil jwtUtil;

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

      final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

      final String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

      try {
         if (StringUtil.notNullOrEmpty(authHeader, true) && authHeader.startsWith("Bearer ")) {

            final String token = authHeader.substring(7);
            log.debug("jwtToken: {}", token);
            Map<String, Object> mapClaims = this.getJwtUtil().validateJwt(token);
            httpServletRequest.setAttribute("username", mapClaims.get("username"));
          
            SecurityContextHolder.getContext().setAuthentication(
            		new UsernamePasswordAuthenticationToken(mapClaims.get("username").toString(), null, new ArrayList<>()));

         }
      } catch (JwtException jetex) {
         log.error("UNAUTHORIZED");
         log.error("{}: {} - {}", jetex.getClass().getSimpleName(), jetex.getMessage(), jetex.getLocalizedMessage());
         httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
         httpServletResponse.setContentType("application/json");
         httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
         httpServletResponse.getWriter().println("The server could not verify that you are authorized to access");
         httpServletResponse.getWriter().flush();
      }
      chain.doFilter(request, response);
   }
}
