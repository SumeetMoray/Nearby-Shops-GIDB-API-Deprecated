package org.nearbyshops.gidb.ModelStaffTracking;

import org.nearbyshops.gidb.Model.Item;
import org.nearbyshops.gidb.ModelRoles.Staff;

import java.sql.Timestamp;

/**
 * Created by sumeet on 16/2/17.
 */
public class StaffItem {

    // Join table between staff and Item to keep a record of Items created by the Staff Member

    // Table Name
    public static final String TABLE_NAME = "STAFF_ITEM";

    // column Names
    public static final String STAFF_ID = "STAFF_ID";
    public static final String ITEM_ID = "ITEM_ID"; // UNIQUE - Because Two staff members cannot add the same Item.
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED"; // UNIQUE - Because Two staff members cannot add the same Item.



    // Create Table Statement
    public static final String createTableStaffItemPostgres = "CREATE TABLE IF NOT EXISTS "
            + StaffItem.TABLE_NAME + "("
            + " " + StaffItem.STAFF_ID + " INT NOT NULL,"
            + " " + StaffItem.ITEM_ID + " INT UNIQUE NOT NULL,"
            + " " + StaffItem.TIMESTAMP_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
            + " PRIMARY KEY (" + StaffItem.STAFF_ID + ", " + StaffItem.ITEM_ID + "),"
            + " FOREIGN KEY(" + StaffItem.ITEM_ID +") REFERENCES " + Item.TABLE_NAME + "(" + Item.ITEM_ID + ") ON DELETE CASCADE,"
            + " FOREIGN KEY(" + StaffItem.STAFF_ID +") REFERENCES " + Staff.TABLE_NAME + "(" + Staff.STAFF_ID + ") ON DELETE CASCADE"
            + ")";



    // Instance Variables

    private int staffID;
    private int itemID;
    private Timestamp timestampCreated;



    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }
}
