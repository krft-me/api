package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserOfferMapper implements EntityDTOMapper<ApplicationUserOffer, ApplicationUserOfferDTO> {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ShowcaseMapper showcaseMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ApplicationUserMapper applicationUserMapper;

    @Autowired
    private OfferMapper offerMapper;

    @Override
    public ApplicationUserOffer toEntity(ApplicationUserOfferDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ApplicationUserOfferDTO toDTO(ApplicationUserOffer entity) {
        return ApplicationUserOfferDTO
            .builder()
            .id(entity.getId())
            .description(entity.getDescription())
            .price(entity.getPrice())
            .active(entity.getActive())
            .reviews(this.reviewMapper.toDTO(entity.getReviews()))
            .showcases(this.showcaseMapper.toDTO(entity.getShowcases()))
            .orders(this.orderMapper.toDTO(entity.getOrders()))
            .tags(this.tagMapper.toDTO(entity.getTags()))
            .provider(this.applicationUserMapper.toDTO(entity.getProvider()))
            .offer(this.offerMapper.toDTO(entity.getOffer()))
            .build();
    }

    public Set<ApplicationUserOfferDTO> toDTO(Set<ApplicationUserOffer> offers) {
        return offers.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
