package com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.EmailAddress;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class User {
    @NonNull
    UserId id;
    @NonNull
    EmailAddress emailAddress;
    @NonNull
    String username;
    @NonNull
    String nickname;
}
