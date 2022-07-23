package hotel.reservations.services.invoiceDAO;

import hotel.reservations.services.reservationDAO.IReservationDAO;
import hotel.reservations.persistence.Database;

public class IInvoiceDAO extends IReservationDAO {
    private static IInvoiceDAO dao = null;
    private static Database db = null;

    private IInvoiceDAO() {
    }

    public static IInvoiceDAO getInvoiceDAO() {
        if (null == dao) {
            dao = new IInvoiceDAO();
            db = Database.Database();
        }

        return dao;
    }

    // getInvoice
    // createInvoice
    // updateInvoice
    // deleteInvoice
}


