package com.gof.hr2s.service;

import com.gof.hr2s.models.Reservation;
import com.gof.hr2s.models.Room;
import com.gof.hr2s.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

public class RoomCatalog {

    private static RoomCatalog roomCatalog = null;
    private Database db = null;

    private RoomCatalog() {

        db = Database.Database();
    }

    public static RoomCatalog getRoomCatalog() {
        if (null == roomCatalog) {
            roomCatalog = new RoomCatalog();
        }

        return roomCatalog;
    }

    public ArrayList<Room> filterRooms(LocalDate arrival, LocalDate departure) {

        ArrayList<Room> all_rooms = db.getAllRooms();
        ArrayList<Reservation> occupiedRooms = db.getOverlappingReservations(arrival, departure);
        ArrayList<Room> filteredRooms = new ArrayList<Room>();

        boolean found;
        for (int i = 0; i < all_rooms.size(); i++) {
            found = false;
            for (int j = 0; j < occupiedRooms.size(); j++) {
                if (all_rooms.get(i).roomId == occupiedRooms.get(j).getRoomNumber()) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                filteredRooms.add(all_rooms.get(i));
            }
        }

        return filteredRooms;
    }

    public Room getRoom(String roomId){
        return db.getRoom(Integer.parseInt(roomId));
    }

/*    public Room getRoom(int roomId) {
        // Build the query
        try {
            PreparedStatement ps = db.conn.prepareStatement(
                    "SELECT `bedType`, `numBeds`, `smoking`, `occupied`, 'nightly_rate' FROM `room` WHERE `id`=?;"
            );
            ps.setInt(1, roomId);

            // Execute the query
            ResultSet rs = ps.executeQuery();
            if (!validate(rs)) {
                logger.info("Empty set for room: " + roomId);
                return null;
            }

            Bed bedType = Bed.valueOf(rs.getString("bedType"));
            int numBeds = rs.getInt("numBeds");
            Boolean smoking = rs.getBoolean("smoking");
            Boolean occupied = rs.getBoolean("occupied");
            double nightly_rate = rs.getDouble("nightly_rate");

            return new Room(roomId, bedType, numBeds, smoking, occupied, nightly_rate);

        } catch (SQLException e) {
            db.logger.severe(e.getMessage());
        }

        return null;
    }*/


}