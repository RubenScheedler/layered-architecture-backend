package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository; // SUT

    @Test
    void retrieveSavedUser_givesOriginalUser() {
        // Given a user that was saved in the db
        User expected = User.builder()
                .id(UUID.randomUUID())
                .username("john.smith.35")
                .emailaddress("john.smith@host.com")
                .password("password123".getBytes(StandardCharsets.UTF_8))
                .nickname("TheJohn")
                .build();
        userRepository.save(expected);

        // When I retrieve that user
        Optional<User> actual = userRepository.findById(expected.getId());

        // Then I expect to get the original user
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }
}
