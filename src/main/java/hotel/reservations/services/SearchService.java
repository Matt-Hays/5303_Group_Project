package hotel.reservations.services;

import hotel.reservations.models.room.Bed;
import hotel.reservations.models.room.Room;

import java.time.LocalDate;
import java.util.List;

public interface SearchService {
    public List<Room> searchRooms(LocalDate arrival, LocalDate departure, int numberOfBeds, Bed typeOfBeds, boolean smoking);
}
