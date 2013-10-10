package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.FeedObjects;

public class Project
{
	public ArrayList<FeedObjects> GetFeeds(Connection connection) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT gname, count(gname) AS number FROM `terrorism_data` GROUP BY gname ORDER BY number DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FeedObjects feedObject = new FeedObjects();
				feedObject.setGroup(rs.getString("gname"));
				feedObject.setNumberOfAttacks(rs.getString("number"));
				feedData.add(feedObject);
			}
			return feedData;
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
			throw e;
		}
	}
	
	public ArrayList<FeedObjects> GetFeeds(Connection connection, String terrorParam1, String terrorParam2) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT gname, count(gname) AS number FROM `terrorism_data`"
						+ "WHERE iyear >= '" + terrorParam1 + "' AND iyear<='" + terrorParam2 + "' GROUP BY gname ORDER BY number DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FeedObjects feedObject = new FeedObjects();
				feedObject.setGroup(rs.getString("gname"));
				feedObject.setNumberOfAttacks(rs.getString("number"));
				feedData.add(feedObject);
			}
			return feedData;
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
			throw e;
		}
	}
	
	public ArrayList<FeedObjects> GetFeeds1(Connection connection, String terrorParam3) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT gname, count(gname) AS number FROM `terrorism_data`"
						+ "WHERE attacktype1_txt = '" + terrorParam3 +  "' GROUP BY gname ORDER BY number DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FeedObjects feedObject = new FeedObjects();
				feedObject.setGroup(rs.getString("gname"));
				feedObject.setNumberOfAttacks(rs.getString("number"));
				feedData.add(feedObject);
			}
			return feedData;
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
			throw e;
		}
	}
	
	

	

}