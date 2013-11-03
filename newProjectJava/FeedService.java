package webService;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.ProjectManager;

import com.google.gson.Gson;

import dto.FeedObjects;

@Path("/WebService")
public class FeedService {

	@GET
	@Path("/GetFeeds")
	@Produces("application/json")
	public String feed()
	{
		String feeds = null;
		try
		{
			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.GetFeeds();
			Gson gson = new Gson();
			//System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);
			//System.out.println("Are we doing anything?");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Exception Error:" + e.getMessage()); //Console
		}
			return feeds;
	}
	
	@GET
	@Path("/GetFeedWithDates")
	@Produces("application/json")
	public String feed2(
			@QueryParam("terrorParam1") String terrorParam1,
			@QueryParam("terrorParam2") String terrorParam2)
	{
		String feeds = null;
		try
		{
			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.GetFeeds( terrorParam1, terrorParam2 );
			Gson gson = new Gson();
			//System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);
			//System.out.println("Are we doing anything?");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Exception Error:" + e.getMessage()); //Console
		}
			return feeds;
	}
	
	@GET
	@Path("/GetFeedWithAttacktype")
	@Produces("application/json")
	public String feed3(
			@QueryParam("terrorParam3") String terrorParam3)
			
	{
		String feeds = null;
		try
		{
			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.GetFeeds( terrorParam3 );
			Gson gson = new Gson();
			//System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);
			//System.out.println("Are we doing anything?");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Exception Error:" + e.getMessage()); //Console
		}
			return feeds;
	}
	
	@GET
	@Path("/GetMethod")
	@Produces(MediaType.TEXT_PLAIN)
	public String newMethod(
			@QueryParam("terrorParam1") String terrorParam1,
			@QueryParam("terrorParam2") String terrorParam2)
	{
		return "These are your two params---->"
				+ terrorParam1 + " and " + terrorParam2;
	}
	
	@GET
	@Path("/GetTopTen")
	@Produces("application/json")
	public String feed3(
			@QueryParam("terrorParam1") String terrorParam1,
			@QueryParam("terrorParam2") String terrorParam2)
	{
		String feeds = null;
		try
		{
			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.GetTopTen( terrorParam1, terrorParam2 );
			Gson gson = new Gson();
			//System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);
			//System.out.println("Are we doing anything?");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Exception Error:" + e.getMessage()); //Console
		}
			return feeds;
	}
	
	@GET
	@Path("/GetListOfAttacks")
	@Produces("application/json")
	public String feed3(
			@QueryParam("yearStart") String yearStart,
			@QueryParam("yearEnd") String yearEnd,
			@QueryParam("terrorGroup") String terrorGroup)
	{
		String feeds = null;
		try
		{
			ArrayList<Map<String,String>> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.GetAttacksByYear( yearStart, yearEnd, terrorGroup );
			Gson gson = new Gson();
			//System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);
			//System.out.println("Are we doing anything?");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Exception Error:" + e.getMessage()); //Console
		}
			return feeds;
	}
	
	
	@GET
	@Path("/GetAttacksByYear")
	@Produces("application/json")
	public String feed4(
			@QueryParam("yearStart") String yearStart,
			@QueryParam("yearEnd") String yearEnd,
			@QueryParam("terrorGroup") String terrorGroup)
	{
		String feeds = null;
		try
		{
			Map<String,String> feedData = null;
			ProjectManager projectManager= new ProjectManager();
			feedData = projectManager.iGetAttacksByYear( yearStart, yearEnd, terrorGroup );
			Gson gson = new Gson();
			//System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);
			//System.out.println("Are we doing anything?");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Exception Error:" + e.getMessage()); //Console
		}
			return feeds;
	}
	

}
