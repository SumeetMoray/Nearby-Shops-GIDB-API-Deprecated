package org.nearbyshops.gidb;

//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.ssl.SSLContextConfigurator;
//import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.*;
import org.nearbyshops.gidb.ModelItemSpecification.ItemSpecificationItem;
import org.nearbyshops.gidb.ModelItemSpecification.ItemSpecificationName;
import org.nearbyshops.gidb.ModelItemSpecification.ItemSpecificationValue;
import org.nearbyshops.gidb.ModelRoles.Admin;
import org.nearbyshops.gidb.ModelRoles.Staff;
import org.nearbyshops.gidb.ModelRoles.Usernames;
import org.nearbyshops.gidb.ModelStaffTracking.StaffItem;
import org.nearbyshops.gidb.ModelStaffTracking.StaffItemCategory;

import java.io.*;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://0.0.0.0:6000/api/";

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "https://0.0.0.0:5100/api/";
    public static String BASE_URI_TWO = "http://0.0.0.0:";

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



        SslContextFactory sslContextFactory = new SslContextFactory(true);


        String keyStorePath = "";
        String keyStorePassword = "";
        String keyPassword = "";
        String port = "";


        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            keyStorePath = prop.getProperty("keystorepath");
            keyStorePassword = prop.getProperty("keysorepassword");
            keyPassword = prop.getProperty("keypassword");
            port = prop.getProperty("port");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        sslContextFactory.setKeyStorePath(keyStorePath);
        sslContextFactory.setKeyStorePassword(keyStorePassword);
//        sslContextFactory.set
        BASE_URI_TWO = BASE_URI_TWO + port;

        System.out.println("Keystore Path : " + keyStorePath + "\nKeystore password : " + keyStorePassword +
        "\nPath : " + port);

//        SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfigurator(sslContextFactory.getSslContext());

        SSLContextConfigurator sslContext = new SSLContextConfigurator();
        sslContext.setKeyStoreFile(keyStorePath);
        sslContext.setKeyStorePass(keyStorePassword);
        sslContext.setKeyPass(keyPassword);

//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI_TWO),rc,true,new SSLEngineConfigurator(sslContext).setClientMode(false).setNeedClientAuth(false));


            return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI_TWO),rc);

//        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI_TWO),sslContextFactory,rc);

    }



//    static void createPropertiesFile()
//    {
//        Properties prop = new Properties();
//        OutputStream output = null;
//
//        try {
//
//            output = new FileOutputStream("config.properties");
//
//            // set the properties value
//            prop.setProperty("database", "localhost");
//            prop.setProperty("dbuser", "mkyong");
//            prop.setProperty("dbpassword", "password");
//
//            // save properties to project root folder
//            prop.store(output, null);
//
//        } catch (IOException io) {
//            io.printStackTrace();
//        } finally {
//            if (output != null) {
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//    }



    static void readPropertiesFile()
    {

    }

//    private SSLEngineConfigurator createSSLConfig(boolean isServer)
//            throws Exception {
//        final SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
//        // override system properties
//        final File cacerts = getStoreFile("server truststore",
//                "truststore_server.jks");
//        if (cacerts != null) {
//            sslContextConfigurator.setTrustStoreFile(cacerts.getAbsolutePath());
//            sslContextConfigurator.setTrustStorePass(TRUSTSTORE_PASSWORD);
//        }
//
//        // override system properties
//        final File keystore = getStoreFile("server keystore", "keystore_server.jks");
//        if (keystore != null) {
//            sslContextConfigurator.setKeyStoreFile(keystore.getAbsolutePath());
//            sslContextConfigurator.setKeyStorePass(TRUSTSTORE_PASSWORD);
//        }
//
//        //
//        boolean clientMode = false;
//        // force client Authentication ...
////        boolean needClientAuth = settings.isNeedClientAuth();
////        boolean wantClientAuth = settings.isWantClientAuth();
//        SSLEngineConfigurator result = new SSLEngineConfigurator(
//                sslContextConfigurator.createSSLContext(), clientMode, needClientAuth,
//                wantClientAuth);
//        return result;
//    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

//        createPropertiesFile();
        createDB();
        createTables();
        upgradeTables();

//        final HttpServer server = startServer();

        startServer();



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







    private static void upgradeTables()
    {

        Connection connection = null;
        Statement statement = null;

        try {

            connection = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
                    ,JDBCContract.CURRENT_USERNAME
                    ,JDBCContract.CURRENT_PASSWORD);

            statement = connection.createStatement();

            statement.executeUpdate(Staff.upgradeTableSchema);
            statement.executeUpdate(Item.upgradeTableSchema);




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
//            statement.executeUpdate(Item.createTableItemImagesPostgres);

            // Create Table Roles
            statement.executeUpdate(Usernames.createTableUsernamesPostgres);
            statement.executeUpdate(Admin.createTableAdminPostgres);
            statement.executeUpdate(Staff.createTableStaffPostgres);

            // Create Table Item Cat and Items
            statement.executeUpdate(ItemCategory.createTableItemCategoryPostgres);
            statement.executeUpdate(Item.createTableItemPostgres);

            statement.executeUpdate(ItemImage.createTableItemImagesPostgres);

            statement.executeUpdate(StaffItem.createTableStaffItemPostgres);
            statement.executeUpdate(StaffItemCategory.createTableStaffItemPostgres);


            statement.executeUpdate(ItemSpecificationName.createTableItemSpecNamePostgres);
            statement.executeUpdate(ItemSpecificationValue.createTableItemSpecificationValuePostgres);
            statement.executeUpdate(ItemSpecificationItem.createTableItemSpecificationItemPostgres);





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

