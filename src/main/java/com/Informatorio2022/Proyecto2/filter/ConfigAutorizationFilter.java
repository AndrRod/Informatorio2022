package com.Informatorio2022.Proyecto2.filter;
import com.Informatorio2022.Proyecto2.exception.BadRequestException;
import com.Informatorio2022.Proyecto2.exception.MessageInfo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class ConfigAutorizationFilter extends OncePerRequestFilter {
         @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         List listPaths = List.of("/auth/login", "/auth/refresh", "/auth/register", "/auth/accessdenied", "/auth/logout", "/auth/logoutsuccess");
        if(listPaths.contains(request.getServletPath())){
            filterChain.doFilter(request, response);
        }else{

            String autorizacionHeader = request.getHeader(AUTHORIZATION);
            if(autorizacionHeader != null && autorizacionHeader.startsWith("Bearer ")) {
                try {
                    String token = autorizacionHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String email = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("role").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
//                    aca determina que que recursos tiene acceso dep el rol y usuario
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), new MessageInfo(exception.getMessage(), 403, request.getRequestURI()));
                }
            }else {
                response.setStatus(UNAUTHORIZED.value());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), new MessageInfo("The resource cannot be accessed, because you arn't logged in or you don't hase use the token.", 401, request.getRequestURI()));
            }
        }
    }
}
