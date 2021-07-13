package com.exadel.discountwebapp.history;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.security.Principal;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Primary
class JaversAuthorProvider implements org.javers.spring.auditable.AuthorProvider {
    private Principal principal;

    @Override
    public String provide() {
        return principal.getName();
    }

    @Bean
    public JaversAuthorProvider provideJaversAuthor() {
        return new JaversAuthorProvider();
    }
}

