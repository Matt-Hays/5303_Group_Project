
package hotel.reservations.persistence.dao.room;

import hotel.reservations.models.room.Room;
import hotel.reservations.models.room.Bed;
import hotel.reservations.services.Response;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IRoomDAO {
    public ArrayList<Room> filterRooms(LocalDate arrival, LocalDate departure, Bed bedType, int numBeds, boolean smoking);
    public hotel.reservations.models.room.Room getRoom(String roomId);
    public ArrayList<hotel.reservations.models.room.Room> getAllRooms();
    public Response updateRoom(hotel.reservations.models.room.Room room);
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Response deleteRoom(int roomId);
}