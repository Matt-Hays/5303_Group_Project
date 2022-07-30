package hotel.reservations.services;

import hotel.reservations.models.session.Session;
import hotel.reservations.models.user.Clerk;

/**
 * Provides an interface for Admin function.
 * @author matthewhays
 * @version 1.0
 *     - Initial draft 7/29/22 Matthew Hays
 */
public interface AdminService {
    Clerk createClerk(Session session, Clerk clerk);
    Report viewReport(Session session);
    Status viewStatus(Session session);
}
