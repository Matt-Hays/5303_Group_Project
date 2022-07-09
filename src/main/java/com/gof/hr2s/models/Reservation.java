package com.gof.hr2s.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    public final int reservationID;
    public final int customerId;
    private int room_number;
    private LocalDate createdAt;
    private LocalDate arrival;
    private LocalDate departure;
    private ReservationStatus status;

    public Reservation(int reservationId, int customerId, int room_number, LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status) {
        this.reservationID = reservationId;
        this.customerId = customerId;
        this.room_number = room_number;
        this.createdAt = createdAt;
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;
    }

    public int getRoom_number() {
        return this.room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public LocalDate getArrival() {
        return this.arrival;
    }

    /**
     * setter method for checkIn date ensuring checkIn date is before the checkOut
     * and the total stay is at least 1 day
     * @param checkIn the date of checkIn
     * @return true (success), false (fail)
     */
    public boolean setCheckIn(LocalDate checkIn) {
        // TODO: need to ensure this doesn't invalidate room availability
        if (checkIn.compareTo(this.arrival) <= 0 || ChronoUnit.DAYS.between(checkIn, this.departure) < 1) {
            return false;
        }

        this.arrival = checkIn;
        return true;
    }

    public LocalDate getDeparture() {
        return this.departure;
    }

    /**
     * setter method for checkOut date ensuring checkOut date is after the checkIn
     * and the total stay is at least 1 day
     * @param checkout checkout date
     * @return true (success), false (fail)
     */
    public boolean setCheckout(LocalDate checkout) {
        // TODO: need to ensure this doesn't violate room availability
        if (checkout.compareTo(this.arrival) <= 0 || ChronoUnit.DAYS.between(this.arrival, checkout) < 1) {
            return false;
        }

        this.departure = checkout;
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
        return ChronoUnit.DAYS.between(this.arrival, this.departure);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", customerId=" + customerId +
                ", roomId=" + room_number +
                ", checkIn=" + arrival +
                ", checkout=" + departure +
                '}';
    }

    public ReservationStatus isStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
