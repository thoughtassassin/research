package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import dao.Database;
import dao.Project;
import dto.FeedObjects;

public class ProjectManager {

	public ArrayList<FeedObjects> GetFeeds()throws Exception {
		ArrayList<FeedObjects> feeds = null;
		try {
			Database database= new Database();
			Connection connection = database.Get_Connection();
			Project project= new Project();
			feeds=project.GetFeeds(connection);
		}
		catch (Exception e) {
			throw e;
		}
		return feeds;
	}
	
	public ArrayList<FeedObjects> GetFeeds(String terrorParam1, String terrorParam2)throws Exception {
		ArrayList<FeedObjects> feeds = null;
		try {
			Database database= new Database();
			Connection connection = database.Get_Connection();
			Project project= new Project();
			feeds=project.GetFeeds(connection, terrorParam1, terrorParam2);
		}
		catch (Exception e) {
			throw e;
		}
		return feeds;
	}
	
	public ArrayList<FeedObjects> GetFeeds(String terrorParam3)throws Exception {
		ArrayList<FeedObjects> feeds = null;
		try {
			Database database= new Database();
			Connection connection = database.Get_Connection();
			Project project= new Project();
			feeds=project.GetFeeds1(connection, terrorParam3);
		}
		catch (Exception e) {
			throw e;
		}
		return feeds;
	}
	
	public ArrayList<FeedObjects> GetTopTen(String terrorParam1, String terrorParam2) throws Exception {
		ArrayList<FeedObjects> feeds = null;
		try {
			Database database= new Database();
			Connection connection = database.Get_Connection();
			Project project= new Project();
			feeds=project.GetTopTen(connection, terrorParam1, terrorParam2);
		}
		catch (Exception e) {
			throw e;
		}
		return feeds;
	}
	
	public ArrayList<Map<String,String>> GetAttacksByYear(String yearStart, String yearEnd, String groupName) throws Exception {
		ArrayList<Map<String,String>> listOfAttacks = null;
		try {
			Database database= new Database();
			Connection connection = database.Get_Connection();
			Project project= new Project();
			listOfAttacks = project.GetAttacksByYear(connection, yearStart, yearEnd, groupName);
		}
		catch (Exception e) {
			throw e;
		}
		return listOfAttacks;
	}
	
	public Map<String,String> iGetAttacksByYear(String yearStart, String yearEnd, String groupName) throws Exception {
		Map<String,String> listOfAttacks = null;
		try {
			Database database= new Database();
			Connection connection = database.Get_Connection();
			Project project= new Project();
			listOfAttacks = project.iGetAttacksByYear(connection, yearStart, yearEnd, groupName);
		}
		catch (Exception e) {
			throw e;
		}
		return listOfAttacks;
	}

}
