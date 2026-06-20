package com.readstack.crud.vote;

import com.readstack.crud.discovery.Discovery;
import com.readstack.dto.VoteAddDto;
import com.readstack.dto.VoteGetDto;
import com.readstack.crud.user.User;

class VoteMapper {
    public static Vote mapAddDtoToEntity(VoteAddDto dto,
                                         User user,
                                         Discovery discovery) {
        Vote entity = new Vote();
        entity.setUser(user);
        entity.setDiscovery(discovery);
        entity.setType(dto.type());
        return entity;
    }

    public static VoteGetDto mapEntityToGetDto(Vote entity) {
        Long userId = entity.getUser().getId();
        Long discoveryId = entity.getDiscovery().getId();

        return new VoteGetDto(
                entity.getId(),
                userId,
                discoveryId,
                entity.getType(),
                entity.getAudit()
                );
    }
}
