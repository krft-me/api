package me.krft.api.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import me.krft.api.domain.Review;
import me.krft.api.repository.ReviewRepository;
import me.krft.api.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Review}.
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review save(Review review) {
        log.debug("Request to save Review : {}", review);
        return reviewRepository.save(review);
    }

    @Override
    public Review update(Review review) {
        log.debug("Request to update Review : {}", review);
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> partialUpdate(Review review) {
        log.debug("Request to partially update Review : {}", review);

        return reviewRepository
            .findById(review.getId())
            .map(existingReview -> {
                if (review.getRating() != null) {
                    existingReview.setRating(review.getRating());
                }
                if (review.getComment() != null) {
                    existingReview.setComment(review.getComment());
                }

                return existingReview;
            })
            .map(reviewRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Review> findAll() {
        log.debug("Request to get all Reviews");
        return reviewRepository.findAll();
    }

    /**
     *  Get all the reviews where Order is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Review> findAllWhereOrderIsNull() {
        log.debug("Request to get all reviews where Order is null");
        return StreamSupport
            .stream(reviewRepository.findAll().spliterator(), false)
            .filter(review -> review.getOrder() == null)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        return reviewRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.deleteById(id);
    }
}
