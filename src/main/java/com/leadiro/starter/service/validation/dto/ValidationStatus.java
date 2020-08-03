package com.leadiro.starter.service.validation.dto;

import lombok.Data;

@Data
public class ValidationStatus<T> {

	private boolean valid;
	private String reason;
	private T detail;
	
}
