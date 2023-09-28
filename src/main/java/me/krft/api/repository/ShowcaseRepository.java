package me.krft.api.repository;

import me.krft.api.domain.Showcase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Showcase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShowcaseRepository extends JpaRepository<Showcase, Long> {}
