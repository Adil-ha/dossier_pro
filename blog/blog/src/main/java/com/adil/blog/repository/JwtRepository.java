package com.adil.blog.repository;

import com.adil.blog.entity.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends JpaRepository<Jwt, Long> {

    Optional<Jwt> findByValueAndIsDisableAndIsExpirate(String value, boolean isDisable, boolean isExpirate);

    @Query("FROM Jwt j WHERE j.isExpirate = :isExpirate AND j.isDisable = :isDisable AND j.user.email = :email ")
    Optional<Jwt> findUserValidToken(String email, boolean isDisable, boolean isExpirate);

    @Query("FROM Jwt j WHERE j.user.email = :email ")
    Stream<Jwt> findUser(String email);

    void deleteAllByIsExpirateAndIsDisable(boolean isEsperate, boolean isDisable);
}
