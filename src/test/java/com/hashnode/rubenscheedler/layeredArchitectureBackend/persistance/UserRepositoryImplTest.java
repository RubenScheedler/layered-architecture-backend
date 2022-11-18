package com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance;

import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.EmailAddress;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.value.UncreatedUser;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.crudrepository.UserCrudRepository;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.model.User;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password.HashedPassword;
import com.hashnode.rubenscheedler.layeredArchitectureBackend.persistance.password.PasswordHashingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.lang.model.util.Types;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {
    @Mock
    PasswordHashingStrategy passwordHashingStrategy;
    @Mock
    UserCrudRepository userCrudRepository;
    @InjectMocks
    UserRepositoryImpl userRepository;

    @Test
    void createUser_hashesPasswordAndSavesUser() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Given a user to create
        UncreatedUser uncreatedUser = UncreatedUser.builder()
                .username("john.smith34")
                .password("SafeAndSound")
                .nickname("TheJohn")
                .emailAddress(EmailAddress.builder().value("john.smith@host.com").build())
                .build();
        HashedPassword hashedPassword = HashedPassword.builder()
                .passwordHash(new byte[]{1, 2, 3, 4})
                .salt(new byte[]{5, 6, 7, 8})
                .build();
        when(passwordHashingStrategy.hashPassword(eq(uncreatedUser.getPassword()))).thenReturn(hashedPassword);
        when(userCrudRepository.save(any())).thenReturn(User.builder()
                        .id(UUID.randomUUID())
                        .emailaddress(uncreatedUser.getEmailAddress().getValue())
                        .username(uncreatedUser.getUsername())
                        .nickname(uncreatedUser.getNickname())
                        .password(new byte[]{1,2,3,4})
                        .salt(new byte[]{5, 6, 7, 8})
                .build());
        // When I create that user via the repository
        com.hashnode.rubenscheedler.layeredArchitectureBackend.business.model.entity.User actualReturned = userRepository.createUser(uncreatedUser);

        // Then I expect the password to be hashed and the user to be saved
        verify(passwordHashingStrategy).hashPassword(uncreatedUser.getPassword());

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userCrudRepository).save(argumentCaptor.capture());
        User actual = argumentCaptor.getValue();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getUsername()).isEqualTo(uncreatedUser.getUsername());
        assertThat(actual.getEmailaddress()).isEqualTo(uncreatedUser.getEmailAddress().getValue());
        assertThat(actual.getNickname()).isEqualTo(uncreatedUser.getNickname());
        assertThat(actual.getPassword()).isEqualTo(hashedPassword.getPasswordHash());
        assertThat(actual.getSalt()).isEqualTo(hashedPassword.getSalt());

        // Then I expect the saved user mapped back to a business model
        assertThat(actualReturned).isNotNull();
        assertThat(actualReturned.getId()).isNotNull();
        assertThat(actualReturned.getEmailAddress()).isNotNull();
        assertThat(actualReturned.getEmailAddress()).isEqualTo(uncreatedUser.getEmailAddress());
        assertThat(actualReturned.getUsername()).isEqualTo(uncreatedUser.getUsername());
        assertThat(actualReturned.getNickname()).isEqualTo(uncreatedUser.getNickname());
    }
}
