//package hotel.reservations.services;
//
//import hotel.reservations.persistence.Database;
//
//public class UserCatalog {
//    public static UserCatalog userCatalog = null;
//
//    private Database db = null;
//
//    private UserCatalog() {
//        db = Database.Database();
//    }
//
//    public static UserCatalog getUserCatalog() {
//        if(null == userCatalog) {
//            userCatalog = new UserCatalog();
//        }
//
//        return userCatalog;
//    }
//
//}
