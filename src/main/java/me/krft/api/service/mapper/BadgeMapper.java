package me.krft.api.service.mapper;

import me.krft.api.domain.Badge;
import me.krft.api.service.dto.BadgeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BadgeMapper implements EntityDTOMapper<Badge, BadgeDTO> {

    @Autowired
    private ApplicationUserBadgeMapper applicationUserBadgeMapper;

    @Override
    public Badge toEntity(BadgeDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BadgeDTO toDTO(Badge entity) {
        return BadgeDTO
            .builder()
            .id(entity.getId())
            .label(entity.getLabel())
            .picture(entity.getPicture())
            .users(this.applicationUserBadgeMapper.toDTO(entity.getUsers()))
            .build();
    }
}
