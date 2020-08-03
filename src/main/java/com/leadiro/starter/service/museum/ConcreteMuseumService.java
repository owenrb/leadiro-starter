package com.leadiro.starter.service.museum;

import org.springframework.stereotype.Service;

import com.leadiro.starter.service.MuseumService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteMuseumService implements MuseumService {
	

	@Override
	public String search(String keyword) {
		log.debug("Searching for {} keyword...", keyword);
		return null;
	}

	@Override
	public String getById(String id) {
		log.debug("Retrievingg record id: {} ...", id);
		return null;
	}

}
