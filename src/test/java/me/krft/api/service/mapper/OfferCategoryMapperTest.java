package me.krft.api.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfferCategoryMapperTest {

    private OfferCategoryMapper offerCategoryMapper;

    @BeforeEach
    public void setUp() {
        offerCategoryMapper = new OfferCategoryMapperImpl();
    }
}
