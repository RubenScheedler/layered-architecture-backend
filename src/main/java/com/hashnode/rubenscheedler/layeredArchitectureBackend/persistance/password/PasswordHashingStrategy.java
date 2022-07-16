package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

import lombok.NonNull;

/**
 * This component encapsulates behavior having to do with password hashing.
 * No abstraction is used (yet) around this strategy, because I consider it
 * unlikely that this stragety will change without a data migration.
 */
@Component
public class PasswordHashingStrategy {
    /**
     * Generates a 16-bytes salt to be used in password hashing.
     */
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Hashes a password with a generated salt using PBKDF2WithHmacSHA1
     * @param password Password to hash (without salt)
     * @return Hashed password (containg the used salt)
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public HashedPassword hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();

        return HashedPassword.builder()
            .passwordHash(hashPassword(password, salt))
            .salt(salt)
        .build();
    }

    private byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    /**
     * Validates a password against a hash.
     * @param password Password to validate
     * @param salt Salt that was used in generating passwordHash
     * @param passwordHash Hash to validate against
     * @return Whether the password is valid
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public boolean passwordIsValid(String password, @NonNull byte[] salt, @NonNull byte[] passwordHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Arrays.equals(hashPassword(password, salt), passwordHash);
    }
}