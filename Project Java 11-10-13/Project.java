package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import dto.FeedObjects;
import dto.TotalGroupAttacks;
import dto.AttacksInOneYear;
import dto.AttackInOneYearComparator;

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
	
	//Get the list of attacks by year for a terror group
	public Map<String, Map<String, String>> getAllAttacksByYear(Connection connection, String yearStart, String yearEnd, String groupName) throws Exception
	{
		
		String topTenQuery = "";
		ArrayList<FeedObjects> topTen = this.GetTopTen(connection, yearStart, yearEnd);
						
		for (int i = 0; i < topTen.size(); i++) 
		{
			topTenQuery += "SELECT gname, iyear, count(gname) AS number FROM `terrorism_data` WHERE iyear >= '" + yearStart + "' AND iyear<='" + yearEnd + "' AND gname = \"" + topTen.get(i).getGroup() + "\" GROUP BY iyear";
			
			if ( i != topTen.size() - 1 )
			{
				topTenQuery += " UNION ";
			}
		}
		
		Map<String, Map<String, String>> bigMap = null;
		String currGroupName = "";
		Map<String, String> map = null; 
		
		try
		{
			
			PreparedStatement ps = connection.prepareStatement(topTenQuery);
			ResultSet rs = ps.executeQuery();
			
			int count = 1;
			
			while(rs.next())
			{
				
				if (currGroupName.equals(rs.getString("gname")))
				{
					map.put(rs.getString("iyear"), rs.getString("number"));
					
					if (count == 9)
					{
						bigMap.put(currGroupName, map);
					}
				}
				else
				{
					if ( bigMap == null )
					{
						bigMap = new HashMap<String, Map<String, String>>();
					}
					else
					{
						bigMap.put(currGroupName, map);
						count++; //keep track of last insertion into bigMap
						System.out.println(count);
					}
					
					currGroupName = rs.getString("gname");
					System.out.println(currGroupName);
					
					map = new TreeMap<String, String>();
					
					for ( int year = Integer.parseInt(yearStart); year <= Integer.parseInt(yearEnd); year++ )
					{	
						map.put(Integer.toString(year), "0");
					}
					
					map.put(rs.getString("iyear"), rs.getString("number"));
					
				}
				
			}			
	
			return bigMap;
		}
		catch(Exception e)
		{
			System.out.println("Error Here");
			throw e;
		}
	}
	
	public ArrayList<TotalGroupAttacks> getAttacksByYears(Connection connection, String yearStart, String yearEnd, String groupName) throws Exception
	{
		
		String topTenQuery = "";
		ArrayList<FeedObjects> topTen = this.GetTopTen(connection, yearStart, yearEnd);
						
		for (int i = 0; i < topTen.size(); i++) 
		{
			topTenQuery += "SELECT gname, iyear, count(gname) AS number FROM `terrorism_data` WHERE iyear >= '" + yearStart + "' AND iyear<='" + yearEnd + "' AND gname = \"" + topTen.get(i).getGroup() + "\" GROUP BY iyear";
			
			if ( i != topTen.size() - 1 )
			{
				topTenQuery += " UNION ";
			}
			
		}
		
		System.out.println(topTenQuery);
		
		ArrayList<TotalGroupAttacks> TotalGroupAttacksList = new ArrayList<TotalGroupAttacks>();
		ArrayList<AttacksInOneYear> newAttacksInOneYearList = null;
		String currGroupName = ""; //variable to keep track of group being processed
	
		try {
			
			PreparedStatement ps = connection.prepareStatement(topTenQuery);
			ResultSet rs = ps.executeQuery();
			
			int count = 1;
			int currentYear, startingYear;
			int previousYear = Integer.parseInt(yearStart) - 1;
			TotalGroupAttacks newTotalGroupAttacks = null; //adds new TotalGroupAttacks to TotalGroupAttackList
			AttacksInOneYear newAttacksInOneYear = null; //adds new AttacksInOneYear to newAttacksInOneYearList
			
			while(rs.next())
			{
				//System.out.println(rs.getString("gname"));
				currentYear = Integer.parseInt(rs.getString("iyear"));
				
				if (currGroupName.equals(rs.getString("gname")))
				{
					
					/*
					 * If there is a gap between the next year in the list
					 * and the next year in the result, add new years to
					 * fill the gap
					 */
					
					if ( currentYear != (previousYear + 1))
					{
						for (int i = previousYear; i < currentYear; i++)
						{
							newAttacksInOneYear = new AttacksInOneYear(i, 0);
							newAttacksInOneYearList.add(newAttacksInOneYear);
							System.out.println(currGroupName + " : " + previousYear);
							
						}
						
					}
					
					System.out.println(currGroupName + " new");
					newAttacksInOneYearList.add(new AttacksInOneYear(Integer.parseInt(rs.getString("iyear")), Integer.parseInt(rs.getString("number"))));
					
					
					
					//for the very last loop
					/*
					if (count == 9)
					{
						TotalGroupAttacksList.add(new TotalGroupAttacks(currGroupName, newAttacksInOneYearList));
					}
					*/
					
					previousYear = currentYear;
					
					System.out.println(currGroupName + " : " + previousYear);
					
				}
				else
				{				
					if (count > 1)
					{							
						
						TotalGroupAttacksList.add(new TotalGroupAttacks(currGroupName, newAttacksInOneYearList));
					}
					
					currGroupName = rs.getString("gname");
					//System.out.println(currGroupName);
					
					
					
					if ( currentYear != (previousYear + 1))
					{
						
						for (int i = previousYear + 1; i < currentYear; i++)
						{
							newAttacksInOneYear = new AttacksInOneYear(i, 0);
							newAttacksInOneYearList.add(newAttacksInOneYear);
							//System.out.println(currGroupName + " : " + previousYear);
							
						}
						
					}
					
					newAttacksInOneYearList = new ArrayList<AttacksInOneYear>(); //adds new ArrayList of AttacksInOneYear to TotalGroupAttackList
					newAttacksInOneYearList.add(new AttacksInOneYear(Integer.parseInt(rs.getString("iyear")), Integer.parseInt(rs.getString("number"))));
					
					previousYear = currentYear;
					//System.out.println(currGroupName + " : " + previousYear);
					count++;
					
				}
				
				
			}		
			
			
		}
		catch(Exception e)
		{
			System.out.println("Error in  getAttacksByYear -->" + e.getMessage());
		}
		
		return TotalGroupAttacksList;
	}


	
	

	

}