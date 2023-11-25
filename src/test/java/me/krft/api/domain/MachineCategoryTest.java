package me.krft.api.domain;

import static me.krft.api.domain.MachineCategoryTestSamples.*;
import static me.krft.api.domain.MachineTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineCategory.class);
        MachineCategory machineCategory1 = getMachineCategorySample1();
        MachineCategory machineCategory2 = new MachineCategory();
        assertThat(machineCategory1).isNotEqualTo(machineCategory2);

        machineCategory2.setId(machineCategory1.getId());
        assertThat(machineCategory1).isEqualTo(machineCategory2);

        machineCategory2 = getMachineCategorySample2();
        assertThat(machineCategory1).isNotEqualTo(machineCategory2);
    }

    @Test
    void machinesTest() throws Exception {
        MachineCategory machineCategory = getMachineCategoryRandomSampleGenerator();
        Machine machineBack = getMachineRandomSampleGenerator();

        machineCategory.addMachines(machineBack);
        assertThat(machineCategory.getMachines()).containsOnly(machineBack);
        assertThat(machineBack.getCategory()).isEqualTo(machineCategory);

        machineCategory.removeMachines(machineBack);
        assertThat(machineCategory.getMachines()).doesNotContain(machineBack);
        assertThat(machineBack.getCategory()).isNull();

        machineCategory.machines(new HashSet<>(Set.of(machineBack)));
        assertThat(machineCategory.getMachines()).containsOnly(machineBack);
        assertThat(machineBack.getCategory()).isEqualTo(machineCategory);

        machineCategory.setMachines(new HashSet<>());
        assertThat(machineCategory.getMachines()).doesNotContain(machineBack);
        assertThat(machineBack.getCategory()).isNull();
    }
}
