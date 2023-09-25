package com.stochastic.parrots.laer.ut.domain.identity;

import com.stochastic.parrots.laer.domain.identity.Name;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class NameTest {

    @Test
    public void createName() {
        var name = Name.of("Arnold");
        assertEquals("Arnold", name.get());
    }

    @Test
    public void throwsInvalidNameWhenCreateWithEmptyString() {
        assertThrows(Name.Invalid.class, () -> Name.of(""));
    }

    @Test
    public void throwsInvalidNameWhenCreateWithBlankString() {
        assertThrows(Name.Invalid.class, () -> Name.of("     "));
        assertThrows(Name.Invalid.class, () -> Name.of(" "));
    }

    @Test
    public void throwsInvalidNameWhenCreateLowerMinimumLength() {
        assertThrows(Name.Invalid.class, () -> Name.of("nn"));
    }

    @Test
    public void throwsInvalidNameWhenCreateGreaterMaximumLength() {
        assertThrows(Name.Invalid.class, () -> Name.of("Arnold Cross Hair"));
    }
}
