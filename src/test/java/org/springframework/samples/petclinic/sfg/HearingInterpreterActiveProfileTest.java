package org.springframework.samples.petclinic.sfg;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ActiveProfiles(value = "yanny")
@SpringJUnitConfig(classes = HearingInterpreterActiveProfileTest.TestConfig.class)
class HearingInterpreterActiveProfileTest {

    @Configuration
    @ComponentScan(value = "org.springframework.samples.petclinic.sfg")
    static class TestConfig{

    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void annotatedPrimaryClassNotUsed() {
        String word = hearingInterpreter.whatIHeard();

        assertNotEquals("Yanny", word);
    }
}