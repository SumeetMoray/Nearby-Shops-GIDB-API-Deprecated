package org.nearbyshops.gidb.Globals;

/**
 * Created by sumeet on 18/9/16.
 */
public class GlobalConstants {

    // Global Constants

    // Whenever new shop is created it is checked that the delivery range of new shop falls under the given range
    // for the given Service Type
    // all values are in Kilometers
    // consider an Example: A shop which desires to Register under

    // These constants are defined for the sake of improving the performance and efficiency. When the services are divided
    // into various different services. The load on each service will become less. This will help us
    // achieve better performance. Instead of one single service hosting all the shops accross the entire globe.
    // Here we have different service for each city.

    public static final Integer SHOP_DELIVERY_RANGE_MIN_CITY = 0;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_CITY = 300;

    public static final Integer SHOP_DELIVERY_RANGE_MIN_STATE = 300;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_STATE = 2500;

    public static final Integer SHOP_DELIVERY_RANGE_MIN_COUNTRY = 2500;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_COUNTRY = 7500;

    public static final Integer SHOP_DELIVERY_RANGE_MIN_GLOBAL = 7500;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_GLOBAL = 25000;


    // A service
    public static final Integer SERVICE_RANGE_MIN_CITY = 300;
    public static final Integer SERVICE_RANGE_MAX_CITY = 500;

    public static final Integer SERVICE_RANGE_MIN_STATE = 2500;
    public static final Integer SERVICE_RANGE_MAX_STATE = 3000;

    public static final Integer SERVICE_RANGE_MIN_COUNTRY = 7500;
    public static final Integer SERVICE_RANGE_MAX_COUNTRY = 9500;

    public static final Integer SERVICE_RANGE_MIN_GLOBAL = 20500;
    public static final Integer SERVICE_RANGE_MAX_GLOBAL = 40700;


    // Constants for Service Level
    public static final Integer SERVICE_LEVEL_CITY = 1;
    public static final Integer SERVICE_LEVEL_STATE = 2;
    public static final Integer SERVICE_LEVEL_COUNTRY = 3;
    public static final Integer SERVICE_LEVEL_GLOBAL = 4;

    // Constants for service type
    public static final Integer SERVICE_TYPE_NONPROFIT = 1;
    public static final Integer SERVICE_TYPE_GOVERNMENT = 2;
    public static final Integer SERVICE_TYPE_COMMERTIAL = 3;


    // Constants for the Roles in the Application

    public static final String ROLE_ADMIN = "ADMIN";

    public static final String ROLE_STAFF_DISABLED = "STAFF_DISABLED";
    public static final String ROLE_STAFF = "STAFF";

    public static final String ROLE_END_USER = "END_USER";



}
