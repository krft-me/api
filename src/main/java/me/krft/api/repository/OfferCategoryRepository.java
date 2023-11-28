package me.krft.api.repository;

import me.krft.api.domain.OfferCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OfferCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferCategoryRepository extends JpaRepository<OfferCategory, Long> {}
