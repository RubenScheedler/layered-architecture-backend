package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    default User createUser(UncreatedUser uncreatedUser) {
        return null;
    }
}
