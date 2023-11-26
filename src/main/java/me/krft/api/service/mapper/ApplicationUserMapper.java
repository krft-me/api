package me.krft.api.service.mapper;

import java.util.Set;
import me.krft.api.domain.ApplicationUser;
import me.krft.api.service.dto.ApplicationUserDTO;
import me.krft.api.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserMapper implements EntityDTOMapper<ApplicationUser, ApplicationUserDTO> {

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    private final ApplicationUserBadgeMapper applicationUserBadgeMapper;

    private final OrderMapper orderMapper;

    private final CityMapper cityMapper;

    @Autowired
    public ApplicationUserMapper(
        @Lazy ApplicationUserOfferMapper applicationUserOfferMapper,
        @Lazy ApplicationUserBadgeMapper applicationUserBadgeMapper,
        @Lazy OrderMapper orderMapper,
        @Lazy CityMapper cityMapper
    ) {
        this.applicationUserOfferMapper = applicationUserOfferMapper;
        this.applicationUserBadgeMapper = applicationUserBadgeMapper;
        this.orderMapper = orderMapper;
        this.cityMapper = cityMapper;
    }

    @Override
    public ApplicationUser toEntity(ApplicationUserDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ApplicationUserDTO toDTO(ApplicationUser entity) {
        return ApplicationUserDTO
            .builder()
            .id(entity.getId())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .username(entity.getUsername())
            .internalUser(new UserDTO(entity.getInternalUser()))
            .offers(this.applicationUserOfferMapper.toDTOId(entity.getOffers()))
            .badges(this.applicationUserBadgeMapper.toDTOId(entity.getBadges()))
            .orders(this.orderMapper.toDTOId(entity.getOrders()))
            .city(this.cityMapper.toDTOId(entity.getCity()))
            .build();
    }

    @Override
    public ApplicationUserDTO toDTOId(ApplicationUser entity) {
        return ApplicationUserDTO.builder().id(entity.getId()).build();
    }

    public Set<ApplicationUserDTO> toDTO(Set<ApplicationUser> entities) {
        return entities.stream().map(this::toDTO).collect(java.util.stream.Collectors.toSet());
    }

    public Set<ApplicationUserDTO> toDTOId(Set<ApplicationUser> entities) {
        return entities.stream().map(this::toDTOId).collect(java.util.stream.Collectors.toSet());
    }
}
