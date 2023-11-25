package me.krft.api.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import me.krft.api.domain.Review;
import me.krft.api.service.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper implements EntityDTOMapper<Review, ReviewDTO> {

    @Autowired
    private ApplicationUserOfferMapper applicationUserOfferMapper;

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
            .offer(this.applicationUserOfferMapper.toDTO(entity.getOffer()))
            .build();
    }

    public Set<ReviewDTO> toDTO(Set<Review> entities) {
        return entities.stream().map(this::toDTO).collect(Collectors.toSet());
    }
}
