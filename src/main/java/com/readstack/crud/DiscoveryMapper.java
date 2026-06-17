package com.readstack.crud;

import com.readstack.dto.CategoryNameDto;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;

class DiscoveryMapper {
    public static DiscoveryGetDto mapEntityToGetDto(Discovery discovery,
                                                    CategoryNameDto categoryNameDto) {
        return new DiscoveryGetDto(
                discovery.getId(),
                discovery.getTitle(),
                discovery.getUrl(),
                discovery.getDescription(),
                categoryNameDto,
                discovery.getAudit()
        );
    }

    public static Discovery mapAddDtoToEntity(DiscoveryAddDto dto,
                                              Category category) {
        Discovery entity = new Discovery();
        entity.setTitle(dto.title());
        entity.setUrl(dto.url());
        entity.setDescription(dto.description());
        entity.setCategory(category);

        return entity;
    }
}

//1. Każdy mapper odpowiada za jedną encję


//W dużych systemach (DDD / microservices / CQRS):
//mappery są „pure functions”
//service/facade składa DTO
//brak zależności między mapperami


//https://dev.to/mohamed_amine_78123694764/best-practices-for-mapping-in-spring-boot-47l4