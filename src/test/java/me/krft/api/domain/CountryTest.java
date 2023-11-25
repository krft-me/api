package me.krft.api.domain;

import static me.krft.api.domain.CountryTestSamples.*;
import static me.krft.api.domain.RegionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CountryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = getCountrySample1();
        Country country2 = new Country();
        assertThat(country1).isNotEqualTo(country2);

        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);

        country2 = getCountrySample2();
        assertThat(country1).isNotEqualTo(country2);
    }

    @Test
    void regionsTest() throws Exception {
        Country country = getCountryRandomSampleGenerator();
        Region regionBack = getRegionRandomSampleGenerator();

        country.addRegions(regionBack);
        assertThat(country.getRegions()).containsOnly(regionBack);
        assertThat(regionBack.getCountry()).isEqualTo(country);

        country.removeRegions(regionBack);
        assertThat(country.getRegions()).doesNotContain(regionBack);
        assertThat(regionBack.getCountry()).isNull();

        country.regions(new HashSet<>(Set.of(regionBack)));
        assertThat(country.getRegions()).containsOnly(regionBack);
        assertThat(regionBack.getCountry()).isEqualTo(country);

        country.setRegions(new HashSet<>());
        assertThat(country.getRegions()).doesNotContain(regionBack);
        assertThat(regionBack.getCountry()).isNull();
    }
}
