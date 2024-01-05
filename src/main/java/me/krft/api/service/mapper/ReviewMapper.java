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

    private final OrderMapper orderMapper;

    @Autowired
    public ReviewMapper(@Lazy OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
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
            .order(this.orderMapper.toDTOId(entity.getOrder()))
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

    public ReviewDTO toDTOCard(Review review) {
        return ReviewDTO
            .builder()
            .rating(review.getRating() / 10.0)
            .comment(review.getComment())
            .order(this.orderMapper.toReviewDTOCard(review.getOrder()))
            .build();
    }
}
