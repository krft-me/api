package me.krft.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineCategory.class);
        MachineCategory machineCategory1 = new MachineCategory();
        machineCategory1.setId(1L);
        MachineCategory machineCategory2 = new MachineCategory();
        machineCategory2.setId(machineCategory1.getId());
        assertThat(machineCategory1).isEqualTo(machineCategory2);
        machineCategory2.setId(2L);
        assertThat(machineCategory1).isNotEqualTo(machineCategory2);
        machineCategory1.setId(null);
        assertThat(machineCategory1).isNotEqualTo(machineCategory2);
    }
}
