package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@Table(name="users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NonNull
    @Id
    private UUID id;
    @NonNull
    private String emailaddress;
    @NonNull
    private String username;
    @NonNull
    private byte[] password;
    @NonNull
    private byte[] salt;
    @NonNull
    private String nickname;
}

