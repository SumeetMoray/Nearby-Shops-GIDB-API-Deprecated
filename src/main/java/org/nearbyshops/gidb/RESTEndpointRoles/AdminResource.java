package org.nearbyshops.gidb.RESTEndpointRoles;

import net.coobird.thumbnailator.Thumbnails;
import org.nearbyshops.gidb.DAOsPreparedRoles.AdminDAOPrepared;
import org.nearbyshops.gidb.Globals.GlobalConstants;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.Model.Image;
import org.nearbyshops.gidb.ModelRoles.Admin;

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


@Path("/v1/Admin")
public class AdminResource {


	private AdminDAOPrepared daoPrepared = Globals.adminDAOPrepared;

	public AdminResource() {
		super();
		// TODO Auto-generated constructor stub
	}


	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed(GlobalConstants.ROLE_ADMIN)
	public Response updateAdmin(Admin admin)
	{
		if(Globals.accountApproved instanceof Admin)
		{

			Admin adminApproved = (Admin) Globals.accountApproved;
			admin.setAdminID(adminApproved.getAdminID());
			int rowCount = daoPrepared.updateAdmin(admin);

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
		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}

		return null;
	}




	@GET
	@Path("Login")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN})
	public Response getDistributorLogin()
	{
		//@Context HttpHeaders header
		if(Globals.accountApproved instanceof Admin)
		{
			Admin adminApproved = (Admin) Globals.accountApproved;

			List<Admin> admin = Globals.adminDAOPrepared.getAdmin(adminApproved.getUsername(),null);

			if(admin.size()==1)
			{

				return Response.status(Status.OK)
						.entity(admin.get(0))
						.build();

			}
			else
			{

				return Response.status(Status.UNAUTHORIZED)
						.build();

			}

		}
		else
		{
			throw new ForbiddenException("Not Permitted !");
		}

	}






	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/Admin");
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
