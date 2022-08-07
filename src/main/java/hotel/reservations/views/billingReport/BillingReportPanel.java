/**
 * @file BillingReport.java
 * @author Christian Haddad
 * @brief The custom page *JPanel* that is provided to allow the Clerk
 *        to complete their request billing report workflow.
 * @dependencies Frame.java
 */

package hotel.reservations.views.billingReport;

import hotel.reservations.models.reservation.Invoice;
import hotel.reservations.views.frame.Frame;
import hotel.reservations.views.styles.RoundedButton;
import hotel.reservations.views.styles.ThemedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class BillingReportPanel extends ThemedPanel {

    private final Frame frame;
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private RoundedButton btnBack;
    private List<Invoice> invoicesCache;

    /**
     * Attach the Frame dependency to the object and revalidate the panel's contents.
     * @param frame
     */
    public BillingReportPanel(Frame frame) {
        this.frame = frame;

        initDisplay();
    }

    private void initDisplay(){

        revalidate();
        repaint();

    }

    /**
     * Upon request of the panel, generate the content and layout of the panel
     * for a given list of invoice domain objects. *Builds a JScrollPane using an internal
     * JPanel and adds it to the BillingReportPanel JPanel.
     * @param invoices A list of Invoice.java domain objects.
     */
    public void fillLayout(List<Invoice> invoices) {
        // Do not try to display an empty list.
        if(invoices.isEmpty()) {
            this.invoicesCache = null;
            initDisplay();
            return;
        }

        this.invoicesCache = invoices;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(getWidth(),  getHeight()));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setAutoscrolls(true);

        scrollPanel = new ThemedPanel();
        scrollPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        for(Invoice invoice : invoices) {
            //insert invoices
            insertInvoices(invoice, gbc);
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.insets = new Insets(8, 0, 8, 0);
        }

        btnBack = new RoundedButton("Back");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getFrame().changeScreen("clerk");
            }
        });

        scrollPanel.add(btnBack);
        scrollPane.setViewportView(scrollPanel);

        scrollPane.revalidate();
        scrollPane.repaint();
        add(scrollPane);
        revalidate();
        repaint();

    }

    /**
     * Inserts an Invoice.java domain object to the Scroll Panel.
     * *Creates labels for the respective Invoice fields and a JButton
     *  for each Invoice domain object. All JLabels and JButtons are added
     *  to the Scroll Pane's internal JPanel *ScrollPanel.
     * @param inv Invoice.java domain object.
     * @param gbc GridBagConstraints current layout.
     */
    private void insertInvoices(Invoice inv, GridBagConstraints gbc) {
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + "Invoice ID: " + inv.getInvoiceId()+ "</p></html>"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + "Resort Fees: " + inv.getFees() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + "Subtotal: "+ inv.getSubtotal() + "</p></html>"), gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        RoundedButton tempBtn = new RoundedButton("View Invoice");
        tempBtn.setActionCommand(String.valueOf(inv.getInvoiceId()));
        tempBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String invId = e.getActionCommand();

                for(Invoice inv : invoicesCache){
                    if(String.valueOf(inv.getInvoiceId()).equals(invId)){
                        //getFrame().getBillingReportPanel().fillLayout((invoicesCache));
                        break;
                    }
                }

                getFrame().changeScreen("billing-report");
            }
        });
        scrollPanel.add(tempBtn, gbc);
    }

    public Frame getFrame() {
        return frame;
    }

}
