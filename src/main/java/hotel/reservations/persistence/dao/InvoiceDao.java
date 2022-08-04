package hotel.reservations.persistence.dao;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.persistence.dao.impls.Response;

import java.util.UUID;

public interface InvoiceDao {

    // getInvoice
    public Invoice getInvoice(UUID invoiceId);


    // createInvoice
    public Invoice createInvoice();


    // updateInvoice
    public Response updateInvoice(Invoice invoice);


    // deleteInvoice
    public Response deleteInvoice(Invoice invoice);
}