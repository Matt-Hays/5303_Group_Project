package hotel.reservations.services.RoomDAO;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;
import hotel.reservations.services.RoomCatalog;

import java.time.LocalDate;
import java.util.ArrayList;

public class RoomDAO implements IRoomDAO {

    private Database db = null;

    public RoomDAO(Database database) {
        db = database;
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

    public ArrayList<Room> getAllRooms(){
        return db.getAllRooms();
    }

    public Response updateRoom(Room room){
        return db.updateRoom(room);
    }
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