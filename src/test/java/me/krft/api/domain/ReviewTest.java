package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.ReviewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Review.class);
        Review review1 = getReviewSample1();
        Review review2 = new Review();
        assertThat(review1).isNotEqualTo(review2);

        review2.setId(review1.getId());
        assertThat(review1).isEqualTo(review2);

        review2 = getReviewSample2();
        assertThat(review1).isNotEqualTo(review2);
    }

    @Test
    void offerTest() throws Exception {
        Review review = getReviewRandomSampleGenerator();
        ApplicationUserOffer applicationUserOfferBack = getApplicationUserOfferRandomSampleGenerator();

        review.setOffer(applicationUserOfferBack);
        assertThat(review.getOffer()).isEqualTo(applicationUserOfferBack);

        review.offer(null);
        assertThat(review.getOffer()).isNull();
    }
}
