package hotel.reservations;

import hotel.reservations.controllers.AppController;
import hotel.reservations.persistence.Database;
import hotel.reservations.services.HotelModels;
import hotel.reservations.views.HotelViews;

public class App {
    public static void main(String[] args) throws NoSuchMethodException {

        // Views
        HotelViews views = new HotelViews();
        // Models
        HotelModels models = new HotelModels();
        // Persistence Layer
        Database db = Database.Database();
        // Controller
        AppController controller = new AppController(models, views, db);
    }
}