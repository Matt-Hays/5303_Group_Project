package com.gof.hr2s.models;

public class Clerk extends User{

    private String clerkFirstName;
    private String clerkLastName;
    public String jobTitle;

    public Clerk(int userId, Account accountType, String username, String firstName, String lastName, String jobTitle) {
        super(userId, Account.CLERK, username, firstName, lastName);
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
}
