package hotel.reservations.persistence.dao;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.persistence.Response;

import java.util.UUID;

public interface InvoiceDao {

    // getInvoice
    public Invoice getInvoice(UUID invoiceId);


    // createInvoice
    public Invoice createInvoice();


    // updateInvoice
    public Response updateInvoice(Invoice invoice);


    // invoice will be deleted when reservation is deleted
//    public Response deleteInvoice(Invoice invoice);
}