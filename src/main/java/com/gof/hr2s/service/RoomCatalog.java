package com.gof.hr2s.models.room;

import com.gof.hr2s.models.reservation.Reservation;
import com.gof.hr2s.models.room.Room;
import com.gof.hr2s.database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;

public class RoomCatalog {

    private static final RoomCatalog roomCatalog = null;
    private Database db = null;
    public RoomCatalog() {

        db = Database.Database();
    }


    public ArrayList<Room> filterRooms(LocalDate arrival, LocalDate departure) {

        ArrayList<Room> all_rooms = db.getAllRooms();
        ArrayList<Reservation> occupiedRooms = db.getOverlappingReservations(arrival, departure);
        ArrayList<Room> filteredRooms = new ArrayList<Room>();

        int i, j;
        for(i = 0; i < all_rooms.size(); i++) {
            for(j = 0; j < occupiedRooms.size(); j++) {
                if (all_rooms.get(i).roomId != occupiedRooms.get(j).getRoomId()) {
                    filteredRooms.add(all_rooms.get(i));
                    break;
                }
            }
        }

        return filteredRooms;
    }

    public Room getRoom(int roomId) {
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
    }

/*        public Room[] filterRooms(Room[] reservedRooms) {
        Room[] avail_rooms = new Room[40];
            int j = 0;
            for (int i = 0; i < reservedRooms.length; i++) {
                if (reservedRooms[i].getOccupied() == false) {
                    avail_rooms[j] = reservedRooms[i];
                    j++;
                }
            }
            return avail_rooms;
        }*/

/*
    public Room findAvailRoom(int roomId) {
        Room[] avail_rooms = new Room[40];
            int i = 0;
            for(i = 0; i < avail_rooms.length; i++) {
                if(avail_rooms[i].roomId == roomId){
                    break;
                }
            }
        return getRoom(avail_rooms[i].roomId);
    }
*/




    //getRoom written by ronwellman (pulled from database class)


/*
    public ArrayList<Room> avail_rooms = new ArrayList<Room>();
    public ArrayList<Room> filterRooms(ArrayList<Room> roomList, boolean isSmoking, int numBeds, Bed bedType) {
        for (int i = 0; i < roomList.size(); i++) {
            if(roomList.get(i).occupied == false && roomList.get(i).smoking == isSmoking &&
                    numBeds == roomList.get(i).numBeds && bedType == roomList.get(i) == bedType) {
                avail_rooms.add(roomList.get(i));
            }
        }
        return avail_rooms;
    }
    public ArrayList<Room> filterRooms(ArrayList<Room> roomList, boolean isSmoking) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).occupied == false && roomList.get(i).isSmoking == isSmoking) {
                avail_rooms.add(roomList.get(i));
            }
        }
        return avail_rooms;
    }
    public ArrayList<Room> filterRooms(ArrayList<Room> roomList, boolean isSmoking, int numBeds) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).occupied == false && roomList.get(i).isSmoking == isSmoking && roomList.get(i).numBeds == numBeds) {
                avail_rooms.add(roomList.get(i));
            }
        }
        return avail_rooms;
    }
    public ArrayList<Room> getAllAvailRooms(ArrayList<Room> roomList) {
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).occupied == false) {
                avail_rooms.add(roomList.get(i));
            }
        }
        return avail_rooms;
    }*/


}