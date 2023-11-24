package me.krft.api.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationUserOfferMapperTest {

    private ApplicationUserOfferMapper applicationUserOfferMapper;

    @BeforeEach
    public void setUp() {
        applicationUserOfferMapper = new ApplicationUserOfferMapperImpl();
    }
}
