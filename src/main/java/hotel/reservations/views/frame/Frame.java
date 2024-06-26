/**
 * @file Runner.java
 * @brief The interface to the GUI JFrame.
 * @author Matthew Hays
 */

package hotel.reservations.views.frame;

import hotel.reservations.controller.AppController;
import hotel.reservations.models.session.Session;
import hotel.reservations.views.admin.AdminPanel;
import hotel.reservations.views.billingReport.BillingReportPanel;
import hotel.reservations.views.clerk.ClerkPanel;
import hotel.reservations.views.home.HomePanel;
import hotel.reservations.views.login.LoginPanel;
import hotel.reservations.views.register.RegisterPanel;
import hotel.reservations.views.reports.StatusReportPanel;
import hotel.reservations.views.reservation.ReservationPanel;
import hotel.reservations.views.reservation.ReservationsPanel;
import hotel.reservations.views.room.RoomPanel;
import hotel.reservations.views.room.RoomsPanel;
import hotel.reservations.views.search.SearchPanel;
import hotel.reservations.views.user.UserPanel;

/**
 * The parent frame (JFrame) interface.
 * Provides an interface such that pages can access additional pages and the controller.
 */
public interface Frame {
    public void changeScreen(String screenName);

    public AppController getAppController();

    public void setSession(Session session);
    public Session getSession();

    public void setHomePanel(String userType);

    public HomePanel getHomePanel();

    public LoginPanel getLoginPanel();

    public ReservationPanel getReservationPanel();

    public AdminPanel getAdminPanel();

    public RegisterPanel getRegisterPanel();

    public SearchPanel getSearchPanel();

    public UserPanel getUserPanel();

    public RoomPanel getRoomPanel();
    public RoomsPanel getRoomsPanel();

    public ReservationsPanel getReservationsPanel();

    public ClerkPanel getClerkPanel();

    public StatusReportPanel getStatusReportPanel();

    public BillingReportPanel getBillingReportPanel();
}
