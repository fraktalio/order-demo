package com.fraktalio.order.web.thymeleaf;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class IndexController {

    @PreAuthorize("hasRole('MANAGER') or hasRole('CUSTOMER')")
    @GetMapping("/")
    Mono<String> orders(Model model, @AuthenticationPrincipal UserDetails user) {
        if (user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equals("ROLE_MANAGER"))) {
            return Mono.just("index-manager");
        } else {
            return Mono.just("index-customer");
        }
    }
}
