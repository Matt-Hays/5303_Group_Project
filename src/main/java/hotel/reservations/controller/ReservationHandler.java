package hotel.reservations.controller;

import java.time.LocalDate;
import java.util.UUID;

public interface ReservationHandler {
    public UUID loginUser(String username, char[] password);

    public void registerUser(String username, char[] password, String firstName, String lastName, String address,
                             String city, String state, String zipCode);

    public void searchRooms(LocalDate arrival, LocalDate departure);
}
