package com.gof.hr2s.service;


import com.gof.hr2s.models.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class SessionCatalog {
    private static SessionCatalog sessionCatalog = null;
    private static ArrayList<Session> sessions = new ArrayList<Session>();

    private SessionCatalog() {

    }

    public static SessionCatalog getSessionCatalog(){
        if (null == sessionCatalog){
            sessionCatalog = new SessionCatalog();
        }
        return sessionCatalog;
    }

    public static UUID createSession(Object user){
        Session newSession = new Session(user);
        addSession(newSession);
        return newSession.getId();
    }

    // Add a Session to the array list.
    public static void addSession(Session session) {
        sessions.add(session);
    }

    // R ead
    public static Object getSession(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        while (itr.hasNext()) {
            if (itr.next().getId().equals(sessionId)) {
                Object sessionUser = itr.next().getUser();
                return sessionUser;
            }
        }
        return null;
    }

    // No Update allowed
    // Delete
    public static void deleteSession(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        // Remove the Session from the SessionCatalog
        while (itr.hasNext()) {
            if (itr.next().getId().equals(sessionId)) {
                itr.remove();
                break;
            }
        }
    }
}
