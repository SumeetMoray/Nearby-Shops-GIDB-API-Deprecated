package org.nearbyshops.gidb.ModelRoles;

import java.sql.Timestamp;

/**
 * Created by sumeet on 29/5/16.
 */
public class Admin {

    // Table Name for Distributor
    public static final String TABLE_NAME = "ADMIN";

    // Column names for Distributor

    public static final String ADMIN_ID = "ADMIN_ID";

    public static final String ADMIN_NAME = "ADMIN_NAME";
//    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";

    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String DESIGNATION = "DESIGNATION";
    // privacy
    // private accounts will be hidden from public displays in the end user app
    public static final String ACCOUNT_PRIVATE = "ACCOUNT_PRIVATE";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";


    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableAdminPostgres =

            "CREATE TABLE IF NOT EXISTS " + Admin.TABLE_NAME + "("

            + " " + Admin.ADMIN_ID + " INT UNIQUE NOT NULL,"
//            + " " + Admin.USER_NAME + " text UNIQUE NOT NULL,"
            + " " + Admin.PASSWORD + " text NOT NULL,"
            + " " + Admin.ADMIN_NAME + " text,"

            + " " + Admin.ABOUT + " text,"
            + " " + Admin.PROFILE_IMAGE_URL + " text,"

            + " " + Admin.PHONE_NUMBER + " text,"
            + " " + Admin.DESIGNATION + " text,"

            + " " + Admin.ACCOUNT_PRIVATE + " boolean,"
            + " " + Admin.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"
            + " FOREIGN KEY(" + Admin.ADMIN_ID +") REFERENCES " + Usernames.TABLE_NAME + "(" + Usernames.USER_ID + ")"
            + ")";



    // Instance Variables
    private int adminID;
    private String username;
    private String password;
    private String administratorName;

    private String about;
    private String profileImageURL;

    private String phone;
    private String designation;

    private boolean accountPrivate;
    private Timestamp timestampCreated;



    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public boolean isAccountPrivate() {
        return accountPrivate;
    }
    public void setAccountPrivate(boolean accountPrivate) {
        this.accountPrivate = accountPrivate;
    }
    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }
    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }
    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }
    public String getProfileImageURL() {
        return profileImageURL;
    }
    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    // Getter and Setters
    public int getAdminID() {
        return adminID;
    }
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
    public String getAdministratorName() {
        return administratorName;
    }
    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName;
    }
}
