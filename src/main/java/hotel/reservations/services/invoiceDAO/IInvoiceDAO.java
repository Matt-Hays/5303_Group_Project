package hotel.reservations.services.invoiceDAO;

import hotel.reservations.models.reservation.Invoice;

public interface IInvoiceDAO {

    // getInvoice
    public Invoice getInvoice(UUID invoiceId) {
        return Invoice;
    }

    // createInvoice
    public Invoice createInvoice(){
        invoice = new Invoice();
        return invoice;
    }

    // updateInvoice
    public Invoice updateInvoice(Invoice invoice){
        return invoice;
    }

    // deleteInvoice
    public deleteInvoice(Invoice invoice){
        return invoice;
    }
}


