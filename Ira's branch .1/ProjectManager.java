package model;

import java.sql.Connection;
import java.util.ArrayList;

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
	
	

}
