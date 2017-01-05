package org.nearbyshops.gidb.RESTEndpointRoles;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.gidb.DAOsPreparedRoles.StaffDAOPrepared;
import org.nearbyshops.gidb.Globals.APIErrors;
import org.nearbyshops.gidb.Globals.ErrorNBSAPI;
import org.nearbyshops.gidb.Globals.GlobalConstants;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Image;
import org.nearbyshops.gidb.ModelRoles.Staff;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.nearbyshops.gidb.Globals.Globals.staffDAOPrepared;


@Path("/v1/Staff")
public class StaffResource {


	private StaffDAOPrepared daoPrepared = staffDAOPrepared;

	public StaffResource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	public Response createStaff(Staff staff)
	{

//		staff.setEnabled(true);
//		staff.setWaitlisted(false);


		int idOfInsertedRow = daoPrepared.saveStaff(staff);

		staff.setUserID(idOfInsertedRow);

		if(idOfInsertedRow >=1)
		{


			return Response.status(Status.CREATED)
					.location(URI.create("/api/staff/" + idOfInsertedRow))
					.entity(staff)
					.build();
			
		}else if(idOfInsertedRow <=0)
		{

			//Response.status(Status.CREATED).location(arg0)
			
			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}
		
		return null;
	}



	
	@PUT
	@Path("/{StaffID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response updateSTAFF(@PathParam("StaffID")int staffID,
								Staff staff)
	{

		staff.setUserID(staffID);

		int rowCount = daoPrepared.updateStaff(staff);

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
	@Path("/UpdateBySelf/{StaffID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_STAFF_DISABLED})
	public Response updateBySelf(@PathParam("StaffID")int staffID, Staff staff)
	{

//		@Context HttpHeaders headers

		staff.setUserID(staffID);

		if(Globals.accountApproved instanceof Staff)
		{
			if(((Staff) Globals.accountApproved).getUserID()!=staffID)
			{
				// an atempt to update the account of other staff member. Which is not permitted !
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
						.build();

				throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
			}
		}


		int rowCount = daoPrepared.updateStaffBySelf(staff);

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




	@DELETE
	@Path("/{StaffID}")
	//@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF_DISABLED})
	public Response deleteStaff(@PathParam("StaffID")int staffID)
	{
		//		@Context HttpHeaders headers



		if(Globals.accountApproved instanceof Staff)
		{
			if(((Staff) Globals.accountApproved).getUserID()!=staffID)
			{
				// an attempt to delete the account of other staff member. Which is not permitted !
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
						.build();

				throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
			}
		}



		int rowCount = daoPrepared.deleteStaff(staffID);
		
		
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
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	public Response getStaff(@QueryParam("IsEnabled") Boolean isEnabled)
	{

		List<Staff> list = daoPrepared.getStaffForAdmin(isEnabled);

		GenericEntity<List<Staff>> listEntity = new GenericEntity<List<Staff>>(list){
			
		};
	
		
		if(list.size()<=0)
		{

			return Response.status(Status.NO_CONTENT)
					.build();
			
		}else
		{

			return Response.status(Status.OK)
					.entity(listEntity)
					.build();
		}
		
	}
	
	
	/*@GET
	@Path("/{staffID}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response getStaffForSelf(@PathParam("staffID")int staffID)
	{

		Staff staff = daoPrepared.getStaffForSelf(staffID);
		
		if(staff != null)
		{

			return Response.status(Status.OK)
			.entity(staff)
			.build();
			
		} else 
		{

			return Response.status(Status.NO_CONTENT)
					.build();
			
		}
		
	}*/





	@GET
	@Path("/CheckUsernameExists/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart(@PathParam("username")String username)
	{
		// Roles allowed not used for this method due to performance and effeciency requirements. Also
		// this endpoint doesnt required to be secured as it does not expose any confidential information

		boolean result = staffDAOPrepared.checkUsernameExists(username);

		if(result)
		{
			return Response.status(Status.OK)
					.build();

		} else
		{
			return Response.status(Status.NO_CONTENT)
					.build();
		}

	}



	@GET
	@Path("Login")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_STAFF_DISABLED})
	public Response getStaffLogin(@Context HttpHeaders header)
	{


		Staff staff = null;

		if(Globals.accountApproved instanceof Staff)
		{
			staff = staffDAOPrepared.getStaffForSelf(((Staff) Globals.accountApproved).getUserID());
		}


		if(staff != null)
		{

			return Response.status(Status.OK)
					.entity(staff)
					.build();

		} else
		{

			return Response.status(Status.UNAUTHORIZED)
					.build();

		}



/*
		//Get request headers
		final MultivaluedMap<String, String> headers = header.getRequestHeaders();

		//Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		//If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {

			Response response = Response.status(Status.UNAUTHORIZED)
					.entity(new ErrorNBSAPI(401, APIErrors.UPDATE_BY_WRONG_USER))
					.build();

			throw new NotAuthorizedException(response);
		}

		//Get encoded username and password

		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		//Decode username and password
		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		System.out.println("Username:Password" + usernameAndPassword);

		//Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		Staff staff = daoPrepared.checkStaff(null,username,password);

		if(staff!= null)
		{
			staff.setPassword(null);

			Response response;

			response = Response.status(Status.OK)
					.entity(staff)
					.build();

			return response;

		} else
		{
			return Response.status(Status.UNAUTHORIZED)
					.build();
		}*/

	}


	


	/*private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	private boolean verifyStaffAccount(HttpHeaders header, int staffID)
	{

		boolean result = true;


		//Get request headers
		final MultivaluedMap<String, String> headers = header.getRequestHeaders();

		//Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		//If no authorization information present; block access
		if (authorization == null || authorization.isEmpty()) {
//                requestContext.abortWith(ACCESS_DENIED);

			return false;
		}

		//Get encoded username and password
		final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

		//Decode username and password
		String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

		System.out.println("Username:Password" + usernameAndPassword);

		//Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();


		Staff staff = daoPrepared.checkStaff(null,username,password);
		// Distributor account exist and is enabled
		if(staff!=null && staff.getEnabled())
		{
			// If code enters here implies that distributor account is used for update. So we need to check if
			// the distributor is same as the one authorized.

			if(staff.getUserID()!=staffID)
			{
				// the user doing an update is not the same as the user whose profile is being updated so has to
				// stop this operation, and should throw an unauthorized exception in this situation.
				return false;
			}
		}

		return true;
	}
	*/






	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Staff");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF_DISABLED})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
								@QueryParam("PreviousImageName") String previousImageName
	) throws Exception
	{


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
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}



	@DELETE
	@Path("/Image/{name}")
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF_DISABLED})
	public Response deleteImageFile(@PathParam("name")String fileName)
	{

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
