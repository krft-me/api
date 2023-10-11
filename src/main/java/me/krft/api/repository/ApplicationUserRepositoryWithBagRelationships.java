package me.krft.api.repository;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUser;
import org.springframework.data.domain.Page;

public interface ApplicationUserRepositoryWithBagRelationships {
    Optional<ApplicationUser> fetchBagRelationships(Optional<ApplicationUser> applicationUser);

    List<ApplicationUser> fetchBagRelationships(List<ApplicationUser> applicationUsers);

    Page<ApplicationUser> fetchBagRelationships(Page<ApplicationUser> applicationUsers);
}
