package hotel.reservations.services.invoiceDAO;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.services.Response;

import java.util.UUID;

public interface IInvoiceDAO {

    // getInvoice
    public Invoice getInvoice(UUID invoiceId);


    // createInvoice
    public Invoice createInvoice();


    // updateInvoice
    public Response updateInvoice(Invoice invoice);


    // deleteInvoice
    public Response deleteInvoice(Invoice invoice);
}