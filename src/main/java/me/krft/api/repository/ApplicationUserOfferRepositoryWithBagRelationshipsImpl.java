package me.krft.api.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.krft.api.domain.ApplicationUserOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ApplicationUserOfferRepositoryWithBagRelationshipsImpl implements ApplicationUserOfferRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ApplicationUserOffer> fetchBagRelationships(Optional<ApplicationUserOffer> applicationUserOffer) {
        return applicationUserOffer.map(this::fetchTags);
    }

    @Override
    public Page<ApplicationUserOffer> fetchBagRelationships(Page<ApplicationUserOffer> applicationUserOffers) {
        return new PageImpl<>(
            fetchBagRelationships(applicationUserOffers.getContent()),
            applicationUserOffers.getPageable(),
            applicationUserOffers.getTotalElements()
        );
    }

    @Override
    public List<ApplicationUserOffer> fetchBagRelationships(List<ApplicationUserOffer> applicationUserOffers) {
        return Optional.of(applicationUserOffers).map(this::fetchTags).orElse(Collections.emptyList());
    }

    ApplicationUserOffer fetchTags(ApplicationUserOffer result) {
        return entityManager
            .createQuery(
                "select applicationUserOffer from ApplicationUserOffer applicationUserOffer left join fetch applicationUserOffer.tags where applicationUserOffer.id = :id",
                ApplicationUserOffer.class
            )
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<ApplicationUserOffer> fetchTags(List<ApplicationUserOffer> applicationUserOffers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, applicationUserOffers.size()).forEach(index -> order.put(applicationUserOffers.get(index).getId(), index));
        List<ApplicationUserOffer> result = entityManager
            .createQuery(
                "select applicationUserOffer from ApplicationUserOffer applicationUserOffer left join fetch applicationUserOffer.tags where applicationUserOffer in :applicationUserOffers",
                ApplicationUserOffer.class
            )
            .setParameter("applicationUserOffers", applicationUserOffers)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
