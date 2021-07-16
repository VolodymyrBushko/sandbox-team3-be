package com.exadel.discountwebapp.history;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Primary
class JaversAuthorProvider implements org.javers.spring.auditable.AuthorProvider {

    @Override
    public String provide() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Bean
    public JaversAuthorProvider provideJaversAuthor() {
        return new JaversAuthorProvider();
    }
}

