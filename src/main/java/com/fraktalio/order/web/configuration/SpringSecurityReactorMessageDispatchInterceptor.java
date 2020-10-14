package com.fraktalio.order.web.configuration;

import com.fraktalio.order.command.api.AuditEntry;
import org.axonframework.extensions.reactor.messaging.ReactorMessageDispatchInterceptor;
import org.axonframework.messaging.Message;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringSecurityReactorMessageDispatchInterceptor<M extends Message<?>>
        implements ReactorMessageDispatchInterceptor<M> {

    private static final String ANONYMOUS = "anonymous";

    @SuppressWarnings("unchecked")
    @Override
    public Mono<M> intercept(Mono<M> message) {
        return ReactiveSecurityContextHolder.getContext().defaultIfEmpty(
                new SecurityContextImpl(
                        new AnonymousAuthenticationToken(
                                ANONYMOUS,
                                ANONYMOUS,
                                Collections.singletonList((GrantedAuthority) () -> ANONYMOUS)
                        )
                )
        ).map(SecurityContext::getAuthentication).flatMap(authentication -> message
                .map(m -> (M) m.andMetaData(Map.of("auditEntry",
                                                   new AuditEntry(
                                                           authentication
                                                                   .getPrincipal() instanceof User ? ((User) authentication
                                                                   .getPrincipal())
                                                                   .getUsername() : authentication
                                                                   .getPrincipal()
                                                                   .toString(),
                                                           Calendar.getInstance()
                                                                   .getTime(),
                                                           authentication
                                                                   .getAuthorities()
                                                                   .stream()
                                                                   .map(GrantedAuthority::getAuthority)
                                                                   .collect(
                                                                           Collectors
                                                                                   .toList()
                                                                   )
                                                   )
                                            )
                     )
                )
        );
    }
}
