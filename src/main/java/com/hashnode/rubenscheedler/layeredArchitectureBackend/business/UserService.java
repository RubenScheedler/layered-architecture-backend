package com.hashnode.rubenscheedler.layeredArchitectureBackend.business;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Responsible for business logic having to do with users.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Persists the yet uncreated user.
     * @return The created user
     */
    public User createUser(UncreatedUser uncreatedUser) {
        return userRepository.createUser(uncreatedUser);
    }
}
