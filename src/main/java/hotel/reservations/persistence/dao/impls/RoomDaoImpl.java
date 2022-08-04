package hotel.reservations.persistence.dao.impls;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.persistence.dao.RoomDao;
import hotel.reservations.models.room.Bed;
import hotel.reservations.persistence.dao.ReservationDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDaoImpl implements RoomDao {

    private Database db = null;
    private ReservationDao rd = null;

    public RoomDaoImpl(Database database, ReservationDao reservationDAO) {
        db = database;
        rd = reservationDAO;
    }

    public ArrayList<Room> filterRooms(LocalDate arrival, LocalDate departure, Bed bedType, int numBeds, boolean smoking) {

        ArrayList<Room> all_rooms = getFilteredRooms(bedType, numBeds, smoking);
        ArrayList<Reservation> occupiedRooms = rd.findReservations(arrival, departure);
        ArrayList<Room> filteredRooms = new ArrayList<>();

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
        ResultSet rs = db.getRoom(Integer.parseInt(roomId));
        return createRoom(rs);
    }

    /**
     * Creates a list of rooms
     */
    public ArrayList<Room> getAllRooms(){
        ArrayList<Room> rooms = new ArrayList<>();
        ResultSet rs =  db.getAllRooms();
        if (null == rs) {
            return rooms;
        }

        try {
            while (rs.next()) {
                rooms.add(createRoom(rs));
            }
        } catch (SQLException e) {
        }

        return rooms;
    }

    /**
     * Create a list of rooms filtered by criteria
     * @param bedType
     * @param numBeds
     * @param smoking
     * @return ArrayList of rooms or empty ArrayList on error
     */
    public ArrayList<Room> getFilteredRooms(Bed bedType, int numBeds, boolean smoking) {
        ArrayList<Room> rooms = new ArrayList<>();
        ResultSet rs =  db.getFilteredRooms(bedType, numBeds, smoking);
        if (null == rs) {
            return rooms;
        }

        try {
            while (rs.next()) {
                rooms.add(createRoom(rs));
            }
        } catch (SQLException e) {
        }

        return rooms;
    }

    /**
     * Inserts a room in the database
     * @param roomId
     * @param bedType
     * @param numBeds
     * @param smoking
     * @param occupied
     * @param nightly_rate
     * @return Response - Success or Fail
     */
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
        return db.insertRoom(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
    }

    /**
     * Create a room
     * @param rs resultset
     * @return Room or null if error
     */
    private Room createRoom(ResultSet rs) {
        if (rs == null) {
            return null;
        }

        int roomId;
        try {
            roomId = rs.getInt("id");
            Bed bedType = Bed.valueOf(rs.getString("bedType"));
            int numBeds = rs.getInt("numBeds");
            boolean smoking = rs.getBoolean("smoking");
            boolean occupied = rs.getBoolean("occupied");
            double nightly_rate = rs.getDouble("nightlyRate");

            return new Room(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
        } catch (SQLException e) {
        }

        return null;
    }

    public Response updateRoom(Room room){
        return db.updateRoom(room.getRoomId(), room.getBedType(), room.getNumBeds(),
            room.getSmoking(), room.getOccupied(), room.getNightlyRate());
    }

    /**
     * Delete a room from the db
     * @param roomId
     * @return Response - Success or Fail
     */
    public Response deleteRoom(int roomId) {
        return db.deleteRoom(roomId);
    }
}