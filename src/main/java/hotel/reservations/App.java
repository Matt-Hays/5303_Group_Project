package hotel.reservations;

import hotel.reservations.controller.AppController;
import hotel.reservations.models.user.IUser;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.HotelModels;
import hotel.reservations.views.HotelViews;

public class App {
    public static void main(String[] args) throws NoSuchMethodException {

        // Views
        Database db = Database.Database();
        IUser userdao = new UserDAO(db);
        // Persistence Layer
        // Controller
        AppController controller = new AppController(userdao);
    }
}