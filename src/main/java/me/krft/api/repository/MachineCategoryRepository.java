package me.krft.api.repository;

import me.krft.api.domain.MachineCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MachineCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MachineCategoryRepository extends JpaRepository<MachineCategory, Long> {}
