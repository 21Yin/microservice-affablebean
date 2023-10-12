package com.example.apisecurity.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("""
select u from User u inner join u.tokens t on u.id = t.user.id
where u.id=:id and t.refreshToken=:refreshToken and t.expiredAt >=:expiredAt
""")
    Optional<User> findUserIdAndTokenByRefreshToken(
            @Param("id") Long id,
            @Param("refreshToken") String refreshToken,
            @Param("expiredAt")LocalDateTime expiredAt);

}
