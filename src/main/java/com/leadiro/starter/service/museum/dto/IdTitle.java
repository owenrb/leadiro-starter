package com.leadiro.starter.service.museum.dto;

import lombok.Data;

@Data
public class IdTitle {
	
	private String id;
	private String title;

	public IdTitle() {
		
	}
	
	public IdTitle(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	@Override
	public String toString() {
		return String.format("{id: '%s', title: '%s'}", id, title);
	}
}
