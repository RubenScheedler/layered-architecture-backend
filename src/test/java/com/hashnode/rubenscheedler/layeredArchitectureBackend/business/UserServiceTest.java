package com.hashnode.rubenscheedler.layeredArchitectureBackend.business;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.exception.UserNotFoundException;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.EmailAddress;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService; // SUT

    @Test
    void uncreatedUser_canBeCreated() {
        // GIVEN an uncreated user
        UncreatedUser uncreatedUser = UncreatedUser.builder()
                .emailAddress(EmailAddress.builder().value("john.doe@gmail.com").build())
                .username("john.doe33")
                .password("verysecretpassphrase")
                .nickname("TheRealJohnDoe")
                .build();
        // AND GIVEN a userRepository
        User expected = User.builder()
                .id(UserId.builder().value(UUID.randomUUID()).build())
                .emailAddress(uncreatedUser.getEmailAddress())
                .username(uncreatedUser.getUsername())
                .nickname(uncreatedUser.getNickname())
                .build();
        when(userRepository.createUser(eq(uncreatedUser))).thenReturn(expected);

        // WHEN I create that user using the userService
        User actual = userService.createUser(uncreatedUser);

        // THEN I expect that user to be created and returned
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getUser_userExists_userIsReturned() {
        // GIVEN an existing user
        UserId userId = UserId.builder().value(UUID.randomUUID()).build();
        User expected = User.builder()
                .id(userId)
                .emailAddress(EmailAddress.builder().value("john.doe@gmail.com").build())
                .username("john.doe33")
                .nickname("TheRealJohnDoe")
                .build();
        when(userRepository.getUser(eq(userId))).thenReturn(Optional.of(expected));

        // WHEN I retrieve that user via the user service
        User actual = userService.getUser(userId);

        // THEN I expect to receive that user
        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getUser_userDoesNotExists_exceptionThrown() {
        // GIVEN the id of a non-existing user
        UserId userId = UserId.builder().value(UUID.randomUUID()).build();
        when(userRepository.getUser(eq(userId))).thenReturn(Optional.empty());

        // WHEN I retrieve that user via the user service
        // THEN I expect an exception because the user does not exist
        assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(String.format("The user with id %s could not be found", userId.getValue()));
    }

    @Test
    void updateUser_updatesUserViaTheRepository() {
        // GIVEN a user to update
        User userToUpdate = User.builder()
                .id(UserId.builder().value(UUID.randomUUID()).build())
                .emailAddress(EmailAddress.builder().value("john.doe@gmail.com").build())
                .username("john.doe34")
                .nickname("TheRealJohnDoe2")
                .build();
        // AND GIVEN a repository to save the user with
        when(userRepository.update(eq(userToUpdate))).thenReturn(userToUpdate);

        // WHEN I ask the user service to update the user
        User actual = userService.updateUser(userToUpdate);

        // THEN I expect this to happen via the repository
        verify(userRepository).update(userToUpdate);
        assertThat(actual).isEqualTo(userToUpdate);
    }

    @Test
    void deleteUser_deletesUserViaRepository() {
        // GIVEN a user to delete
        UserId userId = UserId.builder().value(UUID.randomUUID()).build();

        // WHEN I ask the user service to delete the user
        userService.deleteUser(userId);

        // THEN I expect this to happen via the repository
        verify(userRepository).deleteById(userId);
    }
}
