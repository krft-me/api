package me.krft.api.service.mapper;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.ApplicationUserOffer;
import me.krft.api.domain.Order;
import me.krft.api.domain.Review;
import me.krft.api.service.dto.ApplicationUserOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserOfferMapper implements EntityDTOMapper<ApplicationUserOffer, ApplicationUserOfferDTO> {

    private final ShowcaseMapper showcaseMapper;

    private final OrderMapper orderMapper;

    private final TagMapper tagMapper;

    private final ApplicationUserMapper applicationUserMapper;

    private final OfferMapper offerMapper;

    @Autowired
    public ApplicationUserOfferMapper(
        @Lazy ShowcaseMapper showcaseMapper,
        @Lazy OrderMapper orderMapper,
        @Lazy TagMapper tagMapper,
        @Lazy ApplicationUserMapper applicationUserMapper,
        @Lazy OfferMapper offerMapper
    ) {
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
            .price(transformPrice(entity.getPrice()))
            .active(entity.getActive())
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

    public ApplicationUserOfferDTO toDTOCard(ApplicationUserOffer applicationUserOffer) {
        Set<Review> reviews = getReviews(applicationUserOffer.getOrders());
        return ApplicationUserOfferDTO
            .builder()
            .id(applicationUserOffer.getId())
            .price(transformPrice(applicationUserOffer.getPrice()))
            .description(applicationUserOffer.getDescription())
            .rating(getRating(reviews))
            .numberOfReviews(reviews.size())
            .tags(this.tagMapper.toDTOLabel(applicationUserOffer.getTags()))
            .provider(this.applicationUserMapper.toDTOUsernameCityName(applicationUserOffer.getProvider()))
            .offer(this.offerMapper.toDTOMachineName(applicationUserOffer.getOffer()))
            .showcases(this.showcaseMapper.toDTOImageId(applicationUserOffer.getShowcases()))
            .build();
    }

    private Set<Review> getReviews(Set<Order> orders) {
        return orders.stream().map(Order::getReview).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private Double getRating(Set<Review> reviews) {
        return reviews.stream().mapToInt(Review::getRating).average().stream().map(Math::round).findFirst().orElse(0) / 10;
    }

    private Double transformPrice(Integer price) {
        return price / 100.0;
    }
}
