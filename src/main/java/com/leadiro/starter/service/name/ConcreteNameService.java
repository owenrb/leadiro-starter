package com.leadiro.starter.service.name;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
	private List<String> suffixes = Arrays.asList("dip ed", "assoc prof", "certified professional");
	
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
				.takeWhile(aka -> !isAka(aka))
				.map(alpha -> trimNonAlpha(alpha)) // trim leading and trailing non-alpha
				.filter(title -> !tooShort(title))
				.filter(alpha -> isAlpha(alpha))
				.map(elem -> toTitle(elem))
				.collect(Collectors.toList());
		
		list = processCompoundSurnames(list);
		
		list = removeCompoundSuffix(list);
		
		
		int size = list.size();
		if(size < 2) {
			return null;
		}
		
		return new String[] {list.get(0), list.get(size - 1)};
	}
	
	private List<String> removeCompoundSuffix(List<String> list) {
		int size = list.size();
		if(size < 2) {
			return list;
		}
		
		List<String> newList = new ArrayList<>(list);
		for(int k = size - 2; k >= 0; k--) {
			String combined = String.format("%s %s", newList.get(k), newList.get(k+1)).toLowerCase();
			if(suffixes.contains(combined)) {
				// remove elements if found
				newList.remove(k+1);
				newList.remove(k);
				
				k--; // adjust the pointer
			}
		}
		
		
		return newList;
	}
	
	private String trimNonAlpha(String alpha) {
		
		List<Character> chars = alpha.chars().mapToObj(e->(char)e).collect(Collectors.toList());
		
		// check leading
		int idx1 = (int) chars.stream().takeWhile(ch -> !Character.isLetter(ch)).count();
		
		if(idx1 == alpha.length()) {
			// entire string is non-alpha
			return alpha;
		}
		
		// reverse to check trailing
		Collections.reverse(chars);
		int idx2 = (int) chars.stream().takeWhile(ch -> !Character.isLetter(ch)).count();
		
		String trimed = alpha.substring(idx1, alpha.length() - idx2);
		
		if(idx1 + idx2 > 0) {
			log.debug("trimed from {} to {}", alpha, trimed);
		}
		
		return trimed;
		
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
	private String toTitle(String title) {
		
		StringBuilder sb = new StringBuilder();
	
		String name;
		
		// check for special case
		if(title.length() > 3 && title.startsWith("de")
				&& Character.isUpperCase(title.charAt(2))) {
			
			sb.append("de");
			name = title.substring(2);
		} else {
			name = title;
		}
		
		
		sb.append(Character.toUpperCase(name.charAt(0)));
		sb.append(name.substring(1).toLowerCase());
		
		return sb.toString();
		
	}

}
