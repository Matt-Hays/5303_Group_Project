package hotel.reservations.services.impls;

import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;
import hotel.reservations.persistence.dao.RoomDao;
import hotel.reservations.services.SearchService;

import java.time.LocalDate;
import java.util.List;

public class SearchServiceImpl implements SearchService {
    private final RoomDao roomDao;

    public SearchServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking) {
        return roomDao.filterRooms(arrival, departure, typeOfBeds, numberOfBeds, smoking);
    }
}
