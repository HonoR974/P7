package com.bibliotheque.configuration;

import com.bibliotheque.service.SecretService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CsrfTokenRepository jwtCsrfTokenRepository;

    @Autowired
    SecretService secretService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }


    // ordered so we can use binary search below
    private String[] ignoreCsrfAntMatchers = { "/dynamic-builder-compress", "/dynamic-builder-general", "/dynamic-builder-specific", "/set-secrets" };

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        /**
        http.addFilterAfter(new JwtCsrfValidatorFilter(), CsrfFilter.class)
            .csrf()
            .csrfTokenRepository(jwtCsrfTokenRepository)
            .ignoringAntMatchers(ignoreCsrfAntMatchers)
            .and()
            .authorizeRequests()
            .antMatchers("/security/register")
            .permitAll();
            */


        /**
         *
         http
         .authorizeRequests()
         .antMatchers("/security/**").permitAll()
         .anyRequest().authenticated()
         .and()
         .formLogin()
         .loginPage("/security/login")
         .permitAll()
         .and()
         .logout()
         .permitAll();
         */

        http.csrf().disable().cors().disable().authorizeRequests().antMatchers("/authenticate", "/register").permitAll();



    }

    private class JwtCsrfValidatorFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            // NOTE: A real implementation should have a nonce cache so the token cannot be reused

            CsrfToken token = (CsrfToken) request.getAttribute("_csrf");

            if (
            // only care if it's a POST
            "POST".equals(request.getMethod()) &&
            // ignore if the request path is in our list
                Arrays.binarySearch(ignoreCsrfAntMatchers, request.getServletPath()) < 0 &&
                // make sure we have a token
                token != null) {
                // CsrfFilter already made sure the token matched. Here, we'll make sure it's not expired
                try {
                    Jwts.parser()
                        .setSigningKeyResolver(secretService.getSigningKeyResolver())
                        .parseClaimsJws(token.getToken());
                } catch (JwtException e) {
                    // most likely an ExpiredJwtException, but this will handle any
                    request.setAttribute("exception", e);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("expired-jwt");
                    dispatcher.forward(request, response);
                }
            }

            filterChain.doFilter(request, response);
        }
    }
}
