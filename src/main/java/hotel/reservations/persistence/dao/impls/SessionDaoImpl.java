package hotel.reservations.persistence.dao.impls;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.User;
import hotel.reservations.persistence.dao.SessionDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SessionDaoImpl implements SessionDao {
    private List<Session> sessions;

    public SessionDaoImpl(){
        sessions = new ArrayList<Session>();
    }

    @Override
    public UUID createSession(User user) {
        if(user == null) return null;
        Session newSession = new Session(user);
        sessions.add(newSession);
        return newSession.getId();
    }

    @Override
    public boolean validateSession(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        while(itr.hasNext()){
            if(itr.next().equals(sessionId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public User getSessionUser(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        while(itr.hasNext()){
            Session temp = itr.next();
            if(temp.getId().equals(sessionId)){
                return temp.getUser();
            }
        }
        return null;
    }

    @Override
    public void destroySessionById(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        while(itr.hasNext()){
            if(itr.next().getId().equals(sessionId)){
                itr.remove();
            }
        }
    }
}
