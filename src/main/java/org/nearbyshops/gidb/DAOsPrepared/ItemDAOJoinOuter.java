package org.nearbyshops.gidb.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Item;
import org.nearbyshops.gidb.ModelEndpoints.ItemEndPoint;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ItemDAOJoinOuter {


	private HikariDataSource dataSource = Globals.getDataSource();


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}



	//Integer shopID,

	public List<Item> getItems(
					Integer itemCategoryID,
					Boolean parentIsNull,
					String searchString,
					String sortBy,
					Integer limit, Integer offset
	) {


		boolean isfirst = true;

		String query = "";
		
//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;
		
		
		String queryJoin = "SELECT DISTINCT "
				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESC + ","

				+ Item.TABLE_NAME + "." + Item.QUANTITY_UNIT + ","
				+ Item.TABLE_NAME + "." + Item.DATE_TIME_CREATED + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + ""

				+ " FROM " + Item.TABLE_NAME;






		if(itemCategoryID != null)
		{
			queryJoin = queryJoin + " WHERE "
					+ Item.TABLE_NAME
					+ "."
					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;
			

			isfirst = false;
			
			//" WHERE ITEM_CATEGORY_ID = " + itemCategoryID
			
//			queryNormal = queryNormal + " WHERE "
//					+ Item.TABLE_NAME
//					+ "."
//					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;
			
		}




		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = Item.ITEM_CATEGORY_ID + " IS NULL";

			if(isfirst)
			{
//				queryNormal = queryNormal + " WHERE " + queryNormalPart;
				queryJoin = queryJoin + " WHERE " + queryNormalPart;

				isfirst = false;

			}else
			{
//				queryNormal = queryNormal + " AND " + queryNormalPart;
				queryJoin = queryJoin + " AND " + queryNormalPart;

			}
		}


		if(searchString !=null)
		{
			String queryPartSearch = " ( " +Item.TABLE_NAME + "." + Item.ITEM_DESC +" ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + " ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'" + ") ";


			if(isfirst)
			{
				queryJoin = queryJoin + " WHERE " + queryPartSearch;
				isfirst = false;
			}
			else
			{
				queryJoin = queryJoin + " AND " + queryPartSearch;
			}
		}



		/*if(shopID!=null)
		{
			String queryPart = "";

			queryPart = queryPart + ShopItem.TABLE_NAME
					+ "."
					+ ShopItem.SHOP_ID + " = " + shopID;

			if(isfirst)
			{
				queryJoin = queryJoin + " WHERE " + queryPart;
				isfirst = false;
			}
			else
			{
				queryJoin = queryJoin + " AND " + queryPart;
			}


		}*/



		// all the non-aggregate columns which are present in select must be present in group by also.
//		queryJoin = queryJoin
//
//				+ " group by "
//				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_ID ;


//				+ ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_DESC



		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

//				queryNormal = queryNormal + queryPartSortBy;
				queryJoin = queryJoin + queryPartSortBy;
			}
		}



		if(limit != null)
		{

			String queryPartLimitOffset = "";

			if(offset>0)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


//			queryNormal = queryNormal + queryPartLimitOffset;
			queryJoin = queryJoin + queryPartLimitOffset;
		}






		/*

		Applying filters Ends

		 */


//		boolean isJoinQuery = false;

		query = queryJoin;
//		isJoinQuery = true;

		ArrayList<Item> itemList = new ArrayList<Item>();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.createStatement();

			System.out.println(query);
			rs = statement.executeQuery(query);
			
			while(rs.next())
			{
				Item item = new Item();

				item.setItemID(rs.getInt(Item.ITEM_ID));
				item.setItemName(rs.getString(Item.ITEM_NAME));
				item.setItemDescription(rs.getString(Item.ITEM_DESC));

				item.setItemImageURL(rs.getString(Item.ITEM_IMAGE_URL));
				item.setItemCategoryID(rs.getInt(Item.ITEM_CATEGORY_ID));

				item.setItemDescriptionLong(rs.getString(Item.ITEM_DESCRIPTION_LONG));
				item.setDateTimeCreated(rs.getTimestamp(Item.DATE_TIME_CREATED));
				item.setQuantityUnit(rs.getString(Item.QUANTITY_UNIT));

				itemList.add(item);
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



	public ItemEndPoint getEndPointMetadata(
			Integer itemCategoryID,
			Boolean parentIsNull,String searchString)
	{


		boolean isfirst = true;

		String query = "";


		String queryJoin = "SELECT "

				+ "count( DISTINCT " + Item.TABLE_NAME + "." + Item.ITEM_ID + ") as item_count" + ""
				+ " FROM " +  Item.TABLE_NAME;


		if(itemCategoryID != null)
		{
			queryJoin = queryJoin + " WHERE "
					+ Item.TABLE_NAME
					+ "."
					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;

			isfirst = false;


			//" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

//			queryNormal = queryNormal + " WHERE "
//					+ Item.TABLE_NAME
//					+ "."
//					+ Item.ITEM_CATEGORY_ID + " = " + itemCategoryID;

		}


		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = Item.ITEM_CATEGORY_ID + " IS NULL";

			if(isfirst)
			{
//				queryNormal = queryNormal + " WHERE " + queryNormalPart;
				queryJoin = queryJoin + " WHERE " + queryNormalPart;
				isfirst = false;

			}else
			{
//				queryNormal = queryNormal + " AND " + queryNormalPart;
				queryJoin = queryJoin + " AND " + queryNormalPart;

			}
		}


		if(searchString !=null)
		{
			String queryPartSearch = " ( " +Item.TABLE_NAME + "." + Item.ITEM_DESC +" ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + " ilike '%" + searchString + "%'"
					+ " or " + Item.TABLE_NAME + "." + Item.ITEM_NAME + " ilike '%" + searchString + "%'" + ") ";


			if(isfirst)
			{
				queryJoin = queryJoin + " WHERE " + queryPartSearch;
				isfirst = false;
			}
			else
			{
				queryJoin = queryJoin + " AND " + queryPartSearch;
			}
		}




		// Applying filters

			query = queryJoin;


		ItemEndPoint endPoint = new ItemEndPoint();


		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while(rs.next())
			{
				endPoint.setItemCount(rs.getInt("item_count"));
			}

			System.out.println("Item Count : " + endPoint.getItemCount());

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

				if(stmt!=null)
				{stmt.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {

				if(conn!=null)
				{conn.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return endPoint;
	}
	
			

}
