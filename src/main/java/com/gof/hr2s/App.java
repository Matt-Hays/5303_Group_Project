package com.gof.hr2s;

import com.gof.hr2s.controller.AppController;
//import com.gof.hr2s.models.HotelModels;
import com.gof.hr2s.ui.HotelViews;

public class App {
    public static void main(String[] args) throws NoSuchMethodException {

        // Views
        HotelViews views = new HotelViews();
        // Models
//        HotelModels models = new HotelModels();
        // Persistence Layer
        // Controller
        AppController controller = new AppController(views);
    }
}
