package me.krft.api.service.mapper;

import java.util.Set;
import me.krft.api.domain.Order;
import me.krft.api.service.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper implements EntityDTOMapper<Order, OrderDTO> {

    @Autowired
    private ApplicationUserOfferMapper applicationUserOfferMapper;

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
            .offer(this.applicationUserOfferMapper.toDTO(entity.getOffer()))
            .build();
    }

    public Set<OrderDTO> toDTO(Set<Order> entities) {
        return entities.stream().map(this::toDTO).collect(java.util.stream.Collectors.toSet());
    }
}
