/**
 * @file RoomService.java
 * @author Matthew Hays
 * @brief Provides an interface to interact with Room domain requests.
 */

package hotel.reservations.services;

import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Response;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking);
    public List<Room> getAllRooms();
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);

    /**
     * Needs to be moved inside the layer; not external.
     */
    public Room getRoom(int roomId);
    public Response updateRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Response deleteRoom(int roomId);

}
