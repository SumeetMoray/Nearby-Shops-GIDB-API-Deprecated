package org.nearbyshops.gidb.RESTEndpointRoles;

import org.nearbyshops.gidb.DAOsPreparedRoles.AdminDAOPrepared;
import org.nearbyshops.gidb.Globals.GlobalConstants;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.ModelRoles.Admin;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
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

}
