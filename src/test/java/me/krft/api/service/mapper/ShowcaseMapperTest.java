package me.krft.api.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShowcaseMapperTest {

    private ShowcaseMapper showcaseMapper;

    @BeforeEach
    public void setUp() {
        showcaseMapper = new ShowcaseMapperImpl();
    }
}
