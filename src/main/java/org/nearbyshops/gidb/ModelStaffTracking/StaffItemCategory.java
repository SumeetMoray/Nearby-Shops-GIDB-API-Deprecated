package org.nearbyshops.gidb.ModelStaffTracking;

import org.nearbyshops.gidb.Model.ItemCategory;
import org.nearbyshops.gidb.ModelRoles.Staff;

import java.sql.Timestamp;

/**
 * Created by sumeet on 16/2/17.
 */
public class StaffItemCategory {

    // Join table between staff and Item to keep a record of Items created by the Staff Member

    // Table Name
    public static final String TABLE_NAME = "STAFF_ITEM_CATEGORY";

    // column Names
    public static final String STAFF_ID = "STAFF_ID";
    public static final String ITEM_CATEGORY_ID = "ITEM_CATEGORY_ID"; // UNIQUE - Because Two staff members cannot add the same Item.
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED"; // UNIQUE - Because Two staff members cannot add the same Item.



    // Create Table Statement
    public static final String createTableStaffItemPostgres = "CREATE TABLE IF NOT EXISTS "
            + StaffItemCategory.TABLE_NAME + "("
            + " " + StaffItemCategory.STAFF_ID + " INT NOT NULL,"
            + " " + StaffItemCategory.ITEM_CATEGORY_ID + " INT UNIQUE NOT NULL,"
            + " " + StaffItemCategory.TIMESTAMP_CREATED + "  timestamp with time zone NOT NULL DEFAULT now(),"
            + " PRIMARY KEY (" + StaffItemCategory.STAFF_ID + ", " + StaffItemCategory.ITEM_CATEGORY_ID + "),"
            + " FOREIGN KEY(" + StaffItemCategory.ITEM_CATEGORY_ID +") REFERENCES " + ItemCategory.TABLE_NAME + "(" + ItemCategory.ITEM_CATEGORY_ID + ") ON DELETE CASCADE,"
            + " FOREIGN KEY(" + StaffItemCategory.STAFF_ID +") REFERENCES " + Staff.TABLE_NAME + "(" + Staff.STAFF_ID + ") ON DELETE CASCADE"
            + ")";



    // Instance Variables

    private int staffID;
    private int itemCategoryID;
    private Timestamp timestampCreated;



    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getItemCategoryID() {
        return itemCategoryID;
    }

    public void setItemCategoryID(int itemCategoryID) {
        this.itemCategoryID = itemCategoryID;
    }

    public Timestamp getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Timestamp timestampCreated) {
        this.timestampCreated = timestampCreated;
    }
}
