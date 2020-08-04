package com.leadiro.starter.service.museum.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.leadiro.starter.service.museum.dto.IdTitle;
import com.leadiro.starter.service.museum.dto.KeywordParam;


public interface MuseumDao {

	@Cacheable("keywords")
	List<IdTitle> search(KeywordParam keywords);
	
	@Cacheable("ids")
	Optional<String> getById(String id);
}
