package com.leadiro.starter.service;

import java.util.List;

import com.leadiro.starter.service.museum.dto.IdTitle;

public interface MuseumService {

	List<IdTitle> search(String keyword);
	
	String getById(String id);
}
