package org.nearbyshops.gidb.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Item;

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

			statement.setString(1,item.getItemName());
			statement.setString(2,item.getItemDescription());
			statement.setString(3,item.getItemImageURL());

			statement.setInt(4,item.getItemCategoryID());
			statement.setString(5,item.getQuantityUnit());
			statement.setString(6,item.getItemDescriptionLong());


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

}
