package com.gof.hr2s.service;

import com.gof.hr2s.models.Account;
import com.gof.hr2s.models.Reservation;
import com.gof.hr2s.models.Room;
import com.gof.hr2s.models.Session;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class HotelModels {
    private static ReservationCatalog reservationCatalog;
    private static RoomCatalog roomCatalog;
    private static UserCatalog userCatalog;
    private static SessionCatalog sessionCatalog;

    public HotelModels() {
        this.reservationCatalog = ReservationCatalog.getReservationCatalog();
        this.roomCatalog = RoomCatalog.getRoomCatalog();
        this.userCatalog = UserCatalog.getUserCatalog();
        this.sessionCatalog = SessionCatalog.getSessionCatalog();
    }

    public static ArrayList<Room> searchAvailableRooms(LocalDate arrival, LocalDate departure) {
        return roomCatalog.filterRooms(arrival, departure);
    }

    public static Object getUserByUsernameCatalog(String username){
        return userCatalog.getUserByUsername(username);
    }

    public static UUID createNewSession(Object user){
        return sessionCatalog.createSession(user);
    }

    public static Object getSessionUser(String sessionId){
        return sessionCatalog.getSession(sessionId);
    }


    public static void createGuest(Account type, String username, String hashed_password,
                                   String fName, String lName, boolean active){
        userCatalog.createNewGuest(type, username, hashed_password, fName, lName, active);
    }


}
