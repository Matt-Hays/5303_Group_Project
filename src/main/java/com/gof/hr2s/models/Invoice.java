package com.gof.hr2s.models;

import java.util.UUID;

public class Invoice {
    private final UUID invoiceId;
    private final UUID customerId;
    private double subtotal = 0.0;
    private double tax_rate = 0.0625;
    private double fees = 0.0;
    private boolean isPaid = false;
    private double total = subtotal + (subtotal*tax_rate) + fees;

    public Invoice(UUID customerID, double subtotal, double tax_rate, double fees, boolean isPaid, double total) {
        this.invoiceId = UUID.randomUUID;
        this.customerId = customerID;
        this.subtotal = subtotal;
        this.tax_rate = tax_rate;
        this.fees = fees;
        this.isPaid = isPaid;
        this.total = total;
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public UUID getCustomerId(){
        return customerId;
    }

    public void setLengthOfStay(Reservation lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public Reservation lengthOfStay() {
        return this.lengthOfStay;
    }

    public void setNightlyRate(Room nightly_rate) {
        this.nightly_rate = nightly_rate;
    }

    public Room getNightlyRate() {
        return this.nightly_rate;
    }

    public void setSubtotal(double nightly_rate, int lengthOfStay) {
        double subtotal = nightly_rate*lengthOfStay + fees;
        return subtotal * (1+tax_rate);
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setTaxRate(int tax_rate) {
        this.tax_rate = tax_rate;
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
                ", subtotal=" + smoking +
                ", tax_rate=" + numBeds +
                ", fees=" + bedType +
                ", isPaid=" + occupied +
                ", total=" + total +
                '}';
    }
}
