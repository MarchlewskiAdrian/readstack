package com.readstack.crud.discovery;

import com.readstack.crud.ResourceType;
import com.readstack.validation.exception.ResourceExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DiscoveryDataValidator {

    private final DiscoveryRepository discoveryRepository;

    void requireUniqueTitle(String title) {
        requireUniqueTitle(null, title);
    }

    void requireUniqueTitle(Long discoveryId, String title) {
        if (titleExists(discoveryId, title)) {
            throw new ResourceExistsException("title", title, ResourceType.DISCOVERY);
        }
    }

    void requireUniqueUrl(String url) {
        requireUniqueUrl(null, url);
    }

    void requireUniqueUrl(Long discoveryId, String url) {
        if (urlExists(discoveryId, url)) {
            throw new ResourceExistsException("url", url, ResourceType.DISCOVERY);
        }
    }

    private boolean urlExists(Long discoveryId, String url) {
        return discoveryId == null ?
                discoveryRepository.existsByUrlIgnoreCase(url) :
                discoveryRepository.existsByUrlIgnoreCaseAndIdNot(url, discoveryId);
    }

    private boolean titleExists(Long discoveryId, String title) {
        return discoveryId == null ?
                discoveryRepository.existsByTitleIgnoreCase(title) :
                discoveryRepository.existsByTitleIgnoreCaseAndIdNot(title, discoveryId);
    }


//    //TODO: Zwrócenie jsona jak przy validation a nie string

}
