package me.krft.api.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationUserBadgeMapperTest {

    private ApplicationUserBadgeMapper applicationUserBadgeMapper;

    @BeforeEach
    public void setUp() {
        applicationUserBadgeMapper = new ApplicationUserBadgeMapperImpl();
    }
}
