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

    public BillingReportPanel(Frame frame) {
        this.frame = frame;

        initDisplay();
    }

    private void initDisplay(){

        revalidate();
        repaint();

    }

    public void fillLayout(List<Invoice> invoices) {
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

    private void insertInvoices(Invoice inv, GridBagConstraints gbc) {

        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + inv.getIsPaid() + "</p></html>"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + inv.getInvoiceId()+ "</p></html>"), gbc);
        gbc.gridy++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + inv.getFees() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + inv.getTaxRate() + "</p></html>"), gbc);
        gbc.gridx++;
        gbc.insets = new Insets(0, 16, 0, 16);
        scrollPanel.add(new JLabel("<html><p style='color:white; font-size:16px; font-weight:bold'>" + inv.getSubtotal() + "</p></html>"), gbc);
        gbc.gridx = 0;
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
    }

    public Frame getFrame() {
        return frame;
    }

}
