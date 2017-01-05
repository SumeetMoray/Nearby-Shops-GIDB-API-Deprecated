package org.nearbyshops.gidb;



import org.nearbyshops.gidb.Globals.ErrorNBSAPI;
import org.nearbyshops.gidb.Globals.GlobalConstants;
import org.nearbyshops.gidb.Globals.Globals;
import org.nearbyshops.gidb.ModelRoles.Admin;
import org.nearbyshops.gidb.ModelRoles.Staff;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Created by sumeet on 9/9/16.
 */


@Provider
public class AuthenticationFilter implements ContainerRequestFilter {


//    private AdminDAOPrepared adminDAOPreparedInheritance = Globals.adminDAOPrepared;
//    private StaffDAOPrepared staffDAOPrepared = Globals.staffDAOPrepared;


    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED)
            .entity("You cannot access this resource").build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN)
            .entity("Access blocked for all users !!").build();


    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

//        System.out.println("Security Fileter");

            if (method.isAnnotationPresent(DenyAll.class)) {

                throw new ForbiddenException("Access is ErrorNBSAPI !");
            }



        //Verify user access
        if (method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            if (authorization == null || authorization.isEmpty()) {
//                requestContext.abortWith(ACCESS_DENIED);

                throw new NotAuthorizedException("Access is Denied ! Credentials not present");
            }


            final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");



            //Decode username and password
            String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));


            System.out.println("Username:Password" + usernameAndPassword);

            //Split username and password tokens
            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            //Verifying Username and password
            System.out.println(username);
            System.out.println(password);

            Globals.accountApproved = isUserAllowed(username, password, rolesSet);

            }
        }


    private Object isUserAllowed(final String username, final String password, final Set<String> rolesSet)
    {


        //        boolean isAllowed = false;

        //        boolean isEnabled = false;

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);


        for(String role : rolesSet)
        {

            if(role.equals(GlobalConstants.ROLE_ADMIN))
            {

                Admin adminInheritance = Globals.adminDAOPrepared.checkAdmin(username,password);
                if(adminInheritance != null)
                {
                    return adminInheritance;
                }
            }
            else if(role.equals(GlobalConstants.ROLE_STAFF_DISABLED))
            {
                Staff staffInheritance = Globals.staffDAOPrepared.checkStaff(username,password);

                if(staffInheritance !=null )
                {
                    return staffInheritance;
                }

            }
            else if(role.equals(GlobalConstants.ROLE_STAFF))
            {
                Staff staffInheritance = Globals.staffDAOPrepared.checkStaff(username,password);

                if(staffInheritance !=null)
                {
                    if(staffInheritance.getEnabled())
                    {
                        return staffInheritance;
                    }
                    else
                    {

                        Response response = Response.status(403)
                                .entity(new ErrorNBSAPI(403, "Your account is disabled. Contact administrator to know more !"))
                                .build();

                        throw new ForbiddenException("Username or password is Incorrect !",response);

                    }
                }

            }
        }


        throw new NotAuthorizedException("Access is Denied ! We are not able to Identify you. ");
    }

}