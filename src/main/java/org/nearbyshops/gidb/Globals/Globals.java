package org.nearbyshops.gidb.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.nearbyshops.gidb.DAOsPrepared.*;
import org.nearbyshops.gidb.DAOsPreparedRoles.AdminDAOPrepared;
import org.nearbyshops.gidb.DAOsPreparedRoles.StaffDAOPrepared;
import org.nearbyshops.gidb.JDBCContract;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


public class Globals {

	public static AdminDAOPrepared adminDAOPrepared = new AdminDAOPrepared();
	public static StaffDAOPrepared staffDAOPrepared = new StaffDAOPrepared();
	public static ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAO();
	public static ItemDAO itemDAO = new ItemDAO();
	public static ItemDAOJoinOuter itemDAOJoinOuter = new ItemDAOJoinOuter();

	public static StaffItemDAO staffItemDAO = new StaffItemDAO();
	public static StaffItemCatDAO staffItemCatDAO = new StaffItemCatDAO();

	public static ItemImagesDAO itemImagesDAO = new ItemImagesDAO();


//	public static ServiceConfigurationDAO serviceConfigurationDAO = new ServiceConfigurationDAO();


	// static reference for holding security accountApproved

	public static Object accountApproved;


	// Configure Connection Pooling

	private static HikariDataSource dataSource;



	public static HikariDataSource getDataSource()
	{
		if(dataSource==null)
		{
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(JDBCContract.CURRENT_CONNECTION_URL);
			config.setUsername(JDBCContract.CURRENT_USERNAME);
			config.setPassword(JDBCContract.CURRENT_PASSWORD);

			dataSource = new HikariDataSource(config);
		}

		return dataSource;
	}



	// SSE Notifications Support

	public static Map<Integer,SseBroadcaster> broadcasterMap = new HashMap<>();

	public static String broadcastMessage(String message, int shopID) {

		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("Order Received !")
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(String.class, message)
				.build();


		if(broadcasterMap.get(shopID)!=null)
		{
			broadcasterMap.get(shopID).broadcast(event);
		}

		return "Message '" + message + "' has been broadcast.";
	}




	public static SseBroadcaster broadcaster = new SseBroadcaster();

	public static String broadcastMessage(String message) {
		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("message")
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(String.class, message)
				.build();

		broadcaster.broadcast(event);

		return "Message '" + message + "' has been broadcast.";
	}

}
