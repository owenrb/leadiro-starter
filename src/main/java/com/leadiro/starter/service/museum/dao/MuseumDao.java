package com.leadiro.starter.service.museum.dao;

import java.util.List;
import java.util.Optional;

import com.leadiro.starter.service.museum.dto.IdTitle;
import com.leadiro.starter.service.museum.dto.KeywordParam;


public interface MuseumDao {

	List<IdTitle> search(KeywordParam keywords);
	
	Optional<String> getById(String id);
}
