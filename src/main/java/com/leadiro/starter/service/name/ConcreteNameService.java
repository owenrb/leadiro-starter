package com.leadiro.starter.service.name;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.leadiro.starter.service.NameService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteNameService implements NameService {
	
	private List<String> preSurnames = Arrays.asList("del");
	private List<String> titles = Arrays.asList("csar", "dr", "rev", "aka", "mba", "bsc" );
	
	@Override
	public String[] process(String name) {
		
		log.debug("Processing name {}...", name);
		
		if(name == null) {
			return null;
		}
		
		
		List<String> list = Stream.of(name.split("\\s+"))
				.map(elem -> new String(elem))
				.collect(Collectors.toList());
		
		
		return list.toArray(new String[] {});
	}

}
