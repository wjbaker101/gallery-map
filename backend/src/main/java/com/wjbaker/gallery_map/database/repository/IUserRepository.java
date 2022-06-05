package com.wjbaker.gallery_map.database.repository;

import com.wjbaker.gallery_map.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select user from UserEntity user where upper(user.username) = upper(:username) ")
    Optional<UserEntity> findByUsername(final String username);
}