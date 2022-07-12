package com.gof.hr2s.models;

import java.util.UUID;

public class Invoice {
    private final UUID invoiceId;
    private double subtotal = 0.0;
    private double tax_rate = 0.0625;
    private double fees = 0.0;
    private boolean isPaid = false;
    private double total = subtotal + (subtotal*tax_rate) + fees;

    public Invoice() {
        this.invoiceId = UUID.randomUUID();
    }

    public Invoice(UUID invoiceId, double tax_rate, double fees, double subtotal, boolean isPaid) {
        this.invoiceId = invoiceId;
        this.tax_rate = tax_rate;
        this.fees = fees;
        this.subtotal = subtotal;
        this.isPaid = isPaid;
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public double setSubtotal(double nightly_rate, long lengthOfStay) {
        double temp = nightly_rate*lengthOfStay + fees;
        subtotal =  temp * (1+tax_rate);
        return subtotal;
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setTaxRate(double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public double getTaxRate() {
        return this.tax_rate;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public double getFees() {
        return this.fees;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public boolean getIsPaid() {
        return this.isPaid;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Invoice {" +
                "invoiceId=" + invoiceId +
                '}';
    }
}
