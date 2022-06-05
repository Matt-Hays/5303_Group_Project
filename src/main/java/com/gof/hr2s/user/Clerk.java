package com.gof.hr2s.user;

import com.gof.hr2s.db.Database;
import com.gof.hr2s.room.Room;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.gof.hr2s.utils.HotelAuth.generatePasswordHash;

public class Clerk extends User{

    private String clerkFirstName;
    private String clerkLastName;
    public String jobTitle;



    public Clerk(int userId, Account accountType, String username, String password, String firstName, String lastName, String jobTitle) {
        super(userId, accountType, username, password, firstName, lastName);
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


    public void editRoom (Room room, int roomID, boolean smoking, int numBeds, Bed bedType) {
        room.setSmoking(smoking);
        room.setNumBeds(numBeds);
        room.setBedType(bedType);

        //db.updateRoom(roomID, smoking, numBeds, bedType);
    }

}
