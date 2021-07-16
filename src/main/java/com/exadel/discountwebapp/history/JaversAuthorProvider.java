package com.exadel.discountwebapp.history;

import com.exadel.discountwebapp.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.nio.file.Path;
import java.security.Principal;

@Component
@NoArgsConstructor
@AllArgsConstructor
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

