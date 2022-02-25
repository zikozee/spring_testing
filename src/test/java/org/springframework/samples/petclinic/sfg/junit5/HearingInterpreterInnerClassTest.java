package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;


//These can be used when tou have a quick configuration you need to do that doesn't require too much
// we can use an inner class Config
@SpringJUnitConfig(classes = HearingInterpreterInnerClassTest.TestConfig.class)
class HearingInterpreterInnerClassTest {

    @Configuration
    static class TestConfig{

        @Bean
        HearingInterpreter hearingInterpreter(){
            return new HearingInterpreter(new LaurelWordProducer());
        }
    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeard(){
        String word = hearingInterpreter.whatIHeard();

        assertEquals("Laurel", word);
    }

}