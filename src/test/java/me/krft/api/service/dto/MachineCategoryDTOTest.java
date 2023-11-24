package me.krft.api.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MachineCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MachineCategoryDTO.class);
        MachineCategoryDTO machineCategoryDTO1 = new MachineCategoryDTO();
        machineCategoryDTO1.setId(1L);
        MachineCategoryDTO machineCategoryDTO2 = new MachineCategoryDTO();
        assertThat(machineCategoryDTO1).isNotEqualTo(machineCategoryDTO2);
        machineCategoryDTO2.setId(machineCategoryDTO1.getId());
        assertThat(machineCategoryDTO1).isEqualTo(machineCategoryDTO2);
        machineCategoryDTO2.setId(2L);
        assertThat(machineCategoryDTO1).isNotEqualTo(machineCategoryDTO2);
        machineCategoryDTO1.setId(null);
        assertThat(machineCategoryDTO1).isNotEqualTo(machineCategoryDTO2);
    }
}
