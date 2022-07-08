package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepository {
    public User createUser(UncreatedUser uncreatedUser) {
        return null;
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
