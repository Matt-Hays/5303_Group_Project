package hotel.reservations.services.roomDAO;

import hotel.reservations.models.reservation.Reservation;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.Response;

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
