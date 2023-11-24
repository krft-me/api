package me.krft.api.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShowcaseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShowcaseDTO.class);
        ShowcaseDTO showcaseDTO1 = new ShowcaseDTO();
        showcaseDTO1.setId(1L);
        ShowcaseDTO showcaseDTO2 = new ShowcaseDTO();
        assertThat(showcaseDTO1).isNotEqualTo(showcaseDTO2);
        showcaseDTO2.setId(showcaseDTO1.getId());
        assertThat(showcaseDTO1).isEqualTo(showcaseDTO2);
        showcaseDTO2.setId(2L);
        assertThat(showcaseDTO1).isNotEqualTo(showcaseDTO2);
        showcaseDTO1.setId(null);
        assertThat(showcaseDTO1).isNotEqualTo(showcaseDTO2);
    }
}
