package org.example.authmodule.service;

import org.example.authmodule.dto.UserRequestDto;
import org.example.authmodule.dto.UserVerificationDto;
import org.example.authmodule.kafka.VerificationProducer;
import org.example.authmodule.model.UserEntity;
import org.example.authmodule.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@SpringBootTest
class AuthServiceTest {

    private AuthService verificationService;
    private UserRepository userRepository;
    private VerificationProducer producer;

    @BeforeEach
    void setup() {

        userRepository = mock(UserRepository.class);
        producer = mock(VerificationProducer.class);

        verificationService = new AuthService(userRepository, producer);
    }
    @Test
    void testGenerateCode() {

        String email = "user@example.com";
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        verificationService.generateAndSendCode(userRequestDto);

        Mockito.verify(userRepository).save(Mockito.any());
        Mockito.verify(producer).send(Mockito.eq(email), Mockito.anyString());
    }

    @Test
    void testVerifyCodeSuccess() {

        String email = "user@example.com";
        String code = "ABC123";

        UserEntity u = new UserEntity(email, code, Instant.now().plusSeconds(300));
        u.setVerified(false);

        UserVerificationDto userVerificationDto = new UserVerificationDto();
        userVerificationDto.setCode(u.getVerificationCode());
        userVerificationDto.setEmail(u.getEmail());

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(u));

        boolean ok = verificationService.verifyCode(userVerificationDto);

        assertTrue(ok);
        assertTrue(u.getVerified());
    }
}