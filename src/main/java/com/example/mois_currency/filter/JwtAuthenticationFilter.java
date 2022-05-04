package com.example.mois_currency.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");

        if (requestTokenHeader != null && !requestTokenHeader.isEmpty()) {
            if (verifyUserByToken(requestTokenHeader)) {
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    final RememberMeAuthenticationToken rememberMeAuthenticationToken = new RememberMeAuthenticationToken("user-id-key", requestTokenHeader, null);
                    rememberMeAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(rememberMeAuthenticationToken);
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    private boolean verifyUserByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<Boolean> request = new HttpEntity<>(headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/rest/api/currency/verify");
        ResponseEntity<Boolean> response = new RestTemplate()
                .exchange(uriBuilder.toUriString(), HttpMethod.GET, request, Boolean.class);
        return Objects.requireNonNull(response.getBody());
    }
}
