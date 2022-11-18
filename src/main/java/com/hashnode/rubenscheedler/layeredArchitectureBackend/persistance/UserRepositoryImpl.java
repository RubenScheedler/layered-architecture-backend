package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.UserRepository;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.EmailAddress;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.crudrepository.UserCrudRepository;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password.HashedPassword;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password.PasswordHashingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.UUID;

/**
 * Responsible for managing the data representation of users.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserCrudRepository userCrudRepository;
    private final PasswordHashingStrategy passwordHashingStrategy;
    /**
     * Maps the user to a db entity and hashes the password.
     */
    public User createUser(UncreatedUser uncreatedUser) {
        try {
            HashedPassword hashedPassword = passwordHashingStrategy.hashPassword(uncreatedUser.getPassword());
            // Map to data model
            com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User newUser = com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User.builder()
                    .id(UUID.randomUUID())
                    .username(uncreatedUser.getUsername())
                    .emailaddress(uncreatedUser.getEmailAddress().getValue())
                    .nickname(uncreatedUser.getNickname())
                    .password(hashedPassword.getPasswordHash())
                    .salt(hashedPassword.getSalt())
                    .build();
            // Persist the new user
            com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User savedUser = userCrudRepository.save(newUser);

            return mapUserToBusinessEntity(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("User could not be created", e);
        }
    }

    private User mapUserToBusinessEntity(com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User user) {
        return User.builder()
                .id(UserId.builder().value(user.getId()).build())
                .emailAddress(EmailAddress.builder().value(user.getEmailaddress()).build())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .build();
    }

    public Optional<User> getUser(UserId userId) {
        return Optional.empty();
    }

    public User update(User user) {
        return null;
    }

    public void deleteById(UserId userId) {

    }
}
