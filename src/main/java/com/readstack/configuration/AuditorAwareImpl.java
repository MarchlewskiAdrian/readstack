package com.readstack.configuration;

import com.readstack.security.SecurityUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof SecurityUser securityUser)) {
            return Optional.empty();
        }
        return Optional.ofNullable(securityUser.getUser().getId());

    }
}


//@Override
//public Optional<Long> getCurrentAuditor() {
//    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//            .filter(Authentication::isAuthenticated)
//            .map(Authentication::getPrincipal)
//            .filter(SecurityUser.class::isInstance)
//            .map(SecurityUser.class::cast)
//            .map(SecurityUser::getUser)
//            .map(User::getId);
//}