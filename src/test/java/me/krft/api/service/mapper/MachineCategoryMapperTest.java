package me.krft.api.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineCategoryMapperTest {

    private MachineCategoryMapper machineCategoryMapper;

    @BeforeEach
    public void setUp() {
        machineCategoryMapper = new MachineCategoryMapperImpl();
    }
}
