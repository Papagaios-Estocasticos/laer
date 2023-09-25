package com.stochastic.parrots.laer.ut.domain.identity;

import com.stochastic.parrots.laer.domain.identity.Email;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class EmailTest {

    @Test
    public void createEmail() {
        var email = Email.of("test@test.com");
        assertEquals("test@test.com", email.get());
    }

    @Test
    public void throwsInvalidEmailWhenDoNotRegexMatch() {
        assertThrows(Email.Invalid.class, () -> Email.of("test.com"));
    }

    @Test
    public void throwsInvalidEmailWhenCreateWithEmptyString() {
        assertThrows(Email.Invalid.class, () -> Email.of(""));
    }

    @Test
    public void throwsInvalidEmailWhenCreateWithBlankString() {
        assertThrows(Email.Invalid.class, () -> Email.of("     "));
        assertThrows(Email.Invalid.class, () -> Email.of(" "));
    }
}
