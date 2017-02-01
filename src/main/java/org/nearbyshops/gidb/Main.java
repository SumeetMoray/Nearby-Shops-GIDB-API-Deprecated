package org.nearbyshops.gidb;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Item;
import org.nearbyshops.gidb.Model.ItemCategory;
import org.nearbyshops.gidb.ModelRoles.Admin;
import org.nearbyshops.gidb.ModelRoles.Staff;
import org.nearbyshops.gidb.ModelRoles.Usernames;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://0.0.0.0:6000/api/";

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:5100/api/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.nearbyshops.gidb package
        final ResourceConfig rc = new ResourceConfig().packages("org.nearbyshops.gidb");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        createDB();
        createTables();


        final HttpServer server = startServer();


//        System.out.println(String.format("Jersey app started with WADL available at "
//                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
//        System.in.read();
//        server.stop();





    }





    private static void createDB()
    {

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
                    ,JDBCContract.CURRENT_USERNAME
                    ,JDBCContract.CURRENT_PASSWORD);

            statement = connection.createStatement();

            String createDB = "CREATE DATABASE \"ITEMS_DB\" WITH ENCODING='UTF8' OWNER=postgres CONNECTION LIMIT=-1";
            statement.executeUpdate(createDB);

        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }















    private static void createTables()
    {

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
                    ,JDBCContract.CURRENT_USERNAME
                    ,JDBCContract.CURRENT_PASSWORD);

            statement = connection.createStatement();


//            statement.executeUpdate(ItemCategory.createTableItemCategoryPostgres);
//            statement.executeUpdate(Item.createTableItemPostgres);

            // Create Table Roles
            statement.executeUpdate(Usernames.createTableUsernamesPostgres);
            statement.executeUpdate(Admin.createTableAdminPostgres);
            statement.executeUpdate(Staff.createTableStaffPostgres);

            // Create Table Item Cat and Items
            statement.executeUpdate(ItemCategory.createTableItemCategoryPostgres);
            statement.executeUpdate(Item.createTableItemPostgres);


            // create table service configuration

            System.out.println("Tables Created ... !");




            // developers Note: whenever adding a table please check that its dependencies are already created.


            // Insert the default administrator if it does not exit

            if(Globals.adminDAOPrepared.getAdmin(null,null).size()<=0)
            {
                Admin defaultAdmin = new Admin();

                defaultAdmin.setPassword("password");
                defaultAdmin.setUsername("username");
                defaultAdmin.setAdministratorName("default name");

                Globals.adminDAOPrepared.saveDefaultAdmin(defaultAdmin);
            }




            // Insert the root category whose DELIVERY_GUY_SELF_ID is 1

            String insertItemCategory = "";

            // The root ItemCategory has id 1. If the root category does not exist then insert it.
            if(Globals.itemCategoryDAO.checkRoot(1) == null)
            {

                insertItemCategory = "INSERT INTO "
                        + ItemCategory.TABLE_NAME
                        + "("
                        + ItemCategory.ITEM_CATEGORY_ID + ","
                        + ItemCategory.ITEM_CATEGORY_NAME + ","
                        + ItemCategory.PARENT_CATEGORY_ID + ","
                        + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
                        + ItemCategory.IMAGE_PATH + ","
                        + ItemCategory.IS_LEAF_NODE + ") VALUES("
                        + "" + "1"	+ ","
                        + "'" + "ROOT"	+ "',"
                        + "" + "NULL" + ","
                        + "'" + "This is the root Category. Do not modify it." + "',"
                        + "'" + " " + "',"
                        + "'" + "FALSE" + "'"
                        + ")";

                statement.executeUpdate(insertItemCategory);
            }



                // create directory images. Check if directory exist and create one if not exist

                final java.nio.file.Path BASE_DIR = Paths.get("./images");

                File theDir = new File(BASE_DIR.toString());

                // if the directory does not exist, create it
                if (!theDir.exists()) {

                    System.out.println("Creating directory: " + BASE_DIR.toString());

                    boolean result = false;

                    try{
                        theDir.mkdir();
                        result = true;
                    }
                    catch(Exception se){
                        //handle it
                    }

                    if(result) {
                        System.out.println("DIR created");
                    }
                }




        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


}

