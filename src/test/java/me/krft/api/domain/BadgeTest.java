package me.krft.api.domain;

import static me.krft.api.domain.ApplicationUserBadgeTestSamples.*;
import static me.krft.api.domain.BadgeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import me.krft.api.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BadgeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Badge.class);
        Badge badge1 = getBadgeSample1();
        Badge badge2 = new Badge();
        assertThat(badge1).isNotEqualTo(badge2);

        badge2.setId(badge1.getId());
        assertThat(badge1).isEqualTo(badge2);

        badge2 = getBadgeSample2();
        assertThat(badge1).isNotEqualTo(badge2);
    }

    @Test
    void usersTest() throws Exception {
        Badge badge = getBadgeRandomSampleGenerator();
        ApplicationUserBadge applicationUserBadgeBack = getApplicationUserBadgeRandomSampleGenerator();

        badge.addUsers(applicationUserBadgeBack);
        assertThat(badge.getUsers()).containsOnly(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getBadge()).isEqualTo(badge);

        badge.removeUsers(applicationUserBadgeBack);
        assertThat(badge.getUsers()).doesNotContain(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getBadge()).isNull();

        badge.users(new HashSet<>(Set.of(applicationUserBadgeBack)));
        assertThat(badge.getUsers()).containsOnly(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getBadge()).isEqualTo(badge);

        badge.setUsers(new HashSet<>());
        assertThat(badge.getUsers()).doesNotContain(applicationUserBadgeBack);
        assertThat(applicationUserBadgeBack.getBadge()).isNull();
    }
}
