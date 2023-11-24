package me.krft.api.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserOfferDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUserOfferDTO.class);
        ApplicationUserOfferDTO applicationUserOfferDTO1 = new ApplicationUserOfferDTO();
        applicationUserOfferDTO1.setId(1L);
        ApplicationUserOfferDTO applicationUserOfferDTO2 = new ApplicationUserOfferDTO();
        assertThat(applicationUserOfferDTO1).isNotEqualTo(applicationUserOfferDTO2);
        applicationUserOfferDTO2.setId(applicationUserOfferDTO1.getId());
        assertThat(applicationUserOfferDTO1).isEqualTo(applicationUserOfferDTO2);
        applicationUserOfferDTO2.setId(2L);
        assertThat(applicationUserOfferDTO1).isNotEqualTo(applicationUserOfferDTO2);
        applicationUserOfferDTO1.setId(null);
        assertThat(applicationUserOfferDTO1).isNotEqualTo(applicationUserOfferDTO2);
    }
}
