package com.stochastic.parrots.laer.ut.domain.identity;

import com.stochastic.parrots.laer.domain.identity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RoleTest {

    @Test
    public void returnsRoleWhenExists() {
        var entries = List.of(
                Map.entry(List.of("ADMIN",  "admin"), Role.ADMIN),
                Map.entry(List.of("USER", "user"), Role.USER));

        entries.forEach(entry -> entry.getKey().forEach(key -> {
            var role = Role.exists(key);
            assertTrue(role.isPresent());
            assertEquals(entry.getValue(), role.get());
        }));
    }

    @Test
    public void returnsEmptyWhenNotExists() {
        var role = Role.exists("SOME");
        assertTrue(role.isEmpty());
    }
}
