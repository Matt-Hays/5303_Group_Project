package com.gof.hr2s;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.models.*;
import com.gof.hr2s.service.ReservationCatalog;
import com.gof.hr2s.service.RoomCatalog;
import com.gof.hr2s.service.UserCatalog;
import com.gof.hr2s.ui.HotelReservationGUI;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                // make catalogs and db connections
                ReservationCatalog reserveCat = ReservationCatalog.getReservationCatalog();
                UserCatalog uc = UserCatalog.getUserCatalog();
                RoomCatalog roomCat = RoomCatalog.getRoomCatalog();
                Database db = Database.Database();

                // get the default admin user
                Object obj = db.getUser("admin");
                if (obj instanceof Admin) {
                    Admin admin = (Admin)obj;

                    // have the admin create some users
                    admin.createUser(Account.GUEST, "guest1", "guestFirst1", "guestLast1");
                    admin.createUser(Account.CLERK, "clerk1", "clerkFirst1", "clerkLast1");

                    obj = db.getUser("guest1");
                    if (!(obj instanceof Guest)) {
                        System.out.println("Can't make a guest");
                        return;
                    }

                    // see if there are rooms available
                    ArrayList<Room> rooms = roomCat.filterRooms(LocalDate.parse("2022-07-01"), LocalDate.parse("2022-07-15"));
                    if (0 == rooms.size()) {
                        System.out.println("The rooms are gone!");
                        return;
                    }

                    // have the guest create a reservation
                    Guest guest = (Guest) obj;
                    Reservation r1 = guest.createReservation(LocalDate.parse("2022-07-01"), LocalDate.parse("2022-07-15"), rooms.get(0));
                    if (null == r1) {
                        System.out.println("Reservation not created");
                        return;
                    }

                    // have the guest modify their account
                    guest.setUsername("username2");
                    guest.update();
                    System.out.println(guest.update());

                    // lookup all the reservations
                    ArrayList<Reservation> reservations = reserveCat.findReservations(LocalDate.parse("2022-07-01"), LocalDate.parse("2022-07-15"));
                    if (0 == reservations.size()) {
                        System.out.println("No reservations found");
                        return;
                    }

                    Reservation r2 = db.getReservation(r1.getReservationId());
                    if (null == r2) {
                        System.out.println("Reservation not found during lookup");
                        return;
                    }

                    // now it's the clerk's turn
                    obj = db.getUser("clerk1");
                    if (!(obj instanceof Clerk)) {
                        System.out.println("Can't make a clerk");
                        return;
                    }

                    Clerk clerk = (Clerk)obj;

                    // have the clerk make a reservation for the guest
                    Guest newGuest = clerk.getUser("guest1");
                    clerk.setCustomer(newGuest);
                    // overlaps with previous reservation
                    Reservation r3 = guest.createReservation(LocalDate.parse("2022-07-13"), LocalDate.parse("2022-07-16"), rooms.get(1));
                    if (null == r3) {
                        System.out.println("Clerk unable to make reservation for guest");
                        return;
                    }

                    // see if there are rooms available
                    rooms = roomCat.filterRooms(LocalDate.parse("2022-07-01"), LocalDate.parse("2022-07-15"));
                    if (0 == rooms.size()) {
                        System.out.println("The rooms are gone!");
                        return;
                    }

                    System.out.println("Rooms left: " + rooms.size());
                    for(Room rm: rooms) {
                        System.out.println("Room: " + rm.roomId);
                    }

                } else {
                    System.out.println("Well that didn't work");
                    return;
                }

                // Views
                HotelReservationGUI gui = new HotelReservationGUI();
                // Models
                // Controller
                // Persistence Layer
            }
        });
    }
}
