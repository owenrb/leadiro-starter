package com.leadiro.starter.service.museum.dao


public interface MuseumDao {

	String[] search(String ... keywords)
	
	String getById(String id);
}