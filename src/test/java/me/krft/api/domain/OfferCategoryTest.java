package me.krft.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfferCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferCategory.class);
        OfferCategory offerCategory1 = new OfferCategory();
        offerCategory1.setId(1L);
        OfferCategory offerCategory2 = new OfferCategory();
        offerCategory2.setId(offerCategory1.getId());
        assertThat(offerCategory1).isEqualTo(offerCategory2);
        offerCategory2.setId(2L);
        assertThat(offerCategory1).isNotEqualTo(offerCategory2);
        offerCategory1.setId(null);
        assertThat(offerCategory1).isNotEqualTo(offerCategory2);
    }
}
