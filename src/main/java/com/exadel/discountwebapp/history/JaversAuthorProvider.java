package com.exadel.discountwebapp.history;

import lombok.NoArgsConstructor;
import org.javers.spring.auditable.AuthorProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
class SpringSecurityAuthorProvider implements AuthorProvider {
    @Override
    public String provide() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            return "Unauthenticated";
        }

        return auth.getName();
    }
}
