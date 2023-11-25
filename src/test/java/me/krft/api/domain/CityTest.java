package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserTestSamples.*;
import static me.krft.api.domain.CityTestSamples.*;
import static me.krft.api.domain.RegionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(City.class);
        City city1 = getCitySample1();
        City city2 = new City();
        assertThat(city1).isNotEqualTo(city2);

        city2.setId(city1.getId());
        assertThat(city1).isEqualTo(city2);

        city2 = getCitySample2();
        assertThat(city1).isNotEqualTo(city2);
    }

    @Test
    void usersTest() throws Exception {
        City city = getCityRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        city.addUsers(applicationUserBack);
        assertThat(city.getUsers()).containsOnly(applicationUserBack);
        assertThat(applicationUserBack.getCity()).isEqualTo(city);

        city.removeUsers(applicationUserBack);
        assertThat(city.getUsers()).doesNotContain(applicationUserBack);
        assertThat(applicationUserBack.getCity()).isNull();

        city.users(new HashSet<>(Set.of(applicationUserBack)));
        assertThat(city.getUsers()).containsOnly(applicationUserBack);
        assertThat(applicationUserBack.getCity()).isEqualTo(city);

        city.setUsers(new HashSet<>());
        assertThat(city.getUsers()).doesNotContain(applicationUserBack);
        assertThat(applicationUserBack.getCity()).isNull();
    }

    @Test
    void regionTest() throws Exception {
        City city = getCityRandomSampleGenerator();
        Region regionBack = getRegionRandomSampleGenerator();

        city.setRegion(regionBack);
        assertThat(city.getRegion()).isEqualTo(regionBack);

        city.region(null);
        assertThat(city.getRegion()).isNull();
    }
}
