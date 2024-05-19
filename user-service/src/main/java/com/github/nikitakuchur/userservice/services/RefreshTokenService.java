package com.github.nikitakuchur.userservice.services;

import com.github.nikitakuchur.userservice.model.RefreshToken;
import com.github.nikitakuchur.userservice.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * A service that is responsible for refresh tokens.
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Saves the given refresh token into the database.
     *
     * @param refreshToken the refresh token to save
     */
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * Finds refresh token details by the corresponding token.
     *
     * @param token the token to find
     * @return an Optional containing the refresh token, or empty if no token is found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Retrieves all refresh tokens associated with the given username.
     *
     * @param username the username
     * @return a list of refresh tokens
     */
    public List<RefreshToken> findByUsername(String username) {
        return refreshTokenRepository.findByUsername(username);
    }

    /**
     * Revokes all refresh tokens attached to the given session.
     *
     * @param sessionId the session ID
     */
    public void revokeAllBySessionId(String sessionId) {
        var tokens = refreshTokenRepository.findBySessionId(sessionId);
        tokens.forEach(t -> t.setInvalidated(true));
        refreshTokenRepository.saveAll(tokens);
    }

    /**
     * Revokes all refresh tokens associated with the given session.
     *
     * @param username the username
     */
    public void revokeAllByUser(String username) {
        var tokens = refreshTokenRepository.findByUsername(username);
        tokens.forEach(t -> t.setInvalidated(true));
        refreshTokenRepository.saveAll(tokens);
    }
}
