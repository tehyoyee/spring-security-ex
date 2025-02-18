package com.taehyeong.backend.config;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.util.Assert;

import java.util.List;

public class CustomSessionRegistryImpl implements SessionRegistry {

    SessionRegistryImpl delegate = new SessionRegistryImpl();

    @Override
    public List<Object> getAllPrincipals() {
        return delegate.getAllPrincipals();
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        return delegate.getAllSessions(principal, includeExpiredSessions);
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        return delegate.getSessionInformation(sessionId);
    }

    @Override
    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
        }

    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        delegate.registerNewSession(sessionId, principal);
    }

    @Override
    public void removeSessionInformation(String sessionId) {
        delegate.removeSessionInformation(sessionId);
    }

}
