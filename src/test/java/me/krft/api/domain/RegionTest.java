package me.krft.api.domain;

import static me.krft.api.domain.CityTestSamples.*;
import static me.krft.api.domain.CountryTestSamples.*;
import static me.krft.api.domain.RegionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Region.class);
        Region region1 = getRegionSample1();
        Region region2 = new Region();
        assertThat(region1).isNotEqualTo(region2);

        region2.setId(region1.getId());
        assertThat(region1).isEqualTo(region2);

        region2 = getRegionSample2();
        assertThat(region1).isNotEqualTo(region2);
    }

    @Test
    void citiesTest() throws Exception {
        Region region = getRegionRandomSampleGenerator();
        City cityBack = getCityRandomSampleGenerator();

        region.addCities(cityBack);
        assertThat(region.getCities()).containsOnly(cityBack);
        assertThat(cityBack.getRegion()).isEqualTo(region);

        region.removeCities(cityBack);
        assertThat(region.getCities()).doesNotContain(cityBack);
        assertThat(cityBack.getRegion()).isNull();

        region.cities(new HashSet<>(Set.of(cityBack)));
        assertThat(region.getCities()).containsOnly(cityBack);
        assertThat(cityBack.getRegion()).isEqualTo(region);

        region.setCities(new HashSet<>());
        assertThat(region.getCities()).doesNotContain(cityBack);
        assertThat(cityBack.getRegion()).isNull();
    }

    @Test
    void countryTest() throws Exception {
        Region region = getRegionRandomSampleGenerator();
        Country countryBack = getCountryRandomSampleGenerator();

        region.setCountry(countryBack);
        assertThat(region.getCountry()).isEqualTo(countryBack);

        region.country(null);
        assertThat(region.getCountry()).isNull();
    }
}
