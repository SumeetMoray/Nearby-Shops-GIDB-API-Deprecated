package org.nearbyshops.gidb.ModelRoles;

import java.sql.Timestamp;

/**
 * Created by sumeet on 29/5/16.
 */
public class Staff {

    // Table Name for Distributor
    public static final String TABLE_NAME = "STAFF";

    // Column names for Distributor

    public static final String STAFF_ID = "STAFF_ID";
    public static final String STAFF_NAME = "STAFF_NAME";
//    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String ABOUT = "ABOUT";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String DESIGNATION = "DESIGNATION";

    // to be Implemented
    public static final String IS_ENABLED = "IS_ENABLED";

    // privacy
    // private accounts will be hidden from public displays in the end user app
    public static final String ACCOUNT_PRIVATE = "ACCOUNT_PRIVATE";

    public static final String GOVERNMENT_ID_NUMBER = "GOVERNMENT_ID_NUMBER";
    public static final String GOVERNMENT_ID_NAME = "GOVERNMENT_ID_NAME";
    public static final String TIMESTAMP_CREATED = "TIMESTAMP_CREATED";

    // permissions
//    public static final String CREATE_UPDATE_ITEM_CATEGORY = "CREATE_UPDATE_ITEM_CATEGORY";
//    public static final String CREATE_UPDATE_ITEMS = "CREATE_UPDATE_ITEMS";

    public static final String CREATE_ITEM_CATEGORY = "CREATE_ITEM_CATEGORY";
    public static final String UPDATE_ITEM_CATEGORY = "UPDATE_ITEM_CATEGORY";
    public static final String UPDATE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF = "UPDATE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF";
    public static final String DELETE_ITEM_CATEGORY = "DELETE_ITEM_CATEGORY";
    public static final String DELETE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF = "DELETE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF";


    public static final String CREATE_ITEM = "CREATE_ITEM";
    public static final String UPDATE_ITEM = "UPDATE_ITEM";
    public static final String UPDATE_ONLY_ITEM_ADDED_BY_SELF = "UPDATE_ONLY_ITEM_ADDED_BY_SELF";
    public static final String DELETE_ITEM = "DELETE_ITEM";
    public static final String DELETE_ONLY_ITEM_ADDED_BY_SELF = "DELETE_ONLY_ITEM_ADDED_BY_SELF";




//    public static final String APPROVE_ITEM_CATEGORY_SUBMISSIONS = "APPROVE_ITEM_CATEGORY_SUBMISSIONS";
//    public static final String APPROVE_ITEM_SUBMISSIONS = "APPROVE_ITEM_SUBMISSIONS";





    //SERIAL PRIMARY KEY

    // Create Table CurrentServiceConfiguration Provider
    public static final String createTableStaffPostgres =
            "CREATE TABLE IF NOT EXISTS " + Staff.TABLE_NAME + "("
            + " " + Staff.STAFF_ID + " INT UNIQUE NOT NULL,"

            + " " + Staff.STAFF_NAME + " text,"
//            + " " + Staff.USER_NAME + " text UNIQUE NOT NULL,"
            + " " + Staff.PASSWORD + " text NOT NULL,"

                  + Staff.ABOUT + " text,"
                  + Staff.PROFILE_IMAGE_URL + " text,"

            + " " + Staff.PHONE_NUMBER + " text,"
            + " " + Staff.DESIGNATION + " text,"
            + " " + Staff.IS_ENABLED + " boolean NOT NULL,"
            + " " + Staff.ACCOUNT_PRIVATE + " boolean,"


            + " " + Staff.GOVERNMENT_ID_NAME + " text,"
            + " " + Staff.GOVERNMENT_ID_NUMBER + " text,"
            + " " + Staff.TIMESTAMP_CREATED + " timestamp with time zone NOT NULL DEFAULT now(),"

//            + " " + Staff.CREATE_UPDATE_ITEM_CATEGORY + " boolean ,"
//            + " " + Staff.CREATE_UPDATE_ITEMS + " boolean ,"

            + " " + Staff.CREATE_ITEM_CATEGORY + " boolean ,"
            + " " + Staff.UPDATE_ITEM_CATEGORY + " boolean ,"
            + " " + Staff.UPDATE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF + " boolean ,"
            + " " + Staff.DELETE_ITEM_CATEGORY + " boolean ,"
            + " " + Staff.DELETE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF + " boolean ,"

            + " " + Staff.CREATE_ITEM + " boolean ,"
            + " " + Staff.UPDATE_ITEM + " boolean ,"
            + " " + Staff.UPDATE_ONLY_ITEM_ADDED_BY_SELF + " boolean ,"
            + " " + Staff.DELETE_ITEM + " boolean ,"
            + " " + Staff.DELETE_ONLY_ITEM_ADDED_BY_SELF + " boolean ,"

            + " FOREIGN KEY(" + Staff.STAFF_ID +") REFERENCES " + Usernames.TABLE_NAME + "(" + Usernames.USER_ID + ")"
            + ")";



    public static final String upgradeTableSchema =
            "ALTER TABLE IF EXISTS " + Staff.TABLE_NAME
                    + " ADD COLUMN " + Staff.CREATE_ITEM_CATEGORY + " boolean,"
                    + " ADD COLUMN " + Staff.UPDATE_ITEM_CATEGORY + " boolean,"
                    + " ADD COLUMN " + Staff.UPDATE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF + " boolean,"
                    + " ADD COLUMN " + Staff.DELETE_ITEM_CATEGORY + " boolean,"
                    + " ADD COLUMN " + Staff.DELETE_ONLY_ITEM_CATEGORY_ADDED_BY_SELF + " boolean,"

                    + " ADD COLUMN " + Staff.CREATE_ITEM + " boolean,"
                    + " ADD COLUMN " + Staff.UPDATE_ITEM + " boolean,"
                    + " ADD COLUMN " + Staff.UPDATE_ONLY_ITEM_ADDED_BY_SELF + " boolean,"
                    + " ADD COLUMN " + Staff.DELETE_ITEM + " boolean,"
                    + " ADD COLUMN " + Staff.DELETE_ONLY_ITEM_ADDED_BY_SELF + " boolean";



    // Instance Variables
    private int userID;

    private String staffName;
    private String username;
    private String password;

    private String about;
    private String profileImageURL;

    private String phone;
    private String designation;

    private Boolean isEnabled;
    private boolean accountPrivate;

    private String govtIDName;
    private String govtIDNumber;
    private Timestamp timestampCreated;

    // permission fields
//    private boolean createUpdateItemCategory;
//    private boolean createUpdateItems;

    // permission fields

    private boolean permitCreateItemCategories;
    private boolean permitUpdateItemCategories;
    private boolean permitUpdateOnlyItemCategoriesAddedBySelf;
    private boolean permitDeleteItemCategories;
    private boolean permitDeleteOnlyItemCategoriesAddedBySelf;


    private boolean permitCreateItems;
    private boolean permitUpdateItems;
    private boolean permitUpdateOnlyItemsAddedBySelf;
    private boolean permitDeleteItems;
    private boolean permitDeleteOnlyItemsAddedBySelf;






    // Getter and Setters


    public boolean isPermitCreateItemCategories() {
        return permitCreateItemCategories;
    }

    public void setPermitCreateItemCategories(boolean permitCreateItemCategories) {
        this.permitCreateItemCategories = permitCreateItemCategories;
    }

    public boolean isPermitUpdateItemCategories() {
        return permitUpdateItemCategories;
    }

    public void setPermitUpdateItemCategories(boolean permitUpdateItemCategories) {
        this.permitUpdateItemCategories = permitUpdateItemCategories;
    }

    public boolean isPermitUpdateOnlyItemCategoriesAddedBySelf() {
        return permitUpdateOnlyItemCategoriesAddedBySelf;
    }

    public void setPermitUpdateOnlyItemCategoriesAddedBySelf(boolean permitUpdateOnlyItemCategoriesAddedBySelf) {
        this.permitUpdateOnlyItemCategoriesAddedBySelf = permitUpdateOnlyItemCategoriesAddedBySelf;
    }

    public boolean isPermitDeleteItemCategories() {
        return permitDeleteItemCategories;
    }

    public void setPermitDeleteItemCategories(boolean permitDeleteItemCategories) {
        this.permitDeleteItemCategories = permitDeleteItemCategories;
    }

    public boolean isPermitDeleteOnlyItemCategoriesAddedBySelf() {
        return permitDeleteOnlyItemCategoriesAddedBySelf;
    }

    public void setPermitDeleteOnlyItemCategoriesAddedBySelf(boolean permitDeleteOnlyItemCategoriesAddedBySelf) {
        this.permitDeleteOnlyItemCategoriesAddedBySelf = permitDeleteOnlyItemCategoriesAddedBySelf;
    }

    public boolean isPermitCreateItems() {
        return permitCreateItems;
    }

    public void setPermitCreateItems(boolean permitCreateItems) {
        this.permitCreateItems = permitCreateItems;
    }

    public boolean isPermitUpdateItems() {
        return permitUpdateItems;
    }

    public void setPermitUpdateItems(boolean permitUpdateItems) {
        this.permitUpdateItems = permitUpdateItems;
    }

    public boolean isPermitUpdateOnlyItemsAddedBySelf() {
        return permitUpdateOnlyItemsAddedBySelf;
    }

    public void setPermitUpdateOnlyItemsAddedBySelf(boolean permitUpdateOnlyItemsAddedBySelf) {
        this.permitUpdateOnlyItemsAddedBySelf = permitUpdateOnlyItemsAddedBySelf;
    }

    public boolean isPermitDeleteItems() {
        return permitDeleteItems;
    }

    public void setPermitDeleteItems(boolean permitDeleteItems) {
        this.permitDeleteItems = permitDeleteItems;
    }

    public boolean isPermitDeleteOnlyItemsAddedBySelf() {
        return permitDeleteOnlyItemsAddedBySelf;
    }

    public void setPermitDeleteOnlyItemsAddedBySelf(boolean permitDeleteOnlyItemsAddedBySelf) {
        this.permitDeleteOnlyItemsAddedBySelf = permitDeleteOnlyItemsAddedBySelf;
    }

    public boolean isAccountPrivate() {
        return accountPrivate;
    }

    public void setAccountPrivate(boolean accountPrivate) {
        this.accountPrivate = accountPrivate;
    }



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

    public String getGovtIDName() {
        return govtIDName;
    }

    public void setGovtIDName(String govtIDName) {
        this.govtIDName = govtIDName;
    }

    public String getGovtIDNumber() {
        return govtIDNumber;
    }

    public void setGovtIDNumber(String govtIDNumber) {
        this.govtIDNumber = govtIDNumber;
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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
