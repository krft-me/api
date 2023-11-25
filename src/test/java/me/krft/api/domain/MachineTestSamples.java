package me.krft.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MachineTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Machine getMachineSample1() {
        return new Machine().id(1L).name("name1");
    }

    public static Machine getMachineSample2() {
        return new Machine().id(2L).name("name2");
    }

    public static Machine getMachineRandomSampleGenerator() {
        return new Machine().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
