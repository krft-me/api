package me.krft.api.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationUserBadgeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ApplicationUserBadge getApplicationUserBadgeSample1() {
        return new ApplicationUserBadge().id(1L);
    }

    public static ApplicationUserBadge getApplicationUserBadgeSample2() {
        return new ApplicationUserBadge().id(2L);
    }

    public static ApplicationUserBadge getApplicationUserBadgeRandomSampleGenerator() {
        return new ApplicationUserBadge().id(longCount.incrementAndGet());
    }
}
