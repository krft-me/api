package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Review;
import me.krft.api.service.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper implements EntityDTOMapper<Review, ReviewDTO> {

    private final ApplicationUserOfferMapper applicationUserOfferMapper;

    @Autowired
    public ReviewMapper(@Lazy ApplicationUserOfferMapper applicationUserOfferMapper) {
        this.applicationUserOfferMapper = applicationUserOfferMapper;
    }

    @Override
    public Review toEntity(ReviewDTO dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReviewDTO toDTO(Review entity) {
        return ReviewDTO
            .builder()
            .id(entity.getId())
            .rating(entity.getRating())
            .comment(entity.getComment())
            .offer(this.applicationUserOfferMapper.toDTOId(entity.getOffer()))
            .build();
    }

    @Override
    public ReviewDTO toDTOId(Review entity) {
        return ReviewDTO.builder().id(entity.getId()).build();
    }

    public Set<ReviewDTO> toDTO(Set<Review> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }

    public Set<ReviewDTO> toDTOId(Set<Review> entities) {
        return entities.stream().map(this::toDTOId).collect(Collectors.toSet());
    }

    public ReviewDTO toDTORating(Review review) {
        return ReviewDTO.builder().id(review.getId()).rating(review.getRating()).build();
    }

    public Set<ReviewDTO> toDTORating(Set<Review> reviews) {
        return reviews.stream().map(this::toDTORating).collect(Collectors.toSet());
    }
}
