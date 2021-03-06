package org.nearbyshops.gidb.RESTEndpoints;

import net.coobird.thumbnailator.Thumbnails;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.nearbyshops.gidb.DAOsPrepared.ItemCategoryDAO;
import org.nearbyshops.gidb.Globals.GlobalConstants;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Image;
import org.nearbyshops.gidb.Model.Item;
import org.nearbyshops.gidb.Model.ItemCategory;
import org.nearbyshops.gidb.ModelEndpoints.ItemCategoryEndPoint;
import org.nearbyshops.gidb.ModelRoles.Admin;
import org.nearbyshops.gidb.ModelRoles.Staff;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Path("/api/v1/ItemCategory")
public class ItemCategoryResource {


	private ItemCategoryDAO itemCategoryDAO = Globals.itemCategoryDAO;


	
	public ItemCategoryResource() {
		super();
		// TODO Auto-generated constructor stub
	}                                 
	
	
	@POST	
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response saveItemCategory(ItemCategory itemCategory)
	{
		int idOfInsertedRow = -1;

		if(Globals.accountApproved instanceof Staff) {

			Staff staff = (Staff) Globals.accountApproved;

			if (!staff.isPermitCreateItemCategories())
			{
				// the staff member doesnt have persmission to post Item Category

				throw new ForbiddenException("Not Permitted");
			}

			idOfInsertedRow = itemCategoryDAO.saveItemCatForStaff(itemCategory,staff.getUserID(),false);
		}
		else if(Globals.accountApproved instanceof Admin)
		{
			idOfInsertedRow = itemCategoryDAO.saveItemCategory(itemCategory);
		}
		else
		{
			throw new ForbiddenException("Not Permitted");
		}



//		System.out.println(itemCategory.getCategoryName() + " | " + itemCategory.getCategoryDescription());
	

		itemCategory.setItemCategoryID(idOfInsertedRow);
		
		
		if(idOfInsertedRow >=1)
		{


			return Response.status(Status.CREATED)
					.location(URI.create("/api/ItemCategory/" + idOfInsertedRow))
					.entity(itemCategory)
					.build();
			
		}else if(idOfInsertedRow <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}


		return null;
	}
	
	
	@DELETE
	@Path("/{ItemCategoryID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response deleteItemCategory(@PathParam("ItemCategoryID")int itemCategoryID)
	{

		if(Globals.accountApproved instanceof Staff) {

			Staff staff = (Staff) Globals.accountApproved;

			if (staff.isPermitDeleteOnlyItemCategoriesAddedBySelf())
			{
				if(Globals.staffItemCatDAO.checkStaffItemCat(itemCategoryID,staff.getUserID())==null)
				{
					// attempt to delete item category not added by self
					throw new ForbiddenException("Not Permitted");
				}
			}
			else if(staff.isPermitDeleteItemCategories())
			{

			}
			else
			{
				// the staff member doesnt have persmission to delete Item Category
				throw new ForbiddenException("Not Permitted");
			}

		}



		String message = "";

		ItemCategory itemCategory = itemCategoryDAO.getItemCatImageURL(itemCategoryID);
		int rowCount = itemCategoryDAO.deleteItemCategory(itemCategoryID);


		if(itemCategory!=null && rowCount>=1)
		{
			// delete successful delete the image also
			System.out.println("Image FIle Deleted : " + itemCategory.getImagePath());
			deleteImageFileInternal(itemCategory.getImagePath());
		}



		message = "Total Deleted : " + rowCount;

		if(rowCount>=1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}

		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}



	@PUT
	@Path("/ChangeParent/{ItemCategoryID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response changeParent(@PathParam("ItemCategoryID")int itemCategoryID, ItemCategory itemCategory)
	{
		if(Globals.accountApproved instanceof Staff) {

			Staff staff = (Staff) Globals.accountApproved;

//			if (!staff.isPermitUpdateItemCategories())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}


			if (staff.isPermitUpdateOnlyItemCategoriesAddedBySelf())
			{
				if(Globals.staffItemCatDAO.checkStaffItemCat(itemCategoryID,staff.getUserID())==null)
				{
					// attempt to delete item category not added by self
					throw new ForbiddenException("Not Permitted");
				}
			}
			else if(staff.isPermitUpdateItemCategories())
			{

			}
			else
			{
				// the staff member doesnt have persmission to delete Item Category
				throw new ForbiddenException("Not Permitted");
			}

		}




		itemCategory.setItemCategoryID(itemCategoryID);

		System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
				+ " " + itemCategory.getCategoryDescription() + " Parent DELIVERY_GUY_SELF_ID : " +  itemCategory.getParentCategoryID());

		int rowCount = itemCategoryDAO.changeParent(itemCategory);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}



	@PUT
	@Path("/ChangeParent")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response changeParentBulk(List<ItemCategory> itemCategoryList)
	{
		if(Globals.accountApproved instanceof Staff) {

			Staff staff = (Staff) Globals.accountApproved;

			if (!staff.isPermitUpdateItemCategories())
			{
				// the staff member doesnt have persmission to post Item Category

				throw new ForbiddenException("Not Permitted");
			}


		}


		int rowCountSum = 0;

//		for(ItemCategory itemCategory : itemCategoryList)
//		{
//			rowCountSum = rowCountSum + itemCategoryDAO.updateItemCategory(itemCategory);
//		}

		rowCountSum = itemCategoryDAO.changeParentBulk(itemCategoryList);



		if(rowCountSum ==  itemCategoryList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemCategoryList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}




	@PUT
	@Path("/{ItemCategoryID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response updateItemCategory(@PathParam("ItemCategoryID")int itemCategoryID, ItemCategory itemCategory)
	{


		if(Globals.accountApproved instanceof Staff) {

			Staff staff = (Staff) Globals.accountApproved;

//			if (!staff.isPermitUpdateItemCategories())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}



			if (staff.isPermitUpdateOnlyItemCategoriesAddedBySelf())
			{
				if(Globals.staffItemCatDAO.checkStaffItemCat(itemCategoryID,staff.getUserID())==null)
				{
					// attempt to delete item category not added by self
					throw new ForbiddenException("Not Permitted");
				}
			}
			else if(staff.isPermitUpdateItemCategories())
			{

			}
			else
			{
				// the staff member doesnt have persmission to delete Item Category
				throw new ForbiddenException("Not Permitted");
			}
		}




		itemCategory.setItemCategoryID(itemCategoryID);

		System.out.println("ItemCategoryID: " + itemCategoryID + " " + itemCategory.getCategoryName()
				+ " " + itemCategory.getCategoryDescription() + " Parent DELIVERY_GUY_SELF_ID : " +  itemCategory.getParentCategoryID());

		int rowCount = itemCategoryDAO.updateItemCategory(itemCategory);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}



	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response updateItemCategoryBulk(List<ItemCategory> itemCategoryList)
	{

		if(Globals.accountApproved instanceof Staff) {

			Staff staff = (Staff) Globals.accountApproved;

			if (!staff.isPermitUpdateItemCategories())
			{
				// the staff member doesnt have persmission to post Item Category
				throw new ForbiddenException("Not Permitted");
			}
		}



		int rowCountSum = 0;

		for(ItemCategory itemCategory : itemCategoryList)
		{
			rowCountSum = rowCountSum + itemCategoryDAO.updateItemCategory(itemCategory);
		}

		if(rowCountSum ==  itemCategoryList.size())
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}
		else if( rowCountSum < itemCategoryList.size() && rowCountSum > 0)
		{

			return Response.status(Status.PARTIAL_CONTENT)
					.entity(null)
					.build();
		}
		else if(rowCountSum == 0 ) {

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}





	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/QuerySimple")
	public Response getItemCategoriesQuerySimple(
			@QueryParam("ParentID")Integer parentID,
			@QueryParam("IsDetached")Boolean parentIsNull,
			@QueryParam("SearchString") String searchString,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") Integer offset,
			@QueryParam("metadata_only")Boolean metaonly
	)
	{

		final int max_limit = 100;

		if(limit!=null)
		{
			if(limit>=max_limit)
			{
				limit = max_limit;
			}
		}
		else
		{
			limit = 30;
		}


		ItemCategoryEndPoint endPoint = itemCategoryDAO
				.getItemCategoriesSimplePrepared(
						parentID,
						parentIsNull,
						searchString,
						sortBy,limit,offset);


		endPoint.setLimit(limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(offset);

//		if(endPoint.getItemCount()==null)
//		{
//			endPoint.setItemCount(0);
//		}


//		try {
//			Thread.sleep(150);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}



		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}


	// Add from Global

	@POST
	@Path("/AddFromGlobal")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response addItemFromGlobal(List<ItemCategory> itemCatList)
	{

		int rowCountSum = 0;

		if(Globals.accountApproved instanceof Staff) {

			// checking permission
			Staff staff = (Staff) Globals.accountApproved;

			if (!staff.isPermitCreateItemCategories())
			{
				// the staff member doesnt have persmission to post Item Category

				throw new ForbiddenException("Not Permitted");
			}
		}


		for(ItemCategory itemCat : itemCatList)
		{
			itemCat.setImagePath(saveNewImage(itemCat.getRt_gidb_service_url(),itemCat.getImagePath()));

			int rowCountTemp = 0;

			if(Globals.accountApproved instanceof Staff)
			{
				rowCountTemp = Globals.itemCategoryDAO.saveItemCatForStaff(itemCat,((Staff) Globals.accountApproved).getUserID(),true);
			}
			else if(Globals.accountApproved instanceof Admin)
			{
				rowCountTemp = Globals.itemCategoryDAO.saveItemCatRowCount(itemCat);
			}


			rowCountSum = rowCountSum + rowCountTemp;

			if(rowCountTemp==0)
			{
				// update failed delete the new added Item Image
				deleteImageFileInternal(itemCat.getImagePath());
			}

		}


		if(rowCountSum >= itemCatList.size())
		{

			return Response.status(Status.CREATED)
					.build();

		}
		else if(rowCountSum < itemCatList.size() && rowCountSum > 0)
		{
			return Response.status(Status.PARTIAL_CONTENT)
					.build();

		}
		else if(rowCountSum <= 0)
		{
			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}

		return null;
	}




	// Image Utility Methods

	boolean deleteImageFileInternal(String fileName)
	{
		boolean deleteStatus = false;

		System.out.println("Filename: " + fileName);

		try {

			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return deleteStatus;
	}




	private String saveNewImage(String serviceURL,String imageID)
	{
		try
		{
			serviceURL = serviceURL + "/api/v1/ItemCategory/Image/" + imageID;

			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(serviceURL)
					.build();

			okhttp3.Response response = null;
			response = client.newCall(request).execute();
			response.body().byteStream();
			System.out.println();

			return uploadNewImage(response.body().byteStream());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}




	String uploadNewImage(InputStream in)
	{

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



		String fileName = "" + System.currentTimeMillis();


		try {

			// Copy the file to its location.
			long filesize = 0;

			filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

			if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
			{
				// delete file if it exceeds the file size limit
				Files.deleteIfExists(BASE_DIR.resolve(fileName));
				return null;
			}

			createThumbnails(fileName);

			Image image = new Image();
			image.setPath(fileName);

			// Return a 201 Created response with the appropriate Location header.

		}
		catch (IOException e) {
			e.printStackTrace();

			return null;
		}

		return fileName;
	}




	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/ItemCategory");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
								@QueryParam("PreviousImageName") String previousImageName
	) throws Exception
	{

//		if(Globals.accountApproved instanceof Staff) {
//
//			Staff staff = (Staff) Globals.accountApproved;
//
//			if (!staff.isPermitCreateItemCategories())
//			{
//				// the staff member doesnt have persmission to post Item Category
//				throw new ForbiddenException("Not Permitted");
//			}
//		}



		if(previousImageName!=null)
		{
			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
		}


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



		String fileName = "" + System.currentTimeMillis();

		// Copy the file to its location.
		long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
		{
			// delete file if it exceeds the file size limit
			Files.deleteIfExists(BASE_DIR.resolve(fileName));

			return Response.status(Status.EXPECTATION_FAILED).build();
		}


		createThumbnails(fileName);


		Image image = new Image();
		image.setPath(fileName);

		// Return a 201 Created response with the appropriate Location header.

		return Response.status(Status.CREATED).location(URI.create("/api/Images/" + fileName)).entity(image).build();
	}



	private void createThumbnails(String filename)
	{
		try {

			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(300,300)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));

			//.toFile(new File("five-" + filename + ".jpg"));

			//.toFiles(Rename.PREFIX_DOT_THUMBNAIL);


			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(500,500)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));



		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@GET
	@Path("/Image/{name}")
	@Produces("image/jpeg")
	public InputStream getImage(@PathParam("name") String fileName) {

		//fileName += ".jpg";
		java.nio.file.Path dest = BASE_DIR.resolve(fileName);

		if (!Files.exists(dest)) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}


		try {

			return Files.newInputStream(dest);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}



	@DELETE
	@Path("/Image/{name}")
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response deleteImageFile(@PathParam("name")String fileName)
	{

//		if(Globals.accountApproved instanceof Staff) {
//
//			Staff staff = (Staff) Globals.accountApproved;
//
//			if (!staff.isPermitDeleteItemCategories())
//			{
//				// the staff member doesnt have persmission to post Item Category
//
//				throw new ForbiddenException("Not Permitted");
//			}
//		}

		boolean deleteStatus = false;

		Response response;

		System.out.println("Filename: " + fileName);

		try {


			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(!deleteStatus)
		{
			response = Response.status(Status.NOT_MODIFIED).build();

		}else
		{
			response = Response.status(Status.OK).build();
		}

		return response;
	}


}
