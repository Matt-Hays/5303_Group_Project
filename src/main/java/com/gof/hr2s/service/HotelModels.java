package com.gof.hr2s.service;

import com.gof.hr2s.models.Reservation;
import com.gof.hr2s.models.Room;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelModels {
    private static ReservationCatalog reservationCatalog;
    private static RoomCatalog roomCatalog;
    private static UserCatalog userCatalog;

    public HotelModels() {
        this.reservationCatalog = ReservationCatalog.getReservationCatalog();
        this.roomCatalog = RoomCatalog.getRoomCatalog();
        this.userCatalog = UserCatalog.getUserCatalog();
    }

    public static ArrayList<Room> searchAvailableRooms(LocalDate arrival, LocalDate departure) {
        return roomCatalog.filterRooms(arrival, departure);
    }

    public static Object getUserByUsernameCatalog(String username){
        return userCatalog.getUserByUsername(username);
    }
}
