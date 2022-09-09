package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.repository;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
}
