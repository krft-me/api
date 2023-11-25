package me.krft.api.service.mapper;

import java.util.Set;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.service.dto.ApplicationUserBadgeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserBadgeMapper implements EntityDTOMapper<ApplicationUserBadge, ApplicationUserBadgeDTO> {

    @Autowired
    private ApplicationUserMapper applicationUserMapper;

    @Autowired
    private BadgeMapper badgeMapper;

    @Override
    public ApplicationUserBadge toEntity(ApplicationUserBadgeDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ApplicationUserBadgeDTO toDTO(ApplicationUserBadge entity) {
        return ApplicationUserBadgeDTO
            .builder()
            .id(entity.getId())
            .obtainedDate(entity.getObtainedDate())
            .user(this.applicationUserMapper.toDTO(entity.getUser()))
            .badge(this.badgeMapper.toDTO(entity.getBadge()))
            .build();
    }

    public Set<ApplicationUserBadgeDTO> toDTO(Set<ApplicationUserBadge> badges) {
        return badges.stream().map(this::toDTO).collect(java.util.stream.Collectors.toSet());
    }
}
