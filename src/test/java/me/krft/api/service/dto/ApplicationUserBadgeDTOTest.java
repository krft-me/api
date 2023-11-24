package me.krft.api.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserBadgeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUserBadgeDTO.class);
        ApplicationUserBadgeDTO applicationUserBadgeDTO1 = new ApplicationUserBadgeDTO();
        applicationUserBadgeDTO1.setId(1L);
        ApplicationUserBadgeDTO applicationUserBadgeDTO2 = new ApplicationUserBadgeDTO();
        assertThat(applicationUserBadgeDTO1).isNotEqualTo(applicationUserBadgeDTO2);
        applicationUserBadgeDTO2.setId(applicationUserBadgeDTO1.getId());
        assertThat(applicationUserBadgeDTO1).isEqualTo(applicationUserBadgeDTO2);
        applicationUserBadgeDTO2.setId(2L);
        assertThat(applicationUserBadgeDTO1).isNotEqualTo(applicationUserBadgeDTO2);
        applicationUserBadgeDTO1.setId(null);
        assertThat(applicationUserBadgeDTO1).isNotEqualTo(applicationUserBadgeDTO2);
    }
}
