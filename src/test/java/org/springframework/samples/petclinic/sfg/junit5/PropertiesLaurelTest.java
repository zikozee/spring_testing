package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(value = "classpath:laurel.properties") // urls, different datasources,
@ActiveProfiles(value = "laurel-properties")
@SpringJUnitConfig(classes = PropertiesLaurelTest.TestConfig.class)
class PropertiesLaurelTest {

    @Configuration
    @ComponentScan(value = "org.springframework.samples.petclinic.sfg")
    static class TestConfig{

    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIheard() {
        String word = hearingInterpreter.whatIHeard();

        assertEquals("LaURel", word);
    }
}