package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.ShowcaseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShowcaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Showcase.class);
        Showcase showcase1 = getShowcaseSample1();
        Showcase showcase2 = new Showcase();
        assertThat(showcase1).isNotEqualTo(showcase2);

        showcase2.setId(showcase1.getId());
        assertThat(showcase1).isEqualTo(showcase2);

        showcase2 = getShowcaseSample2();
        assertThat(showcase1).isNotEqualTo(showcase2);
    }

    @Test
    void offerTest() throws Exception {
        Showcase showcase = getShowcaseRandomSampleGenerator();
        ApplicationUserOffer applicationUserOfferBack = getApplicationUserOfferRandomSampleGenerator();

        showcase.setOffer(applicationUserOfferBack);
        assertThat(showcase.getOffer()).isEqualTo(applicationUserOfferBack);

        showcase.offer(null);
        assertThat(showcase.getOffer()).isNull();
    }
}
