package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserOfferTestSamples.*;
import static me.krft.api.domain.ApplicationUserTestSamples.*;
import static me.krft.api.domain.OrderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = getOrderSample1();
        Order order2 = new Order();
        assertThat(order1).isNotEqualTo(order2);

        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);

        order2 = getOrderSample2();
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    void offerTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        ApplicationUserOffer applicationUserOfferBack = getApplicationUserOfferRandomSampleGenerator();

        order.setOffer(applicationUserOfferBack);
        assertThat(order.getOffer()).isEqualTo(applicationUserOfferBack);

        order.offer(null);
        assertThat(order.getOffer()).isNull();
    }

    @Test
    void customerTest() throws Exception {
        Order order = getOrderRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        order.setCustomer(applicationUserBack);
        assertThat(order.getCustomer()).isEqualTo(applicationUserBack);

        order.customer(null);
        assertThat(order.getCustomer()).isNull();
    }
}
