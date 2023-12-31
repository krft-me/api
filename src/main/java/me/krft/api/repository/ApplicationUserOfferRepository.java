package me.krft.api.repository;

import java.util.List;
import java.util.Optional;
import me.krft.api.domain.ApplicationUserOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ApplicationUserOffer entity.
 *
 * When extending this class, extend ApplicationUserOfferRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface ApplicationUserOfferRepository
    extends ApplicationUserOfferRepositoryWithBagRelationships, PagingAndSortingRepository<ApplicationUserOffer, Long> {
    default Optional<ApplicationUserOffer> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ApplicationUserOffer> findAllWithEagerRelationships() {
        return this.fetchBagRelationships((List<ApplicationUserOffer>) this.findAll());
    }

    default Page<ApplicationUserOffer> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @Query
    List<ApplicationUserOffer> findByActiveTrue(Pageable pageable);
}
