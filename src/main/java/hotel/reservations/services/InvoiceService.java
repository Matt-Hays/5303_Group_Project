package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.services.invoiceDAO.IInvoiceDAO;

import java.util.Set;
import java.util.UUID;

/**
 * Provides service into the Invoice.
 * @author matthewhays
 * @version 1.0
 *     - Initial draft 7/29/22 Matthew Hays
 */
public class InvoiceService implements Service<Invoice, UUID>{
    private final IInvoiceDAO invoiceDAO;

    public InvoiceService(IInvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public Invoice findById(UUID id) {
        return null;
    }

    @Override
    public Set<Invoice> findAll() {
        return null;
    }

    @Override
    public Invoice save(Invoice object) {
        return null;
    }

    @Override
    public Invoice create(Invoice object) {
        return null;
    }

    @Override
    public void delete(Invoice object) {

    }
}
