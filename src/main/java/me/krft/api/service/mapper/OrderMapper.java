package me.krft.api.service.mapper;

import java.util.Set;
import me.krft.api.domain.Order;
import me.krft.api.service.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper implements EntityDTOMapper<Order, OrderDTO> {

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    @Autowired
    public OrderMapper(@Lazy ApplicationUserOfferMapper applicationUserOfferMapper) {
        this.applicationUserOfferMapper = applicationUserOfferMapper;
    }

    @Override
    public Order toEntity(OrderDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OrderDTO toDTO(Order entity) {
        return OrderDTO
            .builder()
            .id(entity.getId())
            .date(entity.getDate())
            .state(entity.getState())
            .offer(this.applicationUserOfferMapper.toDTOId(entity.getOffer()))
            .build();
    }

    @Override
    public OrderDTO toDTOId(Order entity) {
        return OrderDTO.builder().id(entity.getId()).build();
    }

    public Set<OrderDTO> toDTO(Set<Order> entities) {
        return entities.stream().map(this::toDTO).collect(java.util.stream.Collectors.toSet());
    }

    public Set<OrderDTO> toDTOId(Set<Order> entities) {
        return entities.stream().map(this::toDTOId).collect(java.util.stream.Collectors.toSet());
    }
}
