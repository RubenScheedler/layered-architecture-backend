package com.hashnode.rubenscheedler.layeredArchitectureBackend.business;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.exception.UserNotFoundException;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;
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

    /**
     * Retrieves the requested user, if it exists.
     * @param userId Id of the user to retrieve
     * @return The User or a {@link UserNotFoundException UserNotFoundException.class}
     */
    public User getUser(UserId userId) {
        return userRepository.getUser(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("The user with id %s could not be found", userId.getValue())
                    )
                );
    }

    /**
     * Updates a user
     * @param user User in its new state
     * @return User after updating it
     */
    public User updateUser(User user) {
        return userRepository.update(user);
    }

    /**
     * Deletes the user with the given id.
     */
    public void deleteUser(UserId userId) {
        userRepository.deleteById(userId);
    }
}
