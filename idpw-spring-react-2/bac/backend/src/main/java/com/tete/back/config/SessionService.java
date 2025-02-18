package com.tete.back.config;

import com.tete.back.entity.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRegistry sessionRegistry;

    public void printAllSessionsInfo() {

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object principal : allPrincipals) {
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                // 현재 사용자의 모든 세션 정보를 가져옵니다.
                List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetails, false);
                sessions.forEach(s-> System.out.println(userDetails.getUsername()+" : "+ s.getSessionId()));
            }
        }

    }

    public List<UserDetailsImpl> fetchAllSessions() {

        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<UserDetailsImpl> userDetailsList = new ArrayList<>();

        for (Object principal : allPrincipals) {
            if (principal instanceof UserDetailsImpl) {
                userDetailsList.add((UserDetailsImpl) principal);
            }
        }
        return userDetailsList;

//        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
//            allPrincipals.stream().map(
//                    p->p)
//            for (Object principal : allPrincipals) {
//                if (principal instanceof UserDetails) {
//                    UserDetails userDetails = (UserDetails) principal;
//                    // 현재 사용자의 모든 세션 정보를 가져옵니다.
//                    List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetails, false);
//                    sessions.forEach(s-> System.out.println(userDetails.getUsername()+" : "+ s.getSessionId()));
//                }
//            }

    }

}
