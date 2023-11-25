package me.krft.api.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MachineCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MachineCategory getMachineCategorySample1() {
        return new MachineCategory().id(1L).label("label1");
    }

    public static MachineCategory getMachineCategorySample2() {
        return new MachineCategory().id(2L).label("label2");
    }

    public static MachineCategory getMachineCategoryRandomSampleGenerator() {
        return new MachineCategory().id(longCount.incrementAndGet()).label(UUID.randomUUID().toString());
    }
}
