package com.leadiro.starter.service.museum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Association {
	private String type;
	private String name;
	private String date;
	private String comments;
	private String streetAddress;
	private String locality;
	private String region;
	private String state;
	private String country;
}
