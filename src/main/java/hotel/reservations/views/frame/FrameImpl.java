/**
 * @file FrameImpl.java
 * @author Matthew Hays
 * @brief The User Interface's primary frame *JFrame* implementing object.
 * @dependencies AppController.java
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
import hotel.reservations.views.room.*;
import hotel.reservations.views.search.SearchPanel;
import hotel.reservations.views.user.ResetGuestCredentials;
import hotel.reservations.views.user.UserPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The parent frame of all user interface pages (JPanel).
 * Provides page switching and access to additional pages to all children pages.
 * Additionally, stores the Session object for the user for caching of the User object and endpoint authorization.
 */
public class FrameImpl extends JFrame implements Frame {
    private AppController appController;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Session session;

    public FrameImpl(AppController appController) {
        // Inject AppController dependency.
        setApplicationController(appController);

        setSize(850, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hotel Reservations");

        // Set up the JFrame to use a primary JPanel that is injected with differing "cards" **our custom JPanels.
        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cardPanel.getLayout();

        /**
         * Create all user interface pages (JPanel) and add them to the frame's card layout.
         * The index of each card is commented to the right of each entry.
         */
        cardPanel.add(new HomePanel(this), "home"); // 0
        cardPanel.add(new LoginPanel(this), "login"); // 1
        cardPanel.add(new RegisterPanel(this), "register"); // 2
        cardPanel.add(new SearchPanel(this), "search"); // 3
        cardPanel.add(new UserPanel(this), "account"); // 4
        cardPanel.add(new AdminPanel(this), "admin"); // 5
        cardPanel.add(new ResetGuestCredentials(this), "reset-guest-credentials"); // 6
        cardPanel.add(new RoomPanel(this), "room"); // 7
        cardPanel.add(new RoomsPanel(this), "rooms"); // 8
        cardPanel.add(new ReservationPanel(this), "reservation"); // 9
        cardPanel.add(new ReservationsPanel(this), "reservations"); // 10
        cardPanel.add(new ClerkPanel(this), "clerk"); // 11
        cardPanel.add(new StatusReportPanel(this), "status-report"); // 12
        cardPanel.add(new BillingReportPanel(this), "billing-report"); //13
        cardPanel.add(new RoomManagementPanel(this), "roomMgmt"); // 14
        cardPanel.add(new CreateRoomPanel(this), "createRoom"); // 15
        cardPanel.add(new ModifyRoomPanel(this), "modifyRoom"); // 16
        //cardPanel.add(new DeleteRoomPanel(this), "deleteRoom"); // 15

        // Add our built CardPanel to the JFrame.
        add(cardPanel);

        // Display the JFrame.
        setVisible(true);
    }

    /**
     * Given a requested screen name, change the current screen to the requested screen.
     * @param screenName The requested screen name.
     */
    @Override
    public void changeScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    /**
     * Standard getter and setter methods follow through the end of the file.
     * These methods allow pages to gain access to other pages as well as the controller.
     */

    private void setApplicationController(AppController appController){
        this.appController = appController;
    }

    @Override
    public AppController getAppController() {
        return appController;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setHomePanel(String userType) {

    }

    @Override
    public HomePanel getHomePanel() {
        return (HomePanel) cardPanel.getComponent(0);
    }

    @Override
    public LoginPanel getLoginPanel() {
        return (LoginPanel) cardPanel.getComponent(1);
    }


    @Override
    public AdminPanel getAdminPanel() {
        return null;
    }

    @Override
    public RegisterPanel getRegisterPanel() {
        return (RegisterPanel) cardPanel.getComponent(2);
    }

    @Override
    public SearchPanel getSearchPanel() {
        return (SearchPanel) cardPanel.getComponent(3);
    }

    @Override
    public UserPanel getUserPanel() {
        return (UserPanel) cardPanel.getComponent(4);
    }

    @Override
    public RoomPanel getRoomPanel() {
        return (RoomPanel) cardPanel.getComponent(7);
    }

    @Override
    public RoomsPanel getRoomsPanel() {
        return (RoomsPanel) cardPanel.getComponent(8);
    }

    @Override
    public ReservationPanel getReservationPanel() {
        return (ReservationPanel) cardPanel.getComponent(9);
    }
    @Override
    public ReservationsPanel getReservationsPanel() {
        return (ReservationsPanel) cardPanel.getComponent(10);
    }

    @Override
    public ClerkPanel getClerkPanel(){
        return (ClerkPanel) cardPanel.getComponent(11);
    }

    @Override
    public StatusReportPanel getStatusReportPanel(){
        return (StatusReportPanel) cardPanel.getComponent(12);
    }

    public BillingReportPanel getBillingReportPanel() {
        return (BillingReportPanel) cardPanel.getComponent(13);
    }

}
