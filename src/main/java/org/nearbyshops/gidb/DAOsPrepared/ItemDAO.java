package org.nearbyshops.gidb.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Item;
import org.nearbyshops.gidb.ModelStaffTracking.StaffItem;

import java.sql.*;
import java.util.List;


public class ItemDAO {


	private HikariDataSource dataSource = Globals.getDataSource();


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
		
	
	public int saveItem(Item item)
	{
		
		
		Connection connection = null;
		PreparedStatement statement = null;
		int idOfInsertedRow = -1;

		String insertItemCategory = "INSERT INTO " 
				+ Item.TABLE_NAME
				+ "("
				+ Item.ITEM_NAME + ","
				+ Item.ITEM_DESC + ","
				+ Item.ITEM_IMAGE_URL + ","

				+ Item.ITEM_CATEGORY_ID + ","
				+ Item.QUANTITY_UNIT + ","
				+ Item.ITEM_DESCRIPTION_LONG

				+ ") VALUES(?,?,? ,?,?,?)";
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setString(++i,item.getItemName());
			statement.setString(++i,item.getItemDescription());
			statement.setString(++i,item.getItemImageURL());
			statement.setInt(++i,item.getItemCategoryID());
			statement.setString(++i,item.getQuantityUnit());
			statement.setString(++i,item.getItemDescriptionLong());


			idOfInsertedRow = statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			
			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		{
			
			try {
			
				if(statement!=null)
				{statement.close();}
			
			} 
			catch (SQLException e) {
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
		
		return idOfInsertedRow;
		
	}



	public int saveItemForStaff(Item item, int staffID, boolean getRowCount)
	{


		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementUpdateStaffItem = null;
		int idOfInsertedRow = 0;
		int rowCountItems = 0;
		int rowCountStaffItem = 0;

		String insertItemCategory = "INSERT INTO "
				+ Item.TABLE_NAME
				+ "("
				+ Item.ITEM_NAME + ","
				+ Item.ITEM_DESC + ","
				+ Item.ITEM_IMAGE_URL + ","

				+ Item.ITEM_CATEGORY_ID + ","
				+ Item.QUANTITY_UNIT + ","
				+ Item.ITEM_DESCRIPTION_LONG

				+ ") VALUES(?,?,? ,?,?,?)";


		String insertStaffItem = "INSERT INTO "
				+ StaffItem.TABLE_NAME
				+ "("
				+ StaffItem.ITEM_ID + ","
				+ StaffItem.STAFF_ID + ""
				+ ") VALUES(?,?)";


		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 0;
			statement.setString(++i,item.getItemName());
			statement.setString(++i,item.getItemDescription());
			statement.setString(++i,item.getItemImageURL());

			statement.setInt(++i,item.getItemCategoryID());
			statement.setString(++i,item.getQuantityUnit());
			statement.setString(++i,item.getItemDescriptionLong());


			rowCountItems = statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}



			statementUpdateStaffItem = connection.prepareStatement(insertStaffItem,PreparedStatement.RETURN_GENERATED_KEYS);
			int j = 0;
			statementUpdateStaffItem.setObject(++j,idOfInsertedRow);
			statementUpdateStaffItem.setObject(++j,staffID);

			rowCountStaffItem = statementUpdateStaffItem.executeUpdate();




			connection.commit();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

					idOfInsertedRow = -1;
					rowCountItems = 0;
					rowCountStaffItem = 0;

					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		}

		finally
		{

			try {

				if(statement!=null)
				{statement.close();}

			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(statementUpdateStaffItem!=null)
			{
				try {
					statementUpdateStaffItem.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			try {

				if(connection!=null)
				{connection.close();}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		if(getRowCount)
		{
			return rowCountItems;
		}
		else
		{
			return idOfInsertedRow;
		}

	}




	public int saveItemRowCount(Item item)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		int idOfInsertedRow = 0;

		String insertItemCategory = "INSERT INTO "
				+ Item.TABLE_NAME
				+ "("
				+ Item.ITEM_NAME + ","
				+ Item.ITEM_DESC + ","
				+ Item.ITEM_IMAGE_URL + ","

				+ Item.ITEM_CATEGORY_ID + ","
				+ Item.QUANTITY_UNIT + ","
				+ Item.ITEM_DESCRIPTION_LONG

				+ ") VALUES(?,?,? ,?,?,?)";

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setString(++i,item.getItemName());
			statement.setString(++i,item.getItemDescription());
			statement.setString(++i,item.getItemImageURL());

			statement.setInt(++i,item.getItemCategoryID());
			statement.setString(++i,item.getQuantityUnit());
			statement.setString(++i,item.getItemDescriptionLong());


			idOfInsertedRow = statement.executeUpdate();

//			ResultSet rs = statement.getGeneratedKeys();
//
//			if(rs.next())
//			{
//				idOfInsertedRow = rs.getInt(1);
//			}


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally
		{

			try {

				if(statement!=null)
				{statement.close();}

			}
			catch (SQLException e) {
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

		return idOfInsertedRow;
	}




	public int changeParent(Item item)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

//				+ " " + Item.ITEM_NAME + " = ?,"
//				+ " " + Item.ITEM_DESC + " = ?,"
//				+ " " + Item.ITEM_IMAGE_URL + " = ?,"

				+ " " + Item.ITEM_CATEGORY_ID + " = ?"
//				+ " " + Item.QUANTITY_UNIT + " = ?,"
//				+ " " + Item.ITEM_DESCRIPTION_LONG + " = ?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

//			statement.setString(1,item.getItemName());
//			statement.setString(2,item.getItemDescription());
//			statement.setString(3,item.getItemImageURL());

			if(item.getItemCategoryID()!=null && (item.getItemCategoryID()==-1||item.getItemCategoryID()==0))
			{
				item.setItemCategoryID(null);
			}

			statement.setObject(1,item.getItemCategoryID());
//			statement.setString(5,item.getQuantityUnit());
//			statement.setString(6,item.getItemDescriptionLong());
			statement.setInt(2,item.getItemID());

			rowCountUpdated = statement.executeUpdate();
			System.out.println("Total rows updated: " + rowCountUpdated);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally

		{

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

		return rowCountUpdated;
	}



	public int changeParentBulk(List<Item> itemList)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

//				+ " " + Item.ITEM_NAME + " = ?,"
//				+ " " + Item.ITEM_DESC + " = ?,"
//				+ " " + Item.ITEM_IMAGE_URL + " = ?,"

				+ " " + Item.ITEM_CATEGORY_ID + " = ?"
//				+ " " + Item.QUANTITY_UNIT + " = ?,"
//				+ " " + Item.ITEM_DESCRIPTION_LONG + " = ?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);


			for(Item item : itemList)
			{
//				statement.setString(1,item.getItemName());
//				statement.setString(2,item.getItemDescription());
//				statement.setString(3,item.getItemImageURL());
				if(item.getItemCategoryID()!=null && item.getItemCategoryID()==-1)
				{
					item.setItemCategoryID(null);
				}

				statement.setObject(1,item.getItemCategoryID());
//				statement.setString(5,item.getQuantityUnit());
//				statement.setString(6,item.getItemDescriptionLong());
				statement.setInt(2,item.getItemID());

				statement.addBatch();
			}


			int[] totalsArray = statement.executeBatch();

			for(int i : totalsArray)
			{
				rowCountUpdated = rowCountUpdated + i;
			}

			System.out.println("Total rows updated: UPDATE BULK " + rowCountUpdated);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally

		{

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

		return rowCountUpdated;
	}




	public int updateItem(Item item)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

				+ " " + Item.ITEM_NAME + " = ?,"
				+ " " + Item.ITEM_DESC + " = ?,"
				+ " " + Item.ITEM_IMAGE_URL + " = ?,"

				+ " " + Item.ITEM_CATEGORY_ID + " = ?,"
				+ " " + Item.QUANTITY_UNIT + " = ?,"
				+ " " + Item.ITEM_DESCRIPTION_LONG + " = ?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;
		
		int rowCountUpdated = 0;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			statement.setString(1,item.getItemName());
			statement.setString(2,item.getItemDescription());
			statement.setString(3,item.getItemImageURL());

			statement.setInt(4,item.getItemCategoryID());
			statement.setString(5,item.getQuantityUnit());
			statement.setString(6,item.getItemDescriptionLong());
			statement.setInt(7,item.getItemID());

			rowCountUpdated = statement.executeUpdate();
			System.out.println("Total rows updated: " + rowCountUpdated);	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		
		{
			
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
		
		return rowCountUpdated;
	}



	public int updateItemBulk(List<Item> itemList)
	{

		String updateStatement = "UPDATE " + Item.TABLE_NAME

				+ " SET "

				+ " " + Item.ITEM_NAME + " = ?,"
				+ " " + Item.ITEM_DESC + " = ?,"
				+ " " + Item.ITEM_IMAGE_URL + " = ?,"

				+ " " + Item.ITEM_CATEGORY_ID + " = ?,"
				+ " " + Item.QUANTITY_UNIT + " = ?,"
				+ " " + Item.ITEM_DESCRIPTION_LONG + " = ?"

				+ " WHERE " + Item.ITEM_ID + " = ?";


		Connection connection = null;
		PreparedStatement statement = null;

		int rowCountUpdated = 0;

		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);


			for(Item item : itemList)
			{
				statement.setString(1,item.getItemName());
				statement.setString(2,item.getItemDescription());
				statement.setString(3,item.getItemImageURL());

				statement.setInt(4,item.getItemCategoryID());
				statement.setString(5,item.getQuantityUnit());
				statement.setString(6,item.getItemDescriptionLong());
				statement.setInt(7,item.getItemID());

				statement.addBatch();
			}


			int[] totalsArray = statement.executeBatch();

			for(int i : totalsArray)
			{
				rowCountUpdated = rowCountUpdated + i;
			}

			System.out.println("Total rows updated: UPDATE BULK " + rowCountUpdated);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally

		{

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

		return rowCountUpdated;
	}




	public int deleteItem(int itemID)
	{

		String deleteStatement = "DELETE FROM " + Item.TABLE_NAME + " WHERE " + Item.ITEM_ID + " = ?";

		Connection connection= null;
		PreparedStatement statement = null;
		int rowCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setInt(1,itemID);

			rowCountDeleted = statement.executeUpdate();
			
			System.out.println("Rows Deleted: " + rowCountDeleted);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally
		
		{
			
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

		return rowCountDeleted;
	}



	public Item getItemImageURL(
			Integer itemID
	) {


		boolean isfirst = true;

		String query = "";

//		String queryNormal = "SELECT * FROM " + Item.TABLE_NAME;


		String queryJoin = "SELECT DISTINCT "
//				+ Item.TABLE_NAME + "." + Item.ITEM_CATEGORY_ID + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_ID + ","
				+ Item.TABLE_NAME + "." + Item.ITEM_IMAGE_URL + ""
//				+ Item.TABLE_NAME + "." + Item.ITEM_NAME + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_DESC + ","

//				+ Item.TABLE_NAME + "." + Item.QUANTITY_UNIT + ","
//				+ Item.TABLE_NAME + "." + Item.DATE_TIME_CREATED + ","
//				+ Item.TABLE_NAME + "." + Item.ITEM_DESCRIPTION_LONG + ""

				+ " FROM " + Item.TABLE_NAME
				+ " WHERE " + Item.ITEM_ID + " = " + itemID;





//
//		if(itemID != null)
//		{
//			queryJoin = queryJoin + " WHERE "
//					+ Item.TABLE_NAME
//					+ "."
//					+ Item.ITEM_ID + " = " + itemID;
//
//
//			isfirst = false;
//		}






		/*

		Applying filters Ends

		 */


//		boolean isJoinQuery = false;

		query = queryJoin;
//		isJoinQuery = true;

//		ArrayList<Item> itemList = new ArrayList<Item>();
		Item item = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

//			System.out.println(query);
			rs = statement.executeQuery(queryJoin);

			while(rs.next())
			{
				item = new Item();

				item.setItemID(itemID);
//				item.setItemName(rs.getString(Item.ITEM_NAME));
//				item.setItemDescription(rs.getString(Item.ITEM_DESC));

				item.setItemImageURL(rs.getString(Item.ITEM_IMAGE_URL));
//				item.setItemCategoryID(rs.getInt(Item.ITEM_CATEGORY_ID));

//				item.setItemDescriptionLong(rs.getString(Item.ITEM_DESCRIPTION_LONG));
//				item.setDateTimeCreated(rs.getTimestamp(Item.DATE_TIME_CREATED));
//				item.setQuantityUnit(rs.getString(Item.QUANTITY_UNIT));
			}



//			System.out.println("Item By CategoryID " + itemList.size());

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

		return item;
	}




}
