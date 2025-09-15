package com.app.showboxd.filters;

import com.app.showboxd.user.entity.User;
import com.app.showboxd.user.service.ShowboxdUserDetailsService;
import com.app.showboxd.auth.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class BasicAuthenticationFilter implements Filter {

    private static final String[] NON_SECURE_API_URLS = {"/showboxd/v1/auth/login", "/showboxd/v1/auth/register", "/actuator/health"};
    private static final String AUTH_HEADER = "Authorization";

    @Value("${spring.security.jwt.secret.key}")
    private String jwtSecretKey;

    private final ShowboxdUserDetailsService userDetailsService;

    public BasicAuthenticationFilter(ShowboxdUserDetailsService showboxdUserDetailsService) {
        this.userDetailsService = showboxdUserDetailsService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("BasicAuthenticationFilter initialised");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse)  servletResponse;
        HttpServletRequest request = (HttpServletRequest)  servletRequest;
        try {
            String requestedPath = (request.getRequestURI() == null) ? "" : request.getRequestURI();
            boolean isNonSecureApiRequest = isNonSecureApiRequest(requestedPath);
            if(!isNonSecureApiRequest) {
                String jwtToken = JWTUtil.extractHeaderToken(request.getHeader(AUTH_HEADER));
                Claims claims = JWTUtil.parseJwtToken(jwtToken, jwtSecretKey);
                if(StringUtils.isNotEmpty(jwtToken) || claims != null) {
                    String userEmail = JWTUtil.getUserEmailFromClaims(claims);
                    User userDetails = (User) userDetailsService.loadUserByUsername(userEmail);
                    if(JWTUtil.isTokenValid(userDetails, claims)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        log.info("loggedIn user = {}, requestedPath {}", userDetails.getEmail(), requestedPath);
                    } else {
                        log.error("Invalid auth-token/User");
                        throw new AuthenticationServiceException("Invalid auth-token/User");
                    }
                } else {
                    log.error("missing auth-token, headers =  {}", request.getHeader(AUTH_HEADER));
                    throw new AuthenticationServiceException("missing auth-token");
                }
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            log.error("BasicAuthenticationFilter error occurred: {}",e.getMessage());
            unauthorizedError(response, e.getMessage());
        }
    }

    private void unauthorizedError(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), message);
    }

    private boolean isNonSecureApiRequest(String requestedPath) {
        return ArrayUtils.containsAny(NON_SECURE_API_URLS, requestedPath);
    }

    @Override
    public void destroy() {
        log.info("BasicAuthenticationFilter destroyed");
        Filter.super.destroy();
    }
}
