package com.gof.hr2s.models;

public class Invoice {
    public final int invoiceId;
    private double subtotal = 0.0;
    private double tax_rate = 0.0625;
    private double fees = 0.0;
    private boolean isPaid = false;
    private double total = subtotal + (subtotal*tax_rate) + fees;

    public Invoice(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Invoice(int invoiceId, double subtotal, double tax_rate, double fees, boolean isPaid, double total) {
        this(invoiceId);
        this.subtotal = subtotal;
        this.tax_rate = tax_rate;
        this.fees = fees;
        this.isPaid = isPaid;
        this.total = total;
    }

    public int getInvoiceId() {
        return this.invoiceId;
    }

    public void setLengthOfStay(Reservation lengthOfStay) {
        this.lengthOfStay = lengthOfStay;
    }

    public Reservation lengthOfStay() {
        return this.lengthOfStay;
    }

    public void setRoomPrice(){
        this.room_price = room_price;
    }

    public room_price(){
        return this.room_price;
    }

    public void setSubtotal(double subtotal) {

        this.subtotal = subtotal;
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
