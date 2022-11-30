package com.github.maiky1304.jobboard.session;

import com.github.maiky1304.jobboard.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    Session findByUser(User user);
    Session findByToken(String token);
    Session findByRefreshToken(String refreshToken);

}
