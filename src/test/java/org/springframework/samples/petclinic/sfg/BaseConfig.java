package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Ezekiel Eromosei
 * @created: 25 February 2022
 */


@Configuration
public class BaseConfig {

    @Bean
    HearingInterpreter hearingInterpreter(WordProducer wordProducer){
        return new HearingInterpreter(wordProducer);
    }
}
