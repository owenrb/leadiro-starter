package com.leadiro.starter.service;

import java.util.List;

import com.leadiro.starter.service.museum.dto.IdTitle;

public interface MuseumService {
	
	String NOT_FOUND_PAYLOAD = "{'error':'Not found'}";

	List<IdTitle> search(String keyword);
	
	String getById(String id);
}
