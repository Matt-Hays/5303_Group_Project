/**
 * @file RoomDao.java
 * @author Joshua Southern
 * @author Joshua Wellman
 * @brief Provides an interface for persistence level interactions on the Room domain object.
 */

package hotel.reservations.persistence.dao;

import hotel.reservations.models.room.Room;
import hotel.reservations.models.room.Bed;
import hotel.reservations.persistence.Response;

import java.time.LocalDate;
import java.util.ArrayList;

public interface RoomDao {
    public ArrayList<Room> filterRooms(LocalDate arrival, LocalDate departure, Bed bedType, int numBeds, boolean smoking);
    public hotel.reservations.models.room.Room getRoom(int roomId);
    public ArrayList<hotel.reservations.models.room.Room> getAllRooms();
    public Response updateRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate);
    public Response deleteRoom(int roomId);
}