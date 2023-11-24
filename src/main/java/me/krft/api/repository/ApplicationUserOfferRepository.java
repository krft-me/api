package me.krft.api.repository;

import me.krft.api.domain.ApplicationUserOffer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationUserOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationUserOfferRepository extends JpaRepository<ApplicationUserOffer, Long> {}
