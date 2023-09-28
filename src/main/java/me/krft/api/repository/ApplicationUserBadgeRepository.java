package me.krft.api.repository;

import me.krft.api.domain.ApplicationUserBadge;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationUserBadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationUserBadgeRepository extends JpaRepository<ApplicationUserBadge, Long> {}
