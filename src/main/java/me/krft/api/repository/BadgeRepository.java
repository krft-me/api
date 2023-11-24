package me.krft.api.repository;

import me.krft.api.domain.Badge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Badge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {}
