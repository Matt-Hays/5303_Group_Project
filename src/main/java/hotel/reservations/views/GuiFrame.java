package hotel.reservations.views;

import hotel.reservations.controller.ReservationHandler;
import hotel.reservations.views.home.HomePanel;
import hotel.reservations.views.login.LoginPanel;
import hotel.reservations.views.register.RegisterPanel;
import hotel.reservations.views.search.SearchPanel;

import javax.swing.*;
import java.awt.*;

public class GuiFrame extends JFrame implements GuiHandler {
    private ReservationHandler reservationHandler;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public GuiFrame(ReservationHandler reservationHandler) {
        setReservationHandler(reservationHandler);

        setSize(850, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Hotel Reservations");

        // Create card layout
        cardPanel = new JPanel(new CardLayout());
        cardLayout = (CardLayout) cardPanel.getLayout();

        // Create cards
        // Add cards to card layout
        cardPanel.add(new HomePanel(this), "home");
        cardPanel.add(new LoginPanel(this), "login");
        cardPanel.add(new RegisterPanel(this), "register");
        cardPanel.add(new SearchPanel(this), "search");

        // Add card panel to app frame.
        add(cardPanel);

        // Display app frame.
        setVisible(true);
    }

    private void setReservationHandler(ReservationHandler reservationHandler) {
        this.reservationHandler = reservationHandler;
    }

    @Override
    public ReservationHandler getReservationHandler() {
        return reservationHandler;
    }

    @Override
    public void changeView(String viewName) {
        cardLayout.show(cardPanel, viewName);
    }

}
