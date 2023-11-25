package me.krft.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BadgeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Badge getBadgeSample1() {
        return new Badge().id(1L).label("label1").picture("picture1");
    }

    public static Badge getBadgeSample2() {
        return new Badge().id(2L).label("label2").picture("picture2");
    }

    public static Badge getBadgeRandomSampleGenerator() {
        return new Badge().id(longCount.incrementAndGet()).label(UUID.randomUUID().toString()).picture(UUID.randomUUID().toString());
    }
}
