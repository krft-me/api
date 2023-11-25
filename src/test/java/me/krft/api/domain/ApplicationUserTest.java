package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserBadgeTestSamples.*;
import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.ApplicationUserTestSamples.*;
import static me.krft.api.domain.CityTestSamples.*;
import static me.krft.api.domain.OrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUser.class);
        ApplicationUser applicationUser1 = getApplicationUserSample1();
        ApplicationUser applicationUser2 = new ApplicationUser();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);

        applicationUser2.setId(applicationUser1.getId());
        assertThat(applicationUser1).isEqualTo(applicationUser2);

        applicationUser2 = getApplicationUserSample2();
        assertThat(applicationUser1).isNotEqualTo(applicationUser2);
    }

    @Test
    void offersTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        ApplicationUserOffer applicationUserOfferBack = getApplicationUserOfferRandomSampleGenerator();

        applicationUser.addOffers(applicationUserOfferBack);
        assertThat(applicationUser.getOffers()).containsOnly(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getProvider()).isEqualTo(applicationUser);

        applicationUser.removeOffers(applicationUserOfferBack);
        assertThat(applicationUser.getOffers()).doesNotContain(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getProvider()).isNull();

        applicationUser.offers(new HashSet<>(Set.of(applicationUserOfferBack)));
        assertThat(applicationUser.getOffers()).containsOnly(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getProvider()).isEqualTo(applicationUser);

        applicationUser.setOffers(new HashSet<>());
        assertThat(applicationUser.getOffers()).doesNotContain(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getProvider()).isNull();
    }

    @Test
    void badgesTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        ApplicationUserBadge applicationUserBadgeBack = getApplicationUserBadgeRandomSampleGenerator();

        applicationUser.addBadges(applicationUserBadgeBack);
        assertThat(applicationUser.getBadges()).containsOnly(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getUser()).isEqualTo(applicationUser);

        applicationUser.removeBadges(applicationUserBadgeBack);
        assertThat(applicationUser.getBadges()).doesNotContain(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getUser()).isNull();

        applicationUser.badges(new HashSet<>(Set.of(applicationUserBadgeBack)));
        assertThat(applicationUser.getBadges()).containsOnly(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getUser()).isEqualTo(applicationUser);

        applicationUser.setBadges(new HashSet<>());
        assertThat(applicationUser.getBadges()).doesNotContain(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getUser()).isNull();
    }

    @Test
    void ordersTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        applicationUser.addOrders(orderBack);
        assertThat(applicationUser.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(applicationUser);

        applicationUser.removeOrders(orderBack);
        assertThat(applicationUser.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getCustomer()).isNull();

        applicationUser.orders(new HashSet<>(Set.of(orderBack)));
        assertThat(applicationUser.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getCustomer()).isEqualTo(applicationUser);

        applicationUser.setOrders(new HashSet<>());
        assertThat(applicationUser.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getCustomer()).isNull();
    }

    @Test
    void cityTest() throws Exception {
        ApplicationUser applicationUser = getApplicationUserRandomSampleGenerator();
        City cityBack = getCityRandomSampleGenerator();

        applicationUser.setCity(cityBack);
        assertThat(applicationUser.getCity()).isEqualTo(cityBack);

        applicationUser.city(null);
        assertThat(applicationUser.getCity()).isNull();
    }
}
