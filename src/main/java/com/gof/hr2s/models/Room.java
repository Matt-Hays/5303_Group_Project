package com.gof.hr2s.models;

public class Room {
    public final int roomId;
    private boolean smoking = false;
    private int numBeds = 2;
    private Bed bedType = Bed.QUEEN;
    private boolean occupied = false;

    public Room(int roomId) {
        this.roomId = roomId;
    }

    public Room(int roomId, Bed bedType, int numBeds, boolean smoking, boolean occupied) {
        this(roomId);
        this.smoking = smoking;
        this.numBeds = numBeds;
        this.bedType = bedType;
        this.occupied = occupied;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public boolean getSmoking() {
        return this.smoking;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }

    public int getNumBeds() {
        return this.numBeds;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean getOccupied() {
        return this.occupied;
    }

    public void setBedType(Bed bedType) {
        this.bedType = bedType;
    }

    public Bed getBedType() {
        return this.bedType;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", smoking=" + smoking +
                ", numBeds=" + numBeds +
                ", bedType=" + bedType +
                ", occupied=" + occupied +
                '}';
    }
}
