package hotel.reservations.services.invoiceDAO;

import hotel.reservations.services.reservationDAOImpl.reservationDAOImpl;
import hotel.reservations.persistence.Database;

public class invoiceDAOImpl extends reservationDAOImpl {
    private static invoiceDAOImpl dao = null;
    private static Database db = null;

    private invoiceDAOImpl() {
    }

    public static invoiceDAOImpl getInvoiceDAO() {
        if (null == dao) {
            dao = new invoiceDAOImpl();
            db = Database.Database();
        }

        return dao;
    }

    // getInvoice
    // createInvoice
    // updateInvoice
    // deleteInvoice
}

