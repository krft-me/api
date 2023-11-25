package me.krft.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ShowcaseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Showcase getShowcaseSample1() {
        return new Showcase().id(1L).imageId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Showcase getShowcaseSample2() {
        return new Showcase().id(2L).imageId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Showcase getShowcaseRandomSampleGenerator() {
        return new Showcase().id(longCount.incrementAndGet()).imageId(UUID.randomUUID());
    }
}
