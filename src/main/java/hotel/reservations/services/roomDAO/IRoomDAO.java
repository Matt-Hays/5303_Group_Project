
package hotel.reservations.services.roomDAO;

import hotel.reservations.models.room.Room;
import hotel.reservations.services.Response;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IRoomDAO {
    public ArrayList<Room> filterRooms(LocalDate arrival, LocalDate departure);
    public hotel.reservations.models.room.Room getRoom(String roomId);
    public ArrayList<hotel.reservations.models.room.Room> getAllRooms();
    public Response updateRoom(hotel.reservations.models.room.Room room);
}