package com.khakiout.study.ddddemo.app.config.security;

import com.khakiout.study.ddddemo.domain.entity.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    Logger logger = LoggerFactory.getLogger(SecurityContextRepository.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {

        ServerHttpRequest request = swe.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        String jwtTokenPrefix = "bearer ";

        boolean jwtPrefixExists = authHeader != null
            && authHeader.startsWith(jwtTokenPrefix.toLowerCase());

        logger.info("Header exists? {}", jwtPrefixExists);
        if (jwtPrefixExists) {
            String authToken = authHeader.substring(jwtTokenPrefix.length());
            Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            return this.authenticationManager.authenticate(auth)
                .map((authentication) -> {
                    logger.info("Got authentication");
                    logger.info("Valid? {}",
                        authentication.getAuthorities().contains(Permission.VIEW_USERS));
                    logger.info(Permission.VIEW_USERS.toString());
                    return new SecurityContextImpl(authentication);
                });
        } else {
            return Mono.empty();
        }
    }

}
