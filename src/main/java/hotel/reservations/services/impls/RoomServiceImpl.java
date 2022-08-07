package hotel.reservations.services.impls;

import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.Response;
import hotel.reservations.persistence.dao.RoomDao;
import hotel.reservations.services.RoomService;

import java.time.LocalDate;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;

    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking) {
        return roomDao.filterRooms(arrival, departure, typeOfBeds, numberOfBeds, smoking);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomDao.getAllRooms();
    }

    @Override
    public Response createRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
        return roomDao.createRoom(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
    }

    @Override
    public Room getRoom(int roomId) {
        return roomDao.getRoom(roomId);
    }

    @Override
    public Response updateRoom(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied, double nightly_rate) {
        return roomDao.updateRoom(roomId, bedType, numBeds, smoking, occupied, nightly_rate);
    }

    @Override
    public Response deleteRoom(int roomId) {
        return roomDao.deleteRoom(roomId);
    }
}
