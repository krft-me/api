package me.krft.api.domain;

import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CacaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caca.class);
        Caca caca1 = new Caca();
        caca1.setId(1L);
        Caca caca2 = new Caca();
        caca2.setId(caca1.getId());
        assertThat(caca1).isEqualTo(caca2);
        caca2.setId(2L);
        assertThat(caca1).isNotEqualTo(caca2);
        caca1.setId(null);
        assertThat(caca1).isNotEqualTo(caca2);
    }
}
