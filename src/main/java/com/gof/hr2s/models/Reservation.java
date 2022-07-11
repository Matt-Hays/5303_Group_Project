package com.gof.hr2s.models;

import com.gof.hr2s.database.Database;
import com.gof.hr2s.service.Response;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Reservation {
    public final UUID reservationID;
    public final UUID customerId;
    private UUID invoiceId = null;
    private int roomNumber;
    private LocalDate createdAt;
    private LocalDate arrival;
    private LocalDate departure;
    private ReservationStatus status = ReservationStatus.AWAITING;

    Database db = null;

    /**
     * Used when a reservation doesn't exist
     * @param customerId
     * @param room
     * @param createdAt
     * @param arrival
     * @param departure
     * @param status
     */
    public Reservation(UUID customerId, Room room, LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status) {
        // randomly generate a reservationID
        this.reservationID = UUID.randomUUID();
        this.customerId = customerId;
        this.roomNumber = room.getRoomId();
        this.createdAt = createdAt;
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;

        generateInvoice(room.getRoomRate(), lengthOfStay());
    }

    /**
     * Used when taking an existing reservation (already has a reservationID) from the DB and creating an object from it
     * @param reservationID
     * @param customerId
     * @param room_number
     * @param createdAt
     * @param arrival
     * @param departure
     * @param status
     */
    public Reservation(UUID reservationID, UUID customerId, UUID invoiceId, int room_number, LocalDate createdAt, LocalDate arrival, LocalDate departure, ReservationStatus status) {
        this.reservationID = reservationID;
        this.customerId = customerId;
        this.invoiceId = invoiceId;
        this.roomNumber = room_number;
        this.createdAt = createdAt;
        this.arrival = arrival;
        this.departure = departure;
        this.status = status;

        db = Database.Database();
    }

    public int getRoomNumber() {
        return this.roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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

    public UUID getReservationId() {
        return this.reservationID;
    }

    public UUID getCustomerId() {
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
                ", roomId=" + roomNumber +
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

    public void deleteReservation() {
        db.deleteReservation(this);
    }
    public void cancelReservation() {
        status = ReservationStatus.CANCELLED;
        db.updateReservation(this);
        // TODO: calculate 80% if need be....
    }

    Invoice generateInvoice(double roomRate, long stayLength) {
        Invoice invoice = new Invoice();
        invoice.setSubtotal(roomRate, stayLength);
        this.invoiceId = invoice.getInvoiceId();

        db.insertInvoice(invoice);
        db.updateReservation(this);

        return invoice;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public UUID getInvoiceId() {
        return invoiceId;
    }
}
