package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserOfferMapper implements EntityDTOMapper<ApplicationUserOffer, ApplicationUserOfferDTO> {

    private final ReviewMapper reviewMapper;

    private final ShowcaseMapper showcaseMapper;

    private final OrderMapper orderMapper;

    private final TagMapper tagMapper;

    private final ApplicationUserMapper applicationUserMapper;

    private final OfferMapper offerMapper;

    @Autowired
    public ApplicationUserOfferMapper(
        @Lazy ReviewMapper reviewMapper,
        @Lazy ShowcaseMapper showcaseMapper,
        @Lazy OrderMapper orderMapper,
        @Lazy TagMapper tagMapper,
        @Lazy ApplicationUserMapper applicationUserMapper,
        @Lazy OfferMapper offerMapper
    ) {
        this.reviewMapper = reviewMapper;
        this.showcaseMapper = showcaseMapper;
        this.orderMapper = orderMapper;
        this.tagMapper = tagMapper;
        this.applicationUserMapper = applicationUserMapper;
        this.offerMapper = offerMapper;
    }

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
            .reviews(this.reviewMapper.toDTOId(entity.getReviews()))
            .showcases(this.showcaseMapper.toDTOId(entity.getShowcases()))
            .orders(this.orderMapper.toDTOId(entity.getOrders()))
            .tags(this.tagMapper.toDTOId(entity.getTags()))
            .provider(this.applicationUserMapper.toDTOId(entity.getProvider()))
            .offer(this.offerMapper.toDTOId(entity.getOffer()))
            .build();
    }

    @Override
    public ApplicationUserOfferDTO toDTOId(ApplicationUserOffer entity) {
        return ApplicationUserOfferDTO.builder().id(entity.getId()).build();
    }

    public Set<ApplicationUserOfferDTO> toDTO(Set<ApplicationUserOffer> offers) {
        return offers.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<ApplicationUserOfferDTO> toDTOId(Set<ApplicationUserOffer> offers) {
        return offers.stream().map(this::toDTOId).collect(Collectors.toSet());
    }
}
