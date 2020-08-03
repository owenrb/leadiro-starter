package com.leadiro.starter.service.museum.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadiro.starter.service.museum.dao.MuseumDao;
import com.leadiro.starter.service.museum.dto.IdTitle;
import com.leadiro.starter.service.museum.dto.KeywordParam;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MuseumRepository implements MuseumDao 
{


	@Value("${url.sei.museum:https://collections.museumsvictoria.com.au/api/search}")
	private String museumUrl;
	
	private JsonNode datasource;
	
	@PostConstruct
	protected void initData() {
		
		// achieve pre-loaded data
		String raw = fetchRemoteResource();
		
		log.debug("raw {}", raw);
		
		datasource = processInput(raw);
		
	}
	
	
	@Override
	public List<IdTitle> search(KeywordParam keywords) {
		
		if(datasource == null) {
			return null;
		}
	
		
		return StreamSupport.stream(
				datasource.spliterator(), false)
			    .filter(node -> node.has("keywords"))
				.filter(node -> keywordSearch(node.get("keywords"), keywords))
				.map(found -> new IdTitle(found.get("id").asText(), found.get("displayTitle").asText()))
				.collect(Collectors.toList());
		
	}

	@Override
	public Optional<String> getById(String id) {
		
		return StreamSupport.stream(
				datasource.spliterator(), false)
			    .filter(node -> node.has("id") && id.equals(node.get("id").textValue()))
				.map(found -> asString(found)).findFirst();
		
	}
	
	private static String asString(JsonNode node) {
		try {
			return new ObjectMapper().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			log.error("JSON parsing error", e);
			return "{'status':'error', 'reason':'JSON parsing error'}";
		}
	}
	
	private static boolean keywordSearch(JsonNode node, KeywordParam keywords) {
		
		
		return StreamSupport.stream(node.spliterator(), false)
		.filter(keyword -> {log.debug("keyword: {}", keyword.asText()); return true;})
		.filter(keyword -> keywords.check(keyword.asText())).count() > 0;
		
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
