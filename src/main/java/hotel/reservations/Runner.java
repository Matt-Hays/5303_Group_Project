package hotel.reservations;

import hotel.reservations.controller.AppController;
import hotel.reservations.controller.ReservationHandler;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.HotelModels;
import hotel.reservations.views.GuiFrame;
import hotel.reservations.views.GuiHandler;
import hotel.reservations.views.HotelViews;

public class Runner {
    public static void main(String[] args) throws NoSuchMethodException {

        // Views
//        HotelViews views = new HotelViews();
//        // Models
        HotelModels models = new HotelModels();
//        // Persistence Layer
        Database db = Database.Database();
//        // Controller
        ReservationHandler reservationHandler = new AppController(models, db);
        GuiHandler guiFrame = new GuiFrame(reservationHandler);
    }
}