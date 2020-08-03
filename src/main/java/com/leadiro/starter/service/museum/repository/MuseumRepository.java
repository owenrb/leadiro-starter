package com.leadiro.starter.service.museum.repository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadiro.starter.service.museum.dao.MuseumDao;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MuseumRepository implements MuseumDao {


	@Value("${url.sei.museum:https://collections.museumsvictoria.com.au/api/search}")
	private String museumUrl;
	
	private JsonNode datasource;
	
	@PostConstruct
	protected void initData() {
		
		// achieve preloaded data
		String raw = fetchRemoteResource();
		
		datasource = processInput(raw);
		
	}
	
	@Override
	public String[] search(String... keywords) {
		
		if(datasource == null) {
			return null;
		}
			
		return null;
	}

	@Override
	public String getById(String id) {
		return null;
	}
	
	/**
	 * This is where we are going to get the data input
	 * @return
	 */
	private String fetchRemoteResource() {

		RestTemplate restTemplate = new RestTemplate();
		
		String data = "";
		
		try {
			ResponseEntity<String> response = restTemplate.getForEntity(museumUrl, String.class);
			
			data = response.getBody();
			
			
		} catch (Exception e) {
			String reason = String.format("[%s] %s", e.getClass().getSimpleName(), e.getMessage());
			log.error(reason, e);
		}
		
		return data;
	}
	
	
	private JsonNode processInput(String raw) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(raw);
			
		} catch (JsonProcessingException e) {
			String reason = "Error processing raw input";
			log.error(reason, e);
			return null;
		}
	}

}
