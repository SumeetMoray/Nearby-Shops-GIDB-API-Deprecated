package org.nearbyshops.gidb.DAOsPrepared;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.ItemCategory;
import org.nearbyshops.gidb.ModelStaffTracking.StaffItem;
import org.nearbyshops.gidb.ModelStaffTracking.StaffItemCategory;
import org.nearbyshops.gidb.ModelEndpoints.ItemCategoryEndPoint;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ItemCategoryDAO {

	private HikariDataSource dataSource = Globals.getDataSource();


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();	
	}
	
	
	public int saveItemCategory(ItemCategory itemCategory)
	{
		
		int rowCount = 0;	
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		String insertItemCategory = "";
//		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());


		
			insertItemCategory = "INSERT INTO "
					+ ItemCategory.TABLE_NAME				
					+ "("  
					+ ItemCategory.ITEM_CATEGORY_NAME + ","
					+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
					+ ItemCategory.PARENT_CATEGORY_ID + ","

					+ ItemCategory.IMAGE_PATH + ","
					+ ItemCategory.CATEGORY_ORDER + ","
					+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
					+ ItemCategory.IS_ABSTRACT + ","

					+ ItemCategory.IS_LEAF_NODE
					+ ") VALUES(?,?,? ,?,?,?,? ,?)";

		int idOfInsertedRow = 0;
		
		try {
			
			conn = dataSource.getConnection();
			statement = conn.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setString(++i,itemCategory.getCategoryName());
			statement.setString(++i,itemCategory.getCategoryDescription());
			statement.setInt(++i,itemCategory.getParentCategoryID());

			statement.setString(++i,itemCategory.getImagePath());
			statement.setObject(++i,itemCategory.getCategoryOrder());
			statement.setString(++i,itemCategory.getDescriptionShort());
			statement.setBoolean(++i,itemCategory.getisAbstractNode());

			statement.setBoolean(++i,itemCategory.getIsLeafNode());


			rowCount = statement.executeUpdate();
			
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
		
		return idOfInsertedRow;
	}






	public int saveItemCatForStaff(ItemCategory itemCategory, int staffID, boolean getRowCount)
	{

		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statementUpdateStaffItemCat = null;
		int idOfInsertedRow = 0;
		int rowCountItemCat = 0;



		String insertItemCategory = "";
		insertItemCategory = "INSERT INTO "
				+ ItemCategory.TABLE_NAME
				+ "("
				+ ItemCategory.ITEM_CATEGORY_NAME + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","

				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.CATEGORY_ORDER + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE
				+ ") VALUES(?,?,? ,?,?,?,? ,?)";



		String insertStaffItem = "INSERT INTO "
				+ StaffItemCategory.TABLE_NAME
				+ "("
				+ StaffItemCategory.ITEM_CATEGORY_ID + ","
				+ StaffItem.STAFF_ID + ""
				+ ") VALUES(?,?)";




		try {

			connection = dataSource.getConnection();
			connection.setAutoCommit(false);


			statement = connection.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);
			int i = 0;
			statement.setString(++i,itemCategory.getCategoryName());
			statement.setString(++i,itemCategory.getCategoryDescription());
			statement.setInt(++i,itemCategory.getParentCategoryID());

			statement.setString(++i,itemCategory.getImagePath());
			statement.setObject(++i,itemCategory.getCategoryOrder());
			statement.setString(++i,itemCategory.getDescriptionShort());
			statement.setBoolean(++i,itemCategory.getisAbstractNode());
			statement.setBoolean(++i,itemCategory.getIsLeafNode());


			rowCountItemCat = statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();

			if(rs.next())
			{
				idOfInsertedRow = rs.getInt(1);
			}



			statementUpdateStaffItemCat = connection.prepareStatement(insertStaffItem,PreparedStatement.RETURN_GENERATED_KEYS);
			int j = 0;
			statementUpdateStaffItemCat.setObject(++j,idOfInsertedRow);
			statementUpdateStaffItemCat.setObject(++j,staffID);

			rowCountItemCat = statementUpdateStaffItemCat.executeUpdate();



			connection.commit();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			if (connection != null) {
				try {

					idOfInsertedRow = -1;
					rowCountItemCat = 0;

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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			if(statementUpdateStaffItemCat!=null)
			{
				try {
					statementUpdateStaffItemCat.close();
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
			return rowCountItemCat;
		}
		else
		{
			return idOfInsertedRow;
		}
	}







	public int saveItemCatRowCount(ItemCategory itemCategory)
	{

		int rowCount = 0;
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		String insertItemCategory = "";
//		System.out.println("isLeaf : " + itemCategory.getIsLeafNode());



		insertItemCategory = "INSERT INTO "
				+ ItemCategory.TABLE_NAME
				+ "("
				+ ItemCategory.ITEM_CATEGORY_NAME + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","

				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.CATEGORY_ORDER + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE
				+ ") VALUES(?,?,? ,?,?,?,? ,?)";

		int idOfInsertedRow = 0;

		try {

			conn = dataSource.getConnection();
			statement = conn.prepareStatement(insertItemCategory,PreparedStatement.RETURN_GENERATED_KEYS);

			int i = 0;
			statement.setString(++i,itemCategory.getCategoryName());
			statement.setString(++i,itemCategory.getCategoryDescription());
			statement.setInt(++i,itemCategory.getParentCategoryID());

			statement.setString(++i,itemCategory.getImagePath());
			statement.setObject(++i,itemCategory.getCategoryOrder());
			statement.setString(++i,itemCategory.getDescriptionShort());
			statement.setBoolean(++i,itemCategory.getisAbstractNode());

			statement.setBoolean(++i,itemCategory.getIsLeafNode());


			rowCount = statement.executeUpdate();

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

		return rowCount;
	}





	public int changeParent(ItemCategory itemCategory)
	{
		int rowCount = 0;


		if(itemCategory.getParentCategoryID()!=null)
		{
			if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
			{
				// an Item category cannot have itself as its own parent so abort this operation and return
				return 0;
			}

			// a hack for android app. The android parcelable does not support Non primitives.
			// So cant have null for the ID.
			// The value of -1 represents the NULL when the request coming from an android app.
			// So when you see a -1 for parent set it to null which really means a detached item category,
			// an item category not having any parent

			if(itemCategory.getParentCategoryID()==-1)
			{
				itemCategory.setParentCategoryID(null);
			}
		}


//		if(itemCategory.getParentCategoryID()!=null)
//		{
//
//		}

		String updateStatement = "";

		updateStatement = "UPDATE "

				+ ItemCategory.TABLE_NAME
				+ " SET "
//				+ " " + ItemCategory.ITEM_CATEGORY_NAME + " = ?,"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " = ?,"
//				+ " " + ItemCategory.IMAGE_PATH + " = ?,"

				+ " " + ItemCategory.PARENT_CATEGORY_ID + " = ?"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " = ?,"
//				+ " " + ItemCategory.IS_ABSTRACT + " = ?,"

//				+ " " + ItemCategory.IS_LEAF_NODE + " = ?"

				+ " WHERE "
				+  ItemCategory.ITEM_CATEGORY_ID + "= ?";


		Connection connection = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

//			statement.setString(1,itemCategory.getCategoryName());
//			statement.setString(2,itemCategory.getCategoryDescription());
//			statement.setString(3,itemCategory.getImagePath());

			statement.setObject(1,itemCategory.getParentCategoryID());
//			statement.setString(5,itemCategory.getDescriptionShort());
//			statement.setBoolean(6,itemCategory.getisAbstractNode());

//			statement.setBoolean(7,itemCategory.getIsLeafNode());
			statement.setInt(2,itemCategory.getItemCategoryID());

			rowCount = statement.executeUpdate();


			System.out.println("Total rows updated: " + rowCount);

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

		return rowCount;
	}






	public int changeParentBulk(List<ItemCategory> itemCategoryList)
	{
		int rowCount = 0;

		String updateStatement = "";

		updateStatement = "UPDATE "

				+ ItemCategory.TABLE_NAME
				+ " SET "
//				+ " " + ItemCategory.ITEM_CATEGORY_NAME + " = ?,"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " = ?,"
//				+ " " + ItemCategory.IMAGE_PATH + " = ?,"

				+ " " + ItemCategory.PARENT_CATEGORY_ID + " = ?"
//				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " = ?,"
//				+ " " + ItemCategory.IS_ABSTRACT + " = ?,"

//				+ " " + ItemCategory.IS_LEAF_NODE + " = ?"

				+ " WHERE "
				+  ItemCategory.ITEM_CATEGORY_ID + "= ?";


		Connection connection = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		try {

			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

//			statement.setString(1,itemCategory.getCategoryName());
//			statement.setString(2,itemCategory.getCategoryDescription());
//			statement.setString(3,itemCategory.getImagePath());


//			statement.setString(5,itemCategory.getDescriptionShort());
//			statement.setBoolean(6,itemCategory.getisAbstractNode());

//			statement.setBoolean(7,itemCategory.getIsLeafNode());

			for(ItemCategory itemCategory: itemCategoryList)
			{

				if(itemCategory.getParentCategoryID()!=null)
				{
					if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
					{
						// an Item category cannot have itself as
						// its own parent so abort this operation and return
						continue;
					}

					if(itemCategory.getParentCategoryID()==-1)
					{
						itemCategory.setParentCategoryID(null);
					}

				}


				statement.setObject(1,itemCategory.getParentCategoryID());
				statement.setInt(2,itemCategory.getItemCategoryID());
				statement.addBatch();
			}


			int[] rowSumArray = statement.executeBatch();

			for(int rows : rowSumArray)
			{
				rowCount = rowCount + rows;
			}


			System.out.println("Total rows updated: CHANGE PARENT BULK " + rowCount);

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

		return rowCount;
	}





	public int updateItemCategory(ItemCategory itemCategory)
	{
		int rowCount = 0;


		if(itemCategory.getParentCategoryID()!=null)
		{
			if(itemCategory.getParentCategoryID() == itemCategory.getItemCategoryID())
			{
				// an Item category cannot have itself as its own parent so abort this operation and return
				return 0;
			}


			// a hack for android app. The android parcelable does not support Non primitives.
			// So cant have null for the ID.
			// The value of -1 represents the NULL when the request coming from an android app.
			// So when you see a -1 for parent set it to null which really means a detached item category,
			// an item category not having any parent

			if(itemCategory.getParentCategoryID()==-1)
			{
				itemCategory.setParentCategoryID(null);
			}
		}


//		if(itemCategory.getParentCategoryID()!=null)
//		{
//		}
		
		String updateStatement = "";

		updateStatement = "UPDATE "

				+ ItemCategory.TABLE_NAME
				+ " SET "
				+ " " + ItemCategory.ITEM_CATEGORY_NAME + " = ?,"
				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " = ?,"
				+ " " + ItemCategory.IMAGE_PATH + " = ?,"
				+ " " + ItemCategory.CATEGORY_ORDER + " = ?,"

				+ " " + ItemCategory.PARENT_CATEGORY_ID + " = ?,"
				+ " " + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + " = ?,"
				+ " " + ItemCategory.IS_ABSTRACT + " = ?,"

				+ " " + ItemCategory.IS_LEAF_NODE + " = ?"

				+ " WHERE "
				+  ItemCategory.ITEM_CATEGORY_ID + "= ?";


		Connection connection = null;
//		Statement stmt = null;
		PreparedStatement statement = null;


		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(updateStatement);

			int i = 0;
			statement.setString(++i,itemCategory.getCategoryName());
			statement.setString(++i,itemCategory.getCategoryDescription());
			statement.setString(++i,itemCategory.getImagePath());
			statement.setObject(++i,itemCategory.getCategoryOrder());

			statement.setObject(++i,itemCategory.getParentCategoryID());
			statement.setString(++i,itemCategory.getDescriptionShort());
			statement.setBoolean(++i,itemCategory.getisAbstractNode());

			statement.setBoolean(++i,itemCategory.getIsLeafNode());
			statement.setInt(++i,itemCategory.getItemCategoryID());

			rowCount = statement.executeUpdate();


			System.out.println("Total rows updated: " + rowCount);
			
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
		
		return rowCount;
	}


	

	public int deleteItemCategory(int itemCategoryID)
	{
		
		String deleteStatement = "DELETE FROM " + ItemCategory.TABLE_NAME + " WHERE "
								+ ItemCategory.ITEM_CATEGORY_ID + " = ?";

		
		Connection connection= null;
		PreparedStatement statement = null;

		int rowCountDeleted = 0;
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(deleteStatement);
			statement.setInt(1,itemCategoryID);

			rowCountDeleted = statement.executeUpdate();
			System.out.println("row Count Deleted: " + rowCountDeleted);	
			
			connection.close();
			
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


	public ItemCategoryEndPoint getItemCategoriesSimplePrepared(
			Integer parentID,
			Boolean parentIsNull,
			String searchString,
			String sortBy,
			Integer limit, Integer offset)
	{

		boolean queryNormalFirst = true;


		String queryNormal = "SELECT "

				+ "count(" + ItemCategory.ITEM_CATEGORY_ID + ") over() AS full_count " + ","
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","
				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.CATEGORY_ORDER + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE + ","
				+ ItemCategory.ITEM_CATEGORY_NAME +

				" FROM " + ItemCategory.TABLE_NAME;






		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID + " = ?";

			queryNormalFirst = false;
		}



		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;
				queryNormalFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " +ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike ? "
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike ?"
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME + " ilike ? ) ";


//			String queryPartSearch = " ( " +ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike '%" + searchString + "%'"
//					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike '%" + searchString + "%'"
//					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME + " ilike '%" + searchString + "%'" + ") ";


			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryPartSearch;
				queryNormalFirst = false;
			}
			else
			{
				queryNormal = queryNormal + " AND " + queryPartSearch;
			}
		}





		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			queryPartLimitOffset = " LIMIT ? OFFSET ? ";


//			if(offset!=null)
//			{
//				queryPartLimitOffset = " LIMIT ?  OFFSET ? ";
//
//			}else
//			{
//				queryPartLimitOffset = " LIMIT ? OFFSET ? ";
//			}


			queryNormal = queryNormal + queryPartLimitOffset;
		}




		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();

		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();


		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;

		boolean rootRemoved = false;

		try {

			connection = dataSource.getConnection();

			statement = connection.prepareStatement(queryNormal);





			// set placeholders
			int i = 0;
			if(parentID!=null)
			{
				statement.setObject(++i,parentID);
			}

			if(searchString!=null)
			{
				statement.setString(++i,"%" + searchString + "%");
				statement.setString(++i,"%" + searchString + "%");
				statement.setString(++i,"%" + searchString + "%");
			}


//			if(sortBy!=null)
//			{
//				statement.setString(++i,sortBy);
//			}

			if(limit!=null)
			{
				if(offset==null)
				{
					offset = 0;
				}

				statement.setObject(++i,limit);
				statement.setObject(++i,offset);
			}



			rs = statement.executeQuery();

			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();

				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));

				if(itemCategory.getItemCategoryID()==1)
				{
					rootRemoved=true;
					continue;
				}

				itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategory.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
				itemCategory.setCategoryOrder(rs.getInt(ItemCategory.CATEGORY_ORDER));

				itemCategory.setCategoryName(rs.getString(ItemCategory.ITEM_CATEGORY_NAME));

				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategory.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setCategoryDescription(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION));

				endPoint.setItemCount(rs.getInt("full_count"));


				itemCategoryList.add(itemCategory);
			}



			// cast to the pg extension interface
			org.postgresql.PGStatement pgstmt = statement.unwrap(org.postgresql.PGStatement.class);
			boolean usingServerPrepare = pgstmt.isUseServerPrepare();
			System.out.println("Used server side: " + usingServerPrepare + " | Prepare threshold : " + pgstmt.getPrepareThreshold());


			if(rootRemoved && endPoint.getItemCount()!=0)
			{
				endPoint.setItemCount(endPoint.getItemCount()-1);
			}

			System.out.println("Total itemCategories queried " + itemCategoryList.size());


			endPoint.setResults(itemCategoryList);

		}


		catch (SQLException e) {
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


		return endPoint;
	}





	public ItemCategoryEndPoint getItemCategoriesSimple(
			Integer parentID,
			Boolean parentIsNull,
			String searchString,
			String sortBy,
			Integer limit, Integer offset)
	{

		boolean queryNormalFirst = true;


		String queryNormal = "SELECT "

				+ "count(" + ItemCategory.ITEM_CATEGORY_ID + ") over() AS full_count " + ","
				+ ItemCategory.ITEM_CATEGORY_ID + ","
				+ ItemCategory.PARENT_CATEGORY_ID + ","
				+ ItemCategory.IMAGE_PATH + ","
				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","

				+ ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT + ","
				+ ItemCategory.IS_ABSTRACT + ","

				+ ItemCategory.IS_LEAF_NODE + ","
				+ ItemCategory.ITEM_CATEGORY_NAME +

				" FROM " + ItemCategory.TABLE_NAME;






		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID + " = " + parentID ;

			queryNormalFirst = false;
		}



		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;
				queryNormalFirst = false;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}



		if(searchString !=null)
		{
			String queryPartSearch = " ( " +ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT +" ilike '%" + searchString + "%'"
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_DESCRIPTION + " ilike '%" + searchString + "%'"
					+ " or " + ItemCategory.TABLE_NAME + "." + ItemCategory.ITEM_CATEGORY_NAME + " ilike '%" + searchString + "%'" + ") ";


			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryPartSearch;
				queryNormalFirst = false;
			}
			else
			{
				queryNormal = queryNormal + " AND " + queryPartSearch;
			}
		}





		if(sortBy!=null)
		{
			if(!sortBy.equals(""))
			{
				String queryPartSortBy = " ORDER BY " + sortBy;

				queryNormal = queryNormal + queryPartSortBy;
			}
		}



		if(limit !=null)
		{

			String queryPartLimitOffset = "";

			if(offset!=null)
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

			}else
			{
				queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
			}


			queryNormal = queryNormal + queryPartLimitOffset;
		}




		ArrayList<ItemCategory> itemCategoryList = new ArrayList<ItemCategory>();

		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		boolean rootRemoved = false;

		try {

			connection = dataSource.getConnection();

			statement = connection.createStatement();

			rs = statement.executeQuery(queryNormal);

			while(rs.next())
			{
				ItemCategory itemCategory = new ItemCategory();

				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));

				if(itemCategory.getItemCategoryID()==1)
				{
					rootRemoved=true;
					continue;
				}

				itemCategory.setParentCategoryID(rs.getInt(ItemCategory.PARENT_CATEGORY_ID));
				itemCategory.setIsLeafNode(rs.getBoolean(ItemCategory.IS_LEAF_NODE));
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
				itemCategory.setCategoryName(rs.getString(ItemCategory.ITEM_CATEGORY_NAME));

				itemCategory.setisAbstractNode(rs.getBoolean(ItemCategory.IS_ABSTRACT));
				itemCategory.setDescriptionShort(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION_SHORT));

				itemCategory.setCategoryDescription(rs.getString(ItemCategory.ITEM_CATEGORY_DESCRIPTION));

				endPoint.setItemCount(rs.getInt("full_count"));


				itemCategoryList.add(itemCategory);
			}



			if(rootRemoved && endPoint.getItemCount()!=0)
			{
				endPoint.setItemCount(endPoint.getItemCount()-1);
			}



//			if(parentIsNull!=null&& parentIsNull)
//			{
//				 exclude the root category
//				for(ItemCategory itemCategory : itemCategoryList)
//				{
//					if(itemCategory.getItemCategoryID()==1)
//					{
//						itemCategoryList.remove(itemCategory);
//
//						break;
//					}
//				}
//			}


//			conn.close();

			System.out.println("Total itemCategories queried " + itemCategoryList.size());


			endPoint.setResults(itemCategoryList);

		}


		catch (SQLException e) {
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


		return endPoint;
	}




	
	





	public ItemCategoryEndPoint getEndPointMetaDataTwo(Integer parentID, Boolean parentIsNull)
	{

		String query = "";

		String queryNormal = "SELECT count(" + ItemCategory.TABLE_NAME +"." + ItemCategory.ITEM_CATEGORY_ID+ ") as item_count "
										+ " FROM " + ItemCategory.TABLE_NAME;



		boolean queryNormalFirst = true;

		if(parentID!=null)
		{
			queryNormal = queryNormal + " WHERE "
					+ ItemCategory.PARENT_CATEGORY_ID
					+ "=" + parentID ;

			queryNormalFirst = false;
		}


/*
		if(shopID!=null)
		{

			String queryPartShopID = "";

			queryPartShopID = queryPartShopID + ShopContract.TABLE_NAME + "."
					+ ShopContract.ITEM_ID + " = " + shopID;

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryPartShopID;
			}
			else
			{
				queryNormal = queryNormal + " AND " + queryPartShopID;
			}


			queryNormalFirst = false;
		}
*/



		if(parentIsNull!=null&& parentIsNull)
		{

			String queryNormalPart = ItemCategory.PARENT_CATEGORY_ID + " IS NULL";

			if(queryNormalFirst)
			{
				queryNormal = queryNormal + " WHERE " + queryNormalPart;

			}else
			{
				queryNormal = queryNormal + " AND " + queryNormalPart;

			}
		}


			query = queryNormal;




		ItemCategoryEndPoint endPoint = new ItemCategoryEndPoint();


		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		try {

			connection = dataSource.getConnection();
			statement = connection.createStatement();

			rs = statement.executeQuery(query);

			while(rs.next())
			{
				endPoint.setItemCount(rs.getInt("item_count"));
			}


			if(parentIsNull!=null&& parentIsNull)
			{
				// exclude the root category
				endPoint.setItemCount(endPoint.getItemCount()-1);
			}


			System.out.println("Item Category EndPoint call count :  " + endPoint.getItemCount());

		}


		catch (SQLException e) {
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

		return endPoint;
	}






	public ItemCategory checkRoot(int itemCategoryID)
	{
		
		String query = "SELECT " + ItemCategory.ITEM_CATEGORY_ID
					+ " FROM " + ItemCategory.TABLE_NAME
					+ " WHERE " +  ItemCategory.ITEM_CATEGORY_ID +  "= ?";

		
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		
		ItemCategory itemCategory = null;
		
		try {
			
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(query);

			statement.setObject(1,itemCategoryID);

			rs = statement.executeQuery();
			
			while(rs.next())
			{
				itemCategory = new ItemCategory();
				itemCategory.setItemCategoryID(rs.getInt(ItemCategory.ITEM_CATEGORY_ID));
			}
			
			
			//System.out.println("Total itemCategories queried " + itemCategoryList.size());	
	
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		
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
	
		return itemCategory;
	}


	public ItemCategory getItemCatImageURL(
			Integer itemCatID
	) {

		String queryJoin = "SELECT DISTINCT "
				+ ItemCategory.TABLE_NAME + "." + ItemCategory.IMAGE_PATH + ""
				+ " FROM " + ItemCategory.TABLE_NAME
				+ " WHERE " + ItemCategory.ITEM_CATEGORY_ID + " = " + itemCatID;


		ItemCategory itemCategory = null;
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
				itemCategory = new ItemCategory();
				itemCategory.setItemCategoryID(itemCatID);
				itemCategory.setImagePath(rs.getString(ItemCategory.IMAGE_PATH));
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

		return itemCategory;
	}




}
