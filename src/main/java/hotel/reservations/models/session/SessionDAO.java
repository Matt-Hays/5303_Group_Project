package hotel.reservations.models.session;

import hotel.reservations.models.user.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SessionDAO implements ISessionDAO{
    private List<Session> sessions;

    public SessionDAO(){
        sessions = new ArrayList<Session>();
    }

    @Override
    public UUID createSession(User user) {
        Session newSession = new Session(user);
       sessions.add(newSession);
       return newSession.getId();
    }

    @Override
    public boolean validateSession(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        while(itr.hasNext()){
            if(itr.next().getId().equals(sessionId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroySession(UUID sessionId) {
        Iterator<Session> itr = sessions.iterator();
        while(itr.hasNext()){
            if(itr.next().getId().equals(sessionId)){
                itr.remove();
            }
        }
    }
}
