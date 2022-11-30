package com.github.maiky1304.jobboard.security.jwt;

import com.github.maiky1304.jobboard.session.Session;
import com.github.maiky1304.jobboard.session.SessionService;
import com.github.maiky1304.jobboard.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "").trim();

        if (jwtTokenUtil.verifyJwt(token) == null || jwtTokenUtil.isTokenExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        User user = jwtTokenUtil.getUserFromToken(token);
        if (user == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!sessionService.isSessionActive(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("session is active!");

        Session session = sessionService.getSessionByToken(token);
        if (session == null) {
            session = sessionService.getSessionByRefreshToken(token);
        }

        if (Objects.equals(session.getRefreshToken(), token)) {
            session.setToken(token);
            session.setRefreshToken(null);
            sessionService.updateSession(session);
        }

        if (jwtTokenUtil.eligibleForRefresh(token)) {
            session.setRefreshToken(jwtTokenUtil.generateJwt(user));
            sessionService.updateSession(session);

            response.setHeader(AUTHORIZATION, session.getRefreshToken());
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

}
