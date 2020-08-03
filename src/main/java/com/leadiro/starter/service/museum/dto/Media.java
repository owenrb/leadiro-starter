package com.leadiro.starter.service.museum.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {
	private String type;
	private String alternativeText;
	private MediaResource large;
	private MediaResource medium;
	private MediaResource small;
	private MediaResource thumbnail;
	private String id;
	private String dateModified;
	private String caption;
	private String[] creators;
	private String[] sources;
	private String credit;
	private String rightsStatement;
	private Licence licence;
}
