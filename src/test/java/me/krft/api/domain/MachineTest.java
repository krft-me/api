package me.krft.api.domain;

import static me.krft.api.domain.MachineCategoryTestSamples.*;
import static me.krft.api.domain.MachineTestSamples.*;
import static me.krft.api.domain.OfferTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Machine.class);
        Machine machine1 = getMachineSample1();
        Machine machine2 = new Machine();
        assertThat(machine1).isNotEqualTo(machine2);

        machine2.setId(machine1.getId());
        assertThat(machine1).isEqualTo(machine2);

        machine2 = getMachineSample2();
        assertThat(machine1).isNotEqualTo(machine2);
    }

    @Test
    void offersTest() throws Exception {
        Machine machine = getMachineRandomSampleGenerator();
        Offer offerBack = getOfferRandomSampleGenerator();

        machine.addOffers(offerBack);
        assertThat(machine.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getMachine()).isEqualTo(machine);

        machine.removeOffers(offerBack);
        assertThat(machine.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getMachine()).isNull();

        machine.offers(new HashSet<>(Set.of(offerBack)));
        assertThat(machine.getOffers()).containsOnly(offerBack);
        assertThat(offerBack.getMachine()).isEqualTo(machine);

        machine.setOffers(new HashSet<>());
        assertThat(machine.getOffers()).doesNotContain(offerBack);
        assertThat(offerBack.getMachine()).isNull();
    }

    @Test
    void categoryTest() throws Exception {
        Machine machine = getMachineRandomSampleGenerator();
        MachineCategory machineCategoryBack = getMachineCategoryRandomSampleGenerator();

        machine.setCategory(machineCategoryBack);
        assertThat(machine.getCategory()).isEqualTo(machineCategoryBack);

        machine.category(null);
        assertThat(machine.getCategory()).isNull();
    }
}
