package com.gof.hr2s.user;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.room.Bed;
import com.gof.hr2s.room.Room;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.gof.hr2s.utils.HotelAuth.generatePasswordHash;

public class Clerk extends User{

    private String clerkFirstName;
    private String clerkLastName;
    public String jobTitle;



    public Clerk(int userId, Account accountType, String username, String firstName, String lastName, String jobTitle) {
        super(userId, accountType, username, firstName, lastName);
        this.clerkFirstName = firstName;
        this.clerkLastName = lastName;
        this.jobTitle = "Hotel Clerk";
    }

    public String getClerkFirstName() {
        return clerkFirstName;
    }

    public void setClerkFirstName(String clerkFirstName) {
        this.clerkFirstName = clerkFirstName;
    }

    public String getClerkLastName() {
        return clerkLastName;
    }

    public void setClerkLastName(String clerkLastName) {
        this.clerkLastName = clerkLastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * updates attributes of a room
     * @param room the room object to be modified
     * @param bedType bed type
     * @param numBeds number of beds
     * @param smoking smoking / nonsmoking
     * @param occupied is the room occupied
     */
    public void editRoom (Room room, Bed bedType, int numBeds, boolean smoking, Boolean occupied) {

        room.setSmoking(smoking);
        room.setNumBeds(numBeds);
        room.setBedType(bedType);
        room.setOccupied(occupied);

        db.updateRoom(room.getRoomId(), bedType, numBeds, smoking, occupied);
    }
}
