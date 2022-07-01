package com.filmreview.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    public static final String SECRET = "c97916ae-69ce-4f76-b4fd-10f2a3d9a701";
    public static final String HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        var attribute = request.getHeader(HEADER);

        if(Objects.isNull(attribute) || !attribute.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        var token = attribute.replace(BEARER,"");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        var user = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(token)
                .getSubject();

        return Objects.nonNull(user)
                ? new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>())
                : null;
    }
}
