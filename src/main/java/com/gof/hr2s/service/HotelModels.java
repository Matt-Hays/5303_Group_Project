package com.gof.hr2s.service;

import com.gof.hr2s.models.Reservation;
import com.gof.hr2s.models.Room;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelModels {
    private static ReservationCatalog reservationCatalog;
    private static RoomCatalog roomCatalog;

    public HotelModels() {
        this.reservationCatalog = ReservationCatalog.getReservationCatalog();
        this.roomCatalog = RoomCatalog.getRoomCatalog();
    }

    // ArrayList<Reservation> findReservations(LocalDate arrival, LocalDate departure)

    public static ArrayList<Room> searchAvailableRooms(LocalDate arrival, LocalDate departure) {
        return roomCatalog.filterRooms(arrival, departure);
    }
}
