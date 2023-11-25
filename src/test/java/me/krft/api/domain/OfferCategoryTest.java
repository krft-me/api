package me.krft.api.domain;

import static me.krft.api.domain.OfferCategoryTestSamples.*;
import static me.krft.api.domain.OfferTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfferCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferCategory.class);
        OfferCategory offerCategory1 = getOfferCategorySample1();
        OfferCategory offerCategory2 = new OfferCategory();
        assertThat(offerCategory1).isNotEqualTo(offerCategory2);

        offerCategory2.setId(offerCategory1.getId());
        assertThat(offerCategory1).isEqualTo(offerCategory2);

        offerCategory2 = getOfferCategorySample2();
        assertThat(offerCategory1).isNotEqualTo(offerCategory2);
    }

    @Test
    void offersTest() throws Exception {
        OfferCategory offerCategory = getOfferCategoryRandomSampleGenerator();
        Offer offerBack = getOfferRandomSampleGenerator();

        offerCategory.addOffers(offerBack);
        assertThat(offerCategory.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getCategory()).isEqualTo(offerCategory);

        offerCategory.removeOffers(offerBack);
        assertThat(offerCategory.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getCategory()).isNull();

        offerCategory.offers(new HashSet<>(Set.of(offerBack)));
        assertThat(offerCategory.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getCategory()).isEqualTo(offerCategory);

        offerCategory.setOffers(new HashSet<>());
        assertThat(offerCategory.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getCategory()).isNull();
    }
}
