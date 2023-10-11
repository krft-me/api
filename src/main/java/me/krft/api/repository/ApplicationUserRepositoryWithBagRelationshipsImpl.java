package me.krft.api.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import me.krft.api.domain.ApplicationUser;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ApplicationUserRepositoryWithBagRelationshipsImpl implements ApplicationUserRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<ApplicationUser> fetchBagRelationships(Optional<ApplicationUser> applicationUser) {
        return applicationUser.map(this::fetchFavoriteApplicationUsers).map(this::fetchFavoriteOffers);
    }

    @Override
    public Page<ApplicationUser> fetchBagRelationships(Page<ApplicationUser> applicationUsers) {
        return new PageImpl<>(
            fetchBagRelationships(applicationUsers.getContent()),
            applicationUsers.getPageable(),
            applicationUsers.getTotalElements()
        );
    }

    @Override
    public List<ApplicationUser> fetchBagRelationships(List<ApplicationUser> applicationUsers) {
        return Optional
            .of(applicationUsers)
            .map(this::fetchFavoriteApplicationUsers)
            .map(this::fetchFavoriteOffers)
            .orElse(Collections.emptyList());
    }

    ApplicationUser fetchFavoriteApplicationUsers(ApplicationUser result) {
        return entityManager
            .createQuery(
                "select applicationUser from ApplicationUser applicationUser left join fetch applicationUser.favoriteApplicationUsers where applicationUser is :applicationUser",
                ApplicationUser.class
            )
            .setParameter("applicationUser", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ApplicationUser> fetchFavoriteApplicationUsers(List<ApplicationUser> applicationUsers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, applicationUsers.size()).forEach(index -> order.put(applicationUsers.get(index).getId(), index));
        List<ApplicationUser> result = entityManager
            .createQuery(
                "select distinct applicationUser from ApplicationUser applicationUser left join fetch applicationUser.favoriteApplicationUsers where applicationUser in :applicationUsers",
                ApplicationUser.class
            )
            .setParameter("applicationUsers", applicationUsers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    ApplicationUser fetchFavoriteOffers(ApplicationUser result) {
        return entityManager
            .createQuery(
                "select applicationUser from ApplicationUser applicationUser left join fetch applicationUser.favoriteOffers where applicationUser is :applicationUser",
                ApplicationUser.class
            )
            .setParameter("applicationUser", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ApplicationUser> fetchFavoriteOffers(List<ApplicationUser> applicationUsers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, applicationUsers.size()).forEach(index -> order.put(applicationUsers.get(index).getId(), index));
        List<ApplicationUser> result = entityManager
            .createQuery(
                "select distinct applicationUser from ApplicationUser applicationUser left join fetch applicationUser.favoriteOffers where applicationUser in :applicationUsers",
                ApplicationUser.class
            )
            .setParameter("applicationUsers", applicationUsers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
