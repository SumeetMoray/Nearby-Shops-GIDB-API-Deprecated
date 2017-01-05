package org.nearbyshops.gidb.ModelRoles;

/**
 * Created by sumeet on 29/5/16.
 */
public class Usernames {

    // Table Name for Distributor
    public static final String TABLE_NAME = "USERNAMES";

    // Column names for Distributor

    public static final String USER_ID = "USER_ID";
    public static final String USERNAME = "USERNAME";

    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableUsernamesPostgres =

            "CREATE TABLE IF NOT EXISTS " + Usernames.TABLE_NAME + "("
            + " " + Usernames.USER_ID + " SERIAL PRIMARY KEY,"
            + " " + Usernames.USERNAME + " text UNIQUE NOT NULL" + ")";


    // Instance Variables
//    private int userID;
//    private String username;

}
