package me.krft.api.service.mapper;

import java.util.Set;
import me.krft.api.domain.ApplicationUser;
import me.krft.api.service.dto.ApplicationUserDTO;
import me.krft.api.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserMapper implements EntityDTOMapper<ApplicationUser, ApplicationUserDTO> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationUserOfferMapper applicationUserOfferMapper;

    @Autowired
    private ApplicationUserBadgeMapper applicationUserBadgeMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CityMapper cityMapper;

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
            .offers(this.applicationUserOfferMapper.toDTO(entity.getOffers()))
            .badges(this.applicationUserBadgeMapper.toDTO(entity.getBadges()))
            .orders(this.orderMapper.toDTO(entity.getOrders()))
            .city(this.cityMapper.toDTO(entity.getCity()))
            .build();
    }

    public Set<ApplicationUserDTO> toDTO(Set<ApplicationUser> entities) {
        return entities.stream().map(this::toDTO).collect(java.util.stream.Collectors.toSet());
    }
}
