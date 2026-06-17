package com.readstack.configuration;

import com.readstack.user_crud.SecurityUser;
import com.readstack.user_crud.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

class AuditorAwareImpl implements AuditorAware<Long> {


    @Override
    public Optional<Long> getCurrentAuditor() {
       return Optional.ofNullable(SecurityContextHolder.getContext())
               .map(SecurityContext::getAuthentication)
               .filter(Authentication::isAuthenticated)
               .map(Authentication::getPrincipal)
               .map(SecurityUser.class::cast)
               .map(SecurityUser::getUser)
               .map(User::getId);

    }
}
