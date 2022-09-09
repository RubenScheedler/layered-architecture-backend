package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.crudrepository;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.crudrepository.UserCrudRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserCrudRepositoryTest {
    @Autowired
    UserCrudRepository userCrudRepository; // SUT

    /**
     * This test not so much validates the correctness of standard CRUD repositories.
     * The test only passes if the liquibase script was loaded and sets up a schema
     * that matches the database entity.
     */
    @Test
    void retrieveSavedUser_givesOriginalUser() {
        // Given a user that was saved in the db
        User expected = User.builder()
                .id(UUID.randomUUID())
                .username("john.smith.35")
                .emailaddress("john.smith@host.com")
                .password("password123".getBytes(StandardCharsets.UTF_8))
                .salt("salt".getBytes(StandardCharsets.UTF_8))
                .nickname("TheJohn")
                .build();
        userCrudRepository.save(expected);

        // When I retrieve that user
        Optional<User> actual = userCrudRepository.findById(expected.getId());

        // Then I expect to get the original user
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);
    }
}
