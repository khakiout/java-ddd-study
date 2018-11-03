package com.khakiout.study.ddddemo.app.config.security;

import com.khakiout.study.ddddemo.domain.entity.Permission;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        Set<Permission> permissions = new HashSet<>(Arrays.asList(Permission.values()));

        UsernamePasswordAuthenticationToken auth =
            new UsernamePasswordAuthenticationToken("testuser", null, permissions);

        return Mono.just(auth);
    }
}
