package me.krft.api.service.mapper;

import java.util.Set;
import me.krft.api.domain.ApplicationUserBadge;
import me.krft.api.service.dto.ApplicationUserBadgeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserBadgeMapper implements EntityDTOMapper<ApplicationUserBadge, ApplicationUserBadgeDTO> {

    private final ApplicationUserMapper applicationUserMapper;

    private final BadgeMapper badgeMapper;

    @Autowired
    public ApplicationUserBadgeMapper(@Lazy ApplicationUserMapper applicationUserMapper, @Lazy BadgeMapper badgeMapper) {
        this.applicationUserMapper = applicationUserMapper;
        this.badgeMapper = badgeMapper;
    }

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
            .user(this.applicationUserMapper.toDTOId(entity.getUser()))
            .badge(this.badgeMapper.toDTOId(entity.getBadge()))
            .build();
    }

    @Override
    public ApplicationUserBadgeDTO toDTOId(ApplicationUserBadge entity) {
        return ApplicationUserBadgeDTO.builder().id(entity.getId()).build();
    }

    public Set<ApplicationUserBadgeDTO> toDTO(Set<ApplicationUserBadge> badges) {
        return badges.stream().map(this::toDTO).collect(java.util.stream.Collectors.toSet());
    }

    public Set<ApplicationUserBadgeDTO> toDTOId(Set<ApplicationUserBadge> badges) {
        return badges.stream().map(this::toDTOId).collect(java.util.stream.Collectors.toSet());
    }
}
