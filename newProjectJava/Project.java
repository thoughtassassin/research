package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
			System.out.println("Error Here" + e.getMessage());
			throw e;
		}
	}
	
	public ArrayList<FeedObjects> GetFeeds(Connection connection, String terrorParam1, String terrorParam2) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT gname, count(gname) AS number FROM `terrorism_data`"
						+ "WHERE iyear >= '" + terrorParam1 + "' AND iyear<='" + terrorParam2 + "' GROUP BY gname ORDER BY number DESC LIMIT 10");
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
	
	//Get the top ten terror groups
	public ArrayList<FeedObjects> GetTopTen(Connection connection, String terrorParam1, String terrorParam2) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		String tenthHighestAttacks = "";
		
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT count(gname) AS number FROM `terrorism_data` WHERE iyear >= '" + terrorParam1 + "' AND iyear<='" + terrorParam2 + "' GROUP BY gname ORDER BY number DESC LIMIT 9,1");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				tenthHighestAttacks = rs.getString("number");
			}
			
			//System.out.println(tenthHighestAttacks);
			
			ps = connection.prepareStatement("(SELECT gname, count(gname) AS number FROM `terrorism_data` WHERE iyear >= '"+ terrorParam1 + "' AND iyear<='" + terrorParam2 + "' GROUP BY gname ORDER BY number DESC LIMIT 9)"
					+ " UNION "
					+ "(SELECT REPLACE('Bottom Groups', 'gname', ''), sum(newnumber) AS number FROM (SELECT gname, count(gname) AS newnumber FROM `terrorism_data` WHERE iyear >= '"+ terrorParam1 + "' AND iyear<='"+ terrorParam2 + "' GROUP BY gname HAVING newnumber <= '" + tenthHighestAttacks + "') AS lower_groups);");
				
			rs = ps.executeQuery();
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
	
	//Get the list of attacks by year for a terror group
	public ArrayList<Map<String, String>> GetAttacksByYear(Connection connection, String yearStart, String yearEnd, String groupName) throws Exception
	{
		ArrayList<Map<String, String>> listOfAttacks = new ArrayList<Map<String, String>>();
		
		try
		{
			for ( int year = Integer.parseInt(yearStart); year <= Integer.parseInt(yearEnd); year++ )
			{
				PreparedStatement ps = connection.prepareStatement("SELECT count(gname) AS number FROM `terrorism_data` WHERE iyear = '" + year + "' AND gname = '" + groupName + "'");
				ResultSet rs = ps.executeQuery();
				while(rs.next())
				{
					Map <String, String> numAttacksByYear = new HashMap<String, String>();
					numAttacksByYear.put(Integer.toString(year), rs.getString("number"));
					listOfAttacks.add(numAttacksByYear);
				}
			}
	
			return listOfAttacks;
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
			throw e;
		}
	}
	
	//Get the list of attacks by year for a terror group
	public Map<String, String> iGetAttacksByYear(Connection connection, String yearStart, String yearEnd, String groupName) throws Exception
	{
		//ArrayList<Map<String, String>> listOfAttacks = new ArrayList<Map<String, String>>();
		Map <String, String> listOfAttacks = new TreeMap<String, String>();
		
		for ( int year = Integer.parseInt(yearStart); year <= Integer.parseInt(yearEnd); year++ )
		{	
			listOfAttacks.put(Integer.toString(year), "0");
		}
		
		try
		{
			
			PreparedStatement ps = connection.prepareStatement("SELECT iyear, count(gname) AS number FROM `terrorism_data` WHERE iyear >= '" + yearStart + "' AND iyear<='" + yearEnd + "' AND gname = '" + groupName + "' GROUP BY iyear");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				listOfAttacks.put(rs.getString("iyear"), rs.getString("number"));
			}			
	
			return listOfAttacks;
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
			throw e;
		}
	}
	
	

	

}