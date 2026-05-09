package com.readstack.crud;

import com.readstack.dto.DiscoveryAddDto;
import com.readstack.validation.exception.DiscoveryExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DiscoveryValidator {

    private final DiscoveryRepository discoveryRepository;

    void validateForUpdate(Long id, DiscoveryAddDto dto) {
        validate(id, dto);
    }

    void validateForAdd(DiscoveryAddDto dto) {
        validate(null, dto);
    }

    private void validate(Long id, DiscoveryAddDto dto) {

        if (titleExists(id, dto)){
            throw new DiscoveryExistsException("title", dto.title());
        }
        if (urlExists(id, dto)){
            throw new DiscoveryExistsException("url", dto.url());
        }
    }

    private boolean urlExists(Long id, DiscoveryAddDto dto) {
        return id == null ?
                discoveryRepository.existsByUrlIgnoreCase(dto.url()) :
                discoveryRepository.existsByUrlIgnoreCaseAndIdNot(dto.url(), id);
    }

    private boolean titleExists(Long id, DiscoveryAddDto dto) {
        return id == null ?
                discoveryRepository.existsByTitleIgnoreCase(dto.title()) :
                discoveryRepository.existsByTitleIgnoreCaseAndIdNot(dto.title(), id);
    }

    //TODO: Zwrócenie jsona jak przy validation a nie string

}
