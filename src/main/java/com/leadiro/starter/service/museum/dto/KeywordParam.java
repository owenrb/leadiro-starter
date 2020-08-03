package com.leadiro.starter.service.museum.dto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeywordParam {
	
	private List<String> keywords;
	
	public KeywordParam(String keyword) {
		
		keywords = Stream.of(keyword.split(","))
			.filter(k -> !k.trim().isEmpty())	// don't include empty string
			.map(k -> k.trim().toLowerCase())	// trim and make lower-case
			.sorted(Comparator.naturalOrder())	// sort them, for comparison with other object
			.collect(Collectors.toUnmodifiableList());
	}
	
	public List<String> getKeywords() {
		return keywords;
	}
	
	public boolean check(String test) {
		return !keywords.stream()
		.filter(search -> test.toLowerCase().indexOf(search) > -1)
		.collect(Collectors.toList()).isEmpty();
	}
	
	@Override
	public int hashCode() {
		return keywords.isEmpty() ? 0 : keywords.get(0).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof KeywordParam) {
			
			KeywordParam that = (KeywordParam) obj;
			
			return this.keywords != null && that.keywords != null 
					&& this.keywords.toString().equals(that.keywords.toString());
		}
		
		return false;
	}

}
