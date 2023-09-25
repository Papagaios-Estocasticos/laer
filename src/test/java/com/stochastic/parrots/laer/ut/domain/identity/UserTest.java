package com.stochastic.parrots.laer.ut.domain.identity;

import com.stochastic.parrots.laer.domain.identity.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Test
    public void createActivatedUser() {
        var user = BUILDER
                .with(ID)
                .with(List.of(Role.USER))
                .activated().build();

        assertEquals(ID, user.id());
        assertEquals(NAME, user.name());
        assertEquals(PASSWORD, user.password());
        assertEquals(EMAIl, user.email());
        assertEquals(List.of(Role.USER), user.roles());
        assertTrue(user.isActive());
    }

    @Test
    public void createDeactivatedUser() {
        var user = BUILDER
                .with(ID)
                .with(List.of(Role.ADMIN))
                .deactivated().build();

        assertEquals(ID, user.id());
        assertEquals(NAME, user.name());
        assertEquals(PASSWORD, user.password());
        assertEquals(EMAIl, user.email());
        assertEquals(List.of(Role.ADMIN), user.roles());
        assertFalse(user.isActive());
    }

    @Test
    public void throwsInvalidUserWhenRolesIsEmpty() {
        assertThrows(User.Invalid.class, () -> BUILDER
                .with(Collections.emptyList())
                .activated().build());
    }

    @Test
    public void equals() {
        var user = BUILDER.with(123L).activated().with(List.of(Role.USER)).build();
        var other = BUILDER.with(234L).activated().with(List.of(Role.USER)).build();

        assertEquals(user, user);
        assertNotEquals(user, other);
    }

    @Test
    public void hashcode() {
        var user = BUILDER.with(123L).activated().with(List.of(Role.USER)).build();
        var other = BUILDER.with(234L).activated().with(List.of(Role.USER)).build();

        assertEquals(user.hashCode(), user.hashCode());
        assertNotEquals(user.hashCode(), other.hashCode());
    }

    private static final Long ID = 123L;
    private static final Name NAME = Name.of("Arnold");
    private static final Password PASSWORD = Password.of("valid_password123");
    private static final Email EMAIl = Email.of("test@test.com");
    private static final User.Builder BUILDER = new User.Builder()
            .with(ID).with(NAME).with(PASSWORD).with(EMAIl);
}
