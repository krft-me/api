package me.krft.api.repository;

import java.util.List;
import me.krft.api.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Review entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Long> {
    List<Review> findByOrder_Offer_Id(Long id, Pageable pageable);
}
