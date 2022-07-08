package com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UserId {
    @NonNull
    UUID value;
}
