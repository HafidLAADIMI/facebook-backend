package facebook.backend.backend.configuration;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import facebook.backend.backend.service.JwtService;
import facebook.backend.backend.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private ApplicationContext context;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        // here we are going to check if the header contain jwt token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            // here we are going to extract the username from the token
            username = JwtService.extractUserName(token);
        }

        // here we are going to check if the username exists or if it is not already
        // authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // getting the user details using the context of MyUserDetailsService class
            UserDetails userdetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if (JwtService.validateToken(token, userdetails)) {
                // creating new details to authentication
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails,
                        null, userdetails.getAuthorities());
                // updating the authToken
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // updating the securityContext authentication
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // continue the authentication to the others filter
        filterChain.doFilter(request, response);

    }

}
