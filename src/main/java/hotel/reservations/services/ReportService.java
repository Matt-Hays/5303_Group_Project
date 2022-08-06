package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;

import java.util.List;

public interface ReportService {

    public List<Invoice> getInvoiceList(String username);
}
