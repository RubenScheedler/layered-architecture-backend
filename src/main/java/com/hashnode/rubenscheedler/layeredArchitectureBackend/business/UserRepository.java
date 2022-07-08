package com.hashnode.rubenscheedler.layeredArchitectureBackend.business;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;

import java.util.Optional;

public interface UserRepository {
    User createUser(UncreatedUser uncreatedUser);

    Optional<User> getUser(UserId userId);

    User update(User user);

    void deleteById(UserId userId);
}
