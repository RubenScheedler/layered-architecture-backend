package com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class EmailAddress {
    @NonNull
    String value;
}
