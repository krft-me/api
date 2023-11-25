package me.krft.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OfferCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OfferCategory getOfferCategorySample1() {
        return new OfferCategory().id(1L).label("label1");
    }

    public static OfferCategory getOfferCategorySample2() {
        return new OfferCategory().id(2L).label("label2");
    }

    public static OfferCategory getOfferCategoryRandomSampleGenerator() {
        return new OfferCategory().id(longCount.incrementAndGet()).label(UUID.randomUUID().toString());
    }
}
