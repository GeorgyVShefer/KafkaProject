package org.example.authmodule.service;

import lombok.RequiredArgsConstructor;
import org.example.authmodule.dto.UserRequestDto;
import org.example.authmodule.dto.UserVerificationDto;
import org.example.authmodule.kafka.VerificationProducer;
import org.example.authmodule.model.UserEntity;
import org.example.authmodule.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VerificationProducer verificationProducer;

    public void generateAndSendCode(UserRequestDto requestDto){

        String email = requestDto.getEmail();
        String code = UUID.randomUUID().toString().substring(0,6).toUpperCase();
        Instant expiresAt = Instant.now().plusSeconds(300);

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElse(new UserEntity(email, code, expiresAt));

        userEntity.setVerificationCode(code);
        userEntity.setVerificationExpiresAt(expiresAt);
        userEntity.setVerified(false);
        userRepository.save(userEntity);

        verificationProducer.send(email, code);
    }

    public boolean verifyCode(UserVerificationDto userVerificationDto) {

        String email = userVerificationDto.getEmail();
        String code = userVerificationDto.getCode();

        return userRepository.findByEmail(email)
                .filter(u -> !u.getVerified())
                .filter(u -> u.getVerificationCode().equals(code))
                .filter(u -> u.getVerificationExpiresAt().isAfter(Instant.now()))
                .map(u -> {
                    u.setVerified(true);
                    userRepository.save(u);
                    return true;
                }).orElse(false);
    }
}
