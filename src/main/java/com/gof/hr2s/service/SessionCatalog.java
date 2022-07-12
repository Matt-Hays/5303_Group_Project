package com.gof.hr2s.service;


import com.gof.hr2s.models.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class SessionCatalog {
    private static ArrayList<Session> sessions;

    public SessionCatalog() {
        if (sessions == null) {
            sessions = new ArrayList<Session>();
        }
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
