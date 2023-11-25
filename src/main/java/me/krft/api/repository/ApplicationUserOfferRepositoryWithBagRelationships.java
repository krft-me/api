package me.krft.api.repository;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUserOffer;
import org.springframework.data.domain.Page;

public interface ApplicationUserOfferRepositoryWithBagRelationships {
    Optional<ApplicationUserOffer> fetchBagRelationships(Optional<ApplicationUserOffer> applicationUserOffer);

    List<ApplicationUserOffer> fetchBagRelationships(List<ApplicationUserOffer> applicationUserOffers);

    Page<ApplicationUserOffer> fetchBagRelationships(Page<ApplicationUserOffer> applicationUserOffers);
}
