package org.nearbyshops.gidb.DAOPreparedStaffTracking;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Item;
import org.nearbyshops.gidb.ModelStaffTracking.StaffItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumeet on 16/2/17.
 */
public class StaffItemDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }


    public List<StaffItem> getStaffItem(
            Integer itemID,
            Integer staffID
    )
    {

        boolean isfirst = true;

        String queryJoin = "SELECT DISTINCT "
                + StaffItem.TABLE_NAME + "." + StaffItem.ITEM_ID + ","
                + StaffItem.TABLE_NAME + "." + StaffItem.STAFF_ID + ""
//                + StaffItem.TABLE_NAME + "." + StaffItem.TIMESTAMP_CREATED + ""
                + " FROM " + StaffItem.TABLE_NAME;





        if(itemID != null)
        {
            queryJoin = queryJoin + " WHERE "
                    + StaffItem.TABLE_NAME + "." + StaffItem.ITEM_ID + " = ? ";

            isfirst = false;
        }




        if(staffID!=null)
        {

            String queryNormalPart = Item.ITEM_CATEGORY_ID + " = ?";

            if(isfirst)
            {
                queryJoin = queryJoin + " WHERE " + queryNormalPart;

                isfirst = false;

            }else
            {
                queryJoin = queryJoin + " AND " + queryNormalPart;
            }
        }





        ArrayList<StaffItem> itemList = new ArrayList<StaffItem>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryJoin);

            int i = 0;

            if(itemID!=null)
            {
                statement.setObject(++i,itemID);
            }

            if(staffID!=null)
            {
                statement.setObject(++i,staffID);
            }



            System.out.println(queryJoin);
            rs = statement.executeQuery();



            while(rs.next())
            {
                StaffItem staffItem = new StaffItem();

                staffItem.setItemID(rs.getInt(StaffItem.ITEM_ID));
                staffItem.setStaffID(rs.getInt(StaffItem.STAFF_ID));

                itemList.add(staffItem);
            }



            System.out.println("Item By CategoryID " + itemList.size());

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return itemList;
    }


    public StaffItem checkStaffItem(
            int itemID,
            int staffID
    )
    {

        String queryJoin = "SELECT DISTINCT "
                + StaffItem.TABLE_NAME + "." + StaffItem.ITEM_ID + ","
                + StaffItem.TABLE_NAME + "." + StaffItem.STAFF_ID + ""
//                + StaffItem.TABLE_NAME + "." + StaffItem.TIMESTAMP_CREATED + ""
                + " FROM " + StaffItem.TABLE_NAME
                + " WHERE " + StaffItem.ITEM_ID + " = ? "
                + " AND " + StaffItem.STAFF_ID + " = ? ";


//        StaffItem itemList = new StaffItem();
        StaffItem staffItem = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(queryJoin);

            int i = 0;

            statement.setObject(++i,itemID);
            statement.setObject(++i,staffID);

            rs = statement.executeQuery();

                        System.out.println(queryJoin);


            while(rs.next())
            {
                staffItem = new StaffItem();


                staffItem.setItemID(rs.getInt(StaffItem.ITEM_ID));
                staffItem.setStaffID(rs.getInt(StaffItem.STAFF_ID));

                System.out.println("Check Staff Item Not NULL !");
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return staffItem;
    }


}
