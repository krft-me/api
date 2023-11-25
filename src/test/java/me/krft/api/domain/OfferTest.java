package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.MachineTestSamples.*;
import static me.krft.api.domain.OfferCategoryTestSamples.*;
import static me.krft.api.domain.OfferTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfferTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offer.class);
        Offer offer1 = getOfferSample1();
        Offer offer2 = new Offer();
        assertThat(offer1).isNotEqualTo(offer2);

        offer2.setId(offer1.getId());
        assertThat(offer1).isEqualTo(offer2);

        offer2 = getOfferSample2();
        assertThat(offer1).isNotEqualTo(offer2);
    }

    @Test
    void userOffersTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        ApplicationUserOffer applicationUserOfferBack = getApplicationUserOfferRandomSampleGenerator();

        offer.addUserOffers(applicationUserOfferBack);
        assertThat(offer.getUserOffers()).containsOnly(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getOffer()).isEqualTo(offer);

        offer.removeUserOffers(applicationUserOfferBack);
        assertThat(offer.getUserOffers()).doesNotContain(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getOffer()).isNull();

        offer.userOffers(new HashSet<>(Set.of(applicationUserOfferBack)));
        assertThat(offer.getUserOffers()).containsOnly(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getOffer()).isEqualTo(offer);

        offer.setUserOffers(new HashSet<>());
        assertThat(offer.getUserOffers()).doesNotContain(applicationUserOfferBack);
        assertThat(applicationUserOfferBack.getOffer()).isNull();
    }

    @Test
    void machineTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        Machine machineBack = getMachineRandomSampleGenerator();

        offer.setMachine(machineBack);
        assertThat(offer.getMachine()).isEqualTo(machineBack);

        offer.machine(null);
        assertThat(offer.getMachine()).isNull();
    }

    @Test
    void categoryTest() throws Exception {
        Offer offer = getOfferRandomSampleGenerator();
        OfferCategory offerCategoryBack = getOfferCategoryRandomSampleGenerator();

        offer.setCategory(offerCategoryBack);
        assertThat(offer.getCategory()).isEqualTo(offerCategoryBack);

        offer.category(null);
        assertThat(offer.getCategory()).isNull();
    }
}
