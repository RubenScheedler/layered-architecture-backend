package com.hashnode.rubenscheedler.layeredArchitectureBackend.business;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.EmailAddress;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UserId;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
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
}
