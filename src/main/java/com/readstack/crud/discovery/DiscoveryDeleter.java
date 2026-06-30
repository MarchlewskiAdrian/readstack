package com.readstack.crud.discovery;

import com.readstack.crud.ResourceType;
import com.readstack.crud.user.User;
import com.readstack.security.SecurityUser;
import com.readstack.validation.ResourceBusinessValidator;
import com.readstack.validation.exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryDeleter {

    private final DiscoveryRepository discoveryRepository;
    private final ResourceBusinessValidator resourceBusinessValidator;

    public void deleteById(Long discoveryId, SecurityUser securityUser) {
        Discovery discovery = discoveryRepository.findById(discoveryId)
                .orElseThrow(() -> new ResourceNotFoundException(discoveryId, ResourceType.DISCOVERY));

        User user = securityUser.getUser();
        Long creatorId = discovery.getAudit()
                .getCreator();

        resourceBusinessValidator.requireResourceOwnerOrAdmin(user, creatorId, ResourceType.DISCOVERY);

        discoveryRepository.deleteById(discoveryId);
    }

}