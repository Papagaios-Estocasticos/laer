package com.stochastic.parrots.laer.ut.domain.identity;

import com.stochastic.parrots.laer.domain.identity.Password;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PasswordTest {

    @Test
    public void createPassword() {
        var password = Password.of("valid_password123");
        assertEquals("valid_password123", password.get());
    }

    @Test
    public void throwsInvalidPasswordWhenCreateWithoutLetters() {
        assertThrows(Password.Invalid.class, () -> Password.of("123768713"));
    }

    @Test
    public void throwsInvalidPasswordWhenCreateWithoutNumbers() {
        assertThrows(Password.Invalid.class, () -> Password.of("invalid_password"));
    }

    @Test
    public void throwsInvalidPasswordWhenCreateWithoutMinimumLength() {
        assertThrows(Password.Invalid.class, () -> Password.of("a12"));
    }
}
