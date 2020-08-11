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
	
	private List<String> preSurnames = Arrays.asList("Del");
	private List<String> titles = Arrays.asList("csar", "dr", "rev", "mba", "bsc" , "ii", "jr", "msc", "mph", "diped", "maca", "phd");
	
	@Override
	public String[] process(String name) {
		
		log.debug("Processing name {}...", name);
		
		if(name == null) {
			return null;
		}
		
		// search for comma
		int idx = name.indexOf(",");
		if(idx >= 0) {
			String left = idx == 0? "" : name.substring(0, idx);
			String right = name.substring(idx+1);
			
			return process(right + " " + left);
		}
		
		
		List<String> list = Stream.of(name.trim().split("\\s+"))
				.filter(title -> !isSalutation(title))
				.filter(title -> !hasEnclosings(title))
				.filter(title -> !tooShort(title))
				.takeWhile(aka -> !isAka(aka))
				.filter(alpha -> isAlpha(alpha))
				.map(elem -> toTitle(elem))
				.collect(Collectors.toList());
		
		list = processCompoundSurnames(list);
		
		
		int size = list.size();
		if(size < 2) {
			return null;
		}
		
		return new String[] {list.get(0), list.get(size - 1)};
	}
	
	private boolean isAlpha(String alpha) {
		return alpha.matches("[a-zA-Z]+");
	}

	private boolean tooShort(String title) {
		
		return title.replaceAll("\\.", "").length() < 2;
	}

	private boolean isAka(String aka) {
		
		return "aka".equalsIgnoreCase(aka.replaceAll("\\.", ""));
	}

	private boolean hasEnclosings(String name) {
		return isEnclosing(name, '\"')
				|| isEnclosing(name, '\'')
				|| isEnclosing(name, '(', ')');
	}
	
	private boolean isEnclosing(String name, char first) {
		return isEnclosing(name, first, first);
	}
	
	private boolean isEnclosing(String name, char first, char last) {
		int len = name.length();
		return name.charAt(0) == first && name.charAt(len - 1) == last;
	}

	private boolean isSalutation(String title) {
		String key = title.replaceAll("\\.", "").toLowerCase();
		return titles.contains(key);
	}
	
	private List<String> processCompoundSurnames(List<String> list) {
		
		int size = list.size();
		if(size > 1) {
			String subj = list.get(size - 2);
			if(preSurnames.contains(subj)) {
				String last = list.get(size - 1);
				
				List<String> newList = list.subList(0, size-2); // split
				newList.add(subj + " " + last);					// and combine
				
				return processCompoundSurnames(newList);	// recursion here
				
			}
		}
		
		return list;
	}

	/**
	 * Convert non-empty string to title format
	 * @param name
	 * @return
	 */
	private String toTitle(String name) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toUpperCase(name.charAt(0)));
		sb.append(name.substring(1).toLowerCase());
		
		return sb.toString();
		
	}

}
