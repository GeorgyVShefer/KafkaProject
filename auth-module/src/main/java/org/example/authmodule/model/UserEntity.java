package org.example.authmodule.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "verified")
    private Boolean verified = false;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expires")
    private Instant verificationExpiresAt;
    @Column(name = "created")
    private Instant createdAt = Instant.now();

    public UserEntity(String email, String verificationCode, Instant verificationExpiresAt) {

        this.email = email;
        this.verificationCode = verificationCode;
        this.verificationExpiresAt = verificationExpiresAt;
        this.verified = false;
    }
}