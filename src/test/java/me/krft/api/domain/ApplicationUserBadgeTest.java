package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserBadgeTestSamples.*;
import static me.krft.api.domain.ApplicationUserTestSamples.*;
import static me.krft.api.domain.BadgeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicationUserBadgeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationUserBadge.class);
        ApplicationUserBadge applicationUserBadge1 = getApplicationUserBadgeSample1();
        ApplicationUserBadge applicationUserBadge2 = new ApplicationUserBadge();
        assertThat(applicationUserBadge1).isNotEqualTo(applicationUserBadge2);

        applicationUserBadge2.setId(applicationUserBadge1.getId());
        assertThat(applicationUserBadge1).isEqualTo(applicationUserBadge2);

        applicationUserBadge2 = getApplicationUserBadgeSample2();
        assertThat(applicationUserBadge1).isNotEqualTo(applicationUserBadge2);
    }

    @Test
    void userTest() throws Exception {
        ApplicationUserBadge applicationUserBadge = getApplicationUserBadgeRandomSampleGenerator();
        ApplicationUser applicationUserBack = getApplicationUserRandomSampleGenerator();

        applicationUserBadge.setUser(applicationUserBack);
        assertThat(applicationUserBadge.getUser()).isEqualTo(applicationUserBack);

        applicationUserBadge.user(null);
        assertThat(applicationUserBadge.getUser()).isNull();
    }

    @Test
    void badgeTest() throws Exception {
        ApplicationUserBadge applicationUserBadge = getApplicationUserBadgeRandomSampleGenerator();
        Badge badgeBack = getBadgeRandomSampleGenerator();

        applicationUserBadge.setBadge(badgeBack);
        assertThat(applicationUserBadge.getBadge()).isEqualTo(badgeBack);

        applicationUserBadge.badge(null);
        assertThat(applicationUserBadge.getBadge()).isNull();
    }
}
