package com.leadiro.starter.service.museum.repository;

import java.util.function.Predicate;

import com.fasterxml.jackson.databind.JsonNode;

public class SearchPredicate implements Predicate<JsonNode>{

	@Override
	public boolean test(JsonNode t) {
		
		return false;
	}

}
