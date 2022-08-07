package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.room.Room;

import java.util.List;

public interface ReportService {
    public List<Invoice> getInvoiceList(String username);
}
