package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author: Ezekiel Eromosei
 * @created: 25 February 2022
 */

@Profile("base-test")
@Configuration
public class YannyConfig {

    @Bean
    YannyWordProducer yannyWordProducer(){
        return new YannyWordProducer();
    }
}
