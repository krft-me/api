package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void offersTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        ApplicationUserOffer applicationUserOfferBack = getApplicationUserOfferRandomSampleGenerator();

        tag.addOffers(applicationUserOfferBack);
        assertThat(tag.getOffers()).containsOnly(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getTags()).containsOnly(tag);

        tag.removeOffers(applicationUserOfferBack);
        assertThat(tag.getOffers()).doesNotContain(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getTags()).doesNotContain(tag);

        tag.offers(new HashSet<>(Set.of(applicationUserOfferBack)));
        assertThat(tag.getOffers()).containsOnly(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getTags()).containsOnly(tag);

        tag.setOffers(new HashSet<>());
        assertThat(tag.getOffers()).doesNotContain(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getTags()).doesNotContain(tag);
    }
}
