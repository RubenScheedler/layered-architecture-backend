package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class HashedPassword {
    /**
     * Result of hashing a password with a salt.
     */
    @NonNull
    byte[] passwordHash;
    /**
     * Salt used to generate passwordHash.
     */
    @NonNull
    byte[] salt;
}
