package com.leadiro.starter.service.museum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadiro.starter.service.MuseumService;
import com.leadiro.starter.service.museum.dao.MuseumDao;
import com.leadiro.starter.service.museum.dto.IdTitle;
import com.leadiro.starter.service.museum.dto.KeywordParam;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteMuseumService implements MuseumService {
	
	@Autowired
	private MuseumDao museumDao;
	

	@Override
	public List<IdTitle> search(String keyword) {
		log.debug("Searching for {} keyword...", keyword);
		
		if(keyword == null || keyword.trim().isEmpty()) {
			return null;
		}
		
		KeywordParam param = new KeywordParam(keyword);
		log.debug("param: " + param.getKeywords());
		
		
		return museumDao.search(param);
	}

	@Override
	public String getById(String id) {
		log.debug("Retrieving record id: {} ...", id);
		return museumDao.getById(id).orElse("Not found");
	}

}
