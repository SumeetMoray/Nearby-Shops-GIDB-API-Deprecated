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
    public static final String CREATE_UPDATE_ITEM_CATEGORY = "CREATE_UPDATE_ITEM_CATEGORY";
    public static final String CREATE_UPDATE_ITEMS = "CREATE_UPDATE_ITEMS";

//    public static final String APPROVE_ITEM_CATEGORY_SUBMISSIONS = "APPROVE_ITEM_CATEGORY_SUBMISSIONS";
//    public static final String APPROVE_ITEM_SUBMISSIONS = "APPROVE_ITEM_SUBMISSIONS";

    public static final String APPROVE_SHOP_ADMIN_ACCOUNTS = "APPROVE_SHOP_ADMIN_ACCOUNTS";
    public static final String APPROVE_SHOPS = "APPROVE_SHOPS";
    public static final String APPROVE_END_USER_ACCOUNTS = "APPROVE_END_USER_ACCOUNTS";






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

            + " " + Staff.CREATE_UPDATE_ITEM_CATEGORY + " boolean ,"
            + " " + Staff.CREATE_UPDATE_ITEMS + " boolean ,"
            + " " + Staff.APPROVE_SHOP_ADMIN_ACCOUNTS + " boolean ,"
            + " " + Staff.APPROVE_SHOPS + " boolean ,"
            + " " + Staff.APPROVE_END_USER_ACCOUNTS + " boolean ,"
            + " FOREIGN KEY(" + Staff.STAFF_ID +") REFERENCES " + Usernames.TABLE_NAME + "(" + Usernames.USER_ID + ")"
            + ")";


    
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
    private boolean createUpdateItemCategory;
    private boolean createUpdateItems;
    private boolean approveShopAdminAccounts;
    private boolean approveShops;
    private boolean approveEndUserAccounts;



    // Getter and Setters


    public boolean isAccountPrivate() {
        return accountPrivate;
    }

    public void setAccountPrivate(boolean accountPrivate) {
        this.accountPrivate = accountPrivate;
    }

    public boolean isCreateUpdateItemCategory() {
        return createUpdateItemCategory;
    }

    public void setCreateUpdateItemCategory(boolean createUpdateItemCategory) {
        this.createUpdateItemCategory = createUpdateItemCategory;
    }

    public boolean isCreateUpdateItems() {
        return createUpdateItems;
    }

    public void setCreateUpdateItems(boolean createUpdateItems) {
        this.createUpdateItems = createUpdateItems;
    }

    public boolean isApproveShopAdminAccounts() {
        return approveShopAdminAccounts;
    }

    public void setApproveShopAdminAccounts(boolean approveShopAdminAccounts) {
        this.approveShopAdminAccounts = approveShopAdminAccounts;
    }

    public boolean isApproveShops() {
        return approveShops;
    }

    public void setApproveShops(boolean approveShops) {
        this.approveShops = approveShops;
    }

    public boolean isApproveEndUserAccounts() {
        return approveEndUserAccounts;
    }

    public void setApproveEndUserAccounts(boolean approveEndUserAccounts) {
        this.approveEndUserAccounts = approveEndUserAccounts;
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
