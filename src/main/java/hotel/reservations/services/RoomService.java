package hotel.reservations.services;

import hotel.reservations.models.room.Room;
import hotel.reservations.services.roomDAO.IRoomDAO;

import java.util.Set;
import java.util.UUID;

public class RoomService implements Service<Room, UUID> {
    private final IRoomDAO roomDAO;

    public RoomService(IRoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Override
    public Room findById(UUID id) {
        return null;
    }

    @Override
    public Set<Room> findAll() {
        return null;
    }

    @Override
    public Room save(Room object) {
        return null;
    }

    @Override
    public Room create(Room object) {
        return null;
    }

    @Override
    public void delete(Room object) {

    }
}
