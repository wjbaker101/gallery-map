package com.wjbaker.gallery_map.database.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(schema = "auth", name = "user")
@NoArgsConstructor
@AllArgsConstructor
public final class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter @Setter
    @Column(name = "username")
    private String username;

    @Getter @Setter
    @Column(name = "password")
    private String password;

    @Getter @Setter
    @Column(name = "password_salt")
    private String passwordSalt;

    @Getter
    @Column(name = "created_at")
    private Instant createdAt;
}