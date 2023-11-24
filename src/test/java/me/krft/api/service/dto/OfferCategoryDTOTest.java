package me.krft.api.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfferCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferCategoryDTO.class);
        OfferCategoryDTO offerCategoryDTO1 = new OfferCategoryDTO();
        offerCategoryDTO1.setId(1L);
        OfferCategoryDTO offerCategoryDTO2 = new OfferCategoryDTO();
        assertThat(offerCategoryDTO1).isNotEqualTo(offerCategoryDTO2);
        offerCategoryDTO2.setId(offerCategoryDTO1.getId());
        assertThat(offerCategoryDTO1).isEqualTo(offerCategoryDTO2);
        offerCategoryDTO2.setId(2L);
        assertThat(offerCategoryDTO1).isNotEqualTo(offerCategoryDTO2);
        offerCategoryDTO1.setId(null);
        assertThat(offerCategoryDTO1).isNotEqualTo(offerCategoryDTO2);
    }
}
