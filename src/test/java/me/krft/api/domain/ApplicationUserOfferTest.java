package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.ApplicationUserTestSamples.*;
import static me.krft.api.domain.OfferTestSamples.*;
import static me.krft.api.domain.OrderTestSamples.*;
import static me.krft.api.domain.ReviewTestSamples.*;
import static me.krft.api.domain.ShowcaseTestSamples.*;
import static me.krft.api.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserOfferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUserOffer.class);
        ApplicationUserOffer applicationUserOffer1 = getApplicationUserOfferSample1();
        ApplicationUserOffer applicationUserOffer2 = new ApplicationUserOffer();
        assertThat(applicationUserOffer1).isNotEqualTo(applicationUserOffer2);

        applicationUserOffer2.setId(applicationUserOffer1.getId());
        assertThat(applicationUserOffer1).isEqualTo(applicationUserOffer2);

        applicationUserOffer2 = getApplicationUserOfferSample2();
        assertThat(applicationUserOffer1).isNotEqualTo(applicationUserOffer2);
    }

    @Test
    void reviewsTest() throws Exception {
        ApplicationUserOffer applicationUserOffer = getApplicationUserOfferRandomSampleGenerator();
        Review reviewBack = getReviewRandomSampleGenerator();

        applicationUserOffer.addReviews(reviewBack);
        assertThat(applicationUserOffer.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getOffer()).isEqualTo(applicationUserOffer);

        applicationUserOffer.removeReviews(reviewBack);
        assertThat(applicationUserOffer.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getOffer()).isNull();

        applicationUserOffer.reviews(new HashSet<>(Set.of(reviewBack)));
        assertThat(applicationUserOffer.getReviews()).containsOnly(reviewBack);
        assertThat(reviewBack.getOffer()).isEqualTo(applicationUserOffer);

        applicationUserOffer.setReviews(new HashSet<>());
        assertThat(applicationUserOffer.getReviews()).doesNotContain(reviewBack);
        assertThat(reviewBack.getOffer()).isNull();
    }

    @Test
    void showcasesTest() throws Exception {
        ApplicationUserOffer applicationUserOffer = getApplicationUserOfferRandomSampleGenerator();
        Showcase showcaseBack = getShowcaseRandomSampleGenerator();

        applicationUserOffer.addShowcases(showcaseBack);
        assertThat(applicationUserOffer.getShowcases()).containsOnly(showcaseBack);
        assertThat(showcaseBack.getOffer()).isEqualTo(applicationUserOffer);

        applicationUserOffer.removeShowcases(showcaseBack);
        assertThat(applicationUserOffer.getShowcases()).doesNotContain(showcaseBack);
        assertThat(showcaseBack.getOffer()).isNull();

        applicationUserOffer.showcases(new HashSet<>(Set.of(showcaseBack)));
        assertThat(applicationUserOffer.getShowcases()).containsOnly(showcaseBack);
        assertThat(showcaseBack.getOffer()).isEqualTo(applicationUserOffer);

        applicationUserOffer.setShowcases(new HashSet<>());
        assertThat(applicationUserOffer.getShowcases()).doesNotContain(showcaseBack);
        assertThat(showcaseBack.getOffer()).isNull();
    }

    @Test
    void ordersTest() throws Exception {
        ApplicationUserOffer applicationUserOffer = getApplicationUserOfferRandomSampleGenerator();
        Order orderBack = getOrderRandomSampleGenerator();

        applicationUserOffer.addOrders(orderBack);
        assertThat(applicationUserOffer.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getOffer()).isEqualTo(applicationUserOffer);

        applicationUserOffer.removeOrders(orderBack);
        assertThat(applicationUserOffer.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getOffer()).isNull();

        applicationUserOffer.orders(new HashSet<>(Set.of(orderBack)));
        assertThat(applicationUserOffer.getOrders()).containsOnly(orderBack);
        assertThat(orderBack.getOffer()).isEqualTo(applicationUserOffer);

        applicationUserOffer.setOrders(new HashSet<>());
        assertThat(applicationUserOffer.getOrders()).doesNotContain(orderBack);
        assertThat(orderBack.getOffer()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        ApplicationUserOffer applicationUserOffer = getApplicationUserOfferRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        applicationUserOffer.addTags(tagBack);
        assertThat(applicationUserOffer.getTags()).containsOnly(tagBack);

        applicationUserOffer.removeTags(tagBack);
        assertThat(applicationUserOffer.getTags()).doesNotContain(tagBack);

        applicationUserOffer.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(applicationUserOffer.getTags()).containsOnly(tagBack);

        applicationUserOffer.setTags(new HashSet<>());
        assertThat(applicationUserOffer.getTags()).doesNotContain(tagBack);
    }

    @Test
    void providerTest() throws Exception {
        ApplicationUserOffer applicationUserOffer = getApplicationUserOfferRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        applicationUserOffer.setProvider(applicationUserBack);
        assertThat(applicationUserOffer.getProvider()).isEqualTo(applicationUserBack);

        applicationUserOffer.provider(null);
        assertThat(applicationUserOffer.getProvider()).isNull();
    }

    @Test
    void offerTest() throws Exception {
        ApplicationUserOffer applicationUserOffer = getApplicationUserOfferRandomSampleGenerator();
        Offer offerBack = getOfferRandomSampleGenerator();

        applicationUserOffer.setOffer(offerBack);
        assertThat(applicationUserOffer.getOffer()).isEqualTo(offerBack);

        applicationUserOffer.offer(null);
        assertThat(applicationUserOffer.getOffer()).isNull();
    }
}
