package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author: Ezekiel Eromosei
 * @created: 25 February 2022
 */

@Component
//@Primary
public class LaurelWordProducer implements WordProducer {
    @Override
    public String getWord() {
        return "Laurel";
    }
}
