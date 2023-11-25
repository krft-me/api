package me.krft.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicationUserOfferTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ApplicationUserOffer getApplicationUserOfferSample1() {
        return new ApplicationUserOffer().id(1L).description("description1").price(1);
    }

    public static ApplicationUserOffer getApplicationUserOfferSample2() {
        return new ApplicationUserOffer().id(2L).description("description2").price(2);
    }

    public static ApplicationUserOffer getApplicationUserOfferRandomSampleGenerator() {
        return new ApplicationUserOffer()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .price(intCount.incrementAndGet());
    }
}
