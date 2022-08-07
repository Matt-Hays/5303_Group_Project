/**
 * @file ReportService.java
 * @author Christian Haddad
 * @brief Provides and interface to interact with reporting requests.
 */

package hotel.reservations.services;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.models.room.Room;

import java.util.List;

public interface ReportService {
    public List<Invoice> getInvoiceList(String username);
}
