package com.github.maiky1304.jobboard.session;

import com.github.maiky1304.jobboard.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public void activateSession(Session session) {
        Session activeSession = sessionRepository.findByUser(session.getUser());
        if (activeSession != null) {
            this.invalidateSession(activeSession);
        }

        sessionRepository.save(session);
    }

    public void invalidateSession(Session session) {
        sessionRepository.delete(session);
    }

    public boolean invalidateSessionByToken(String token) {
        Session session = sessionRepository.findByToken(token);
        if (session != null) {
            this.invalidateSession(session);
        }
        return session != null;
    }

    public boolean invalidateSessionByUser(User user) {
        Session session = sessionRepository.findByUser(user);
        if (session != null) {
            this.invalidateSession(session);
        }
        return session != null;
    }

    public boolean isSessionActive(String token) {
        return sessionRepository.findByToken(token) != null || sessionRepository.findByRefreshToken(token) != null;
    }

    public Session getSessionByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    public Session getSessionByRefreshToken(String token) {
        return sessionRepository.findByRefreshToken(token);
    }

    public void updateSession(Session session) {
        sessionRepository.save(session);
    }

}
