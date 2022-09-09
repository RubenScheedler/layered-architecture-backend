package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordHashingStrategyTest {
    
    PasswordHashingStrategy passwordHashingStrategy = new PasswordHashingStrategy(); // SUT
    @Test
    void hashPassword_usesSalt_andProducesReproducibleResult() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // GIVEN a password
        String password = "superSafePassword";
        // WHEN I hash that password
        HashedPassword hashedPassword= passwordHashingStrategy.hashPassword(password);
        // THEN I expect to be able to recreate that hash with my password and the used salt
        assertThat(hashedPassword).isNotNull();
        assertThat(passwordHashingStrategy.passwordIsValid(password, hashedPassword.getSalt(), hashedPassword.getPasswordHash())).isTrue();
    }
}
