package com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Represents a user that is about to be created.
 */
@Value
@Builder
public class UncreatedUser {
    @NonNull
    EmailAddress emailAddress;
    @NonNull
    String username;
    @NonNull
    String password;
    @NonNull
    String nickname;
}
