package com.gof.hr2s.reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    public final int reservationID;
    public final int customerId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkout;

    public Reservation(int reservationId, int customerId, int roomId, LocalDate checkIn, LocalDate checkout) {
        this.reservationID = reservationId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkout = checkout;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckIn() {
        return this.checkIn;
    }

    /**
     * setter method for checkIn date ensuring checkIn date is before the checkOut
     * and the total stay is at least 1 day
     * @param checkIn the date of checkIn
     * @return true (success), false (fail)
     */
    public boolean setCheckIn(LocalDate checkIn) {
        // TODO: need to ensure this doesn't invalidate room availability
        if (checkIn.compareTo(this.checkIn) <= 0 || ChronoUnit.DAYS.between(checkIn, this.checkout) < 1) {
            return false;
        }

        this.checkIn = checkIn;
        return true;
    }

    public LocalDate getCheckout() {
        return this.checkout;
    }

    /**
     * setter method for checkOut date ensuring checkOut date is after the checkIn
     * and the total stay is at least 1 day
     * @param checkout checkout date
     * @return true (success), false (fail)
     */
    public boolean setCheckout(LocalDate checkout) {
        // TODO: need to ensure this doesn't violate room availability
        if (checkout.compareTo(this.checkIn) <= 0 || ChronoUnit.DAYS.between(this.checkIn, checkout) < 1) {
            return false;
        }

        this.checkout = checkout;
        return true;
    }

    public int getReservationId() {
        return this.reservationID;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * determines the number of days between check-in and checkout
     * @return long representing the number of days
     */
    public long lengthOfStay() {
        return ChronoUnit.DAYS.between(this.checkIn, this.checkout);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", customerId=" + customerId +
                ", roomId=" + roomId +
                ", checkIn=" + checkIn +
                ", checkout=" + checkout +
                '}';
    }
}
