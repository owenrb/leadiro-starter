package com.leadiro.starter.service.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.leadiro.starter.service.ValidateService;
import com.leadiro.starter.service.validation.dto.PostalDetail;
import com.leadiro.starter.service.validation.dto.ValidationStatus;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConcreteValidateService implements ValidateService{
	
	@Value("${url.sei.postcodes:https://postcodes.io/postcodes}")
	private String postCodeUrl;
	
	
	@Override
	public ValidationStatus<String> email(String email) {
		return null;
	}

	@Override
	public ValidationStatus<PostalDetail> postalCode(String postalCode) {
		
		ValidationStatus<PostalDetail> status = new ValidationStatus<>();
		
		// request
		String request =  postCodeUrl + "/" + postalCode;
		log.debug("Request: " + request);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response;
		
		try {
			response = restTemplate.getForEntity(request, String.class);
		} catch (Exception e) {
			String reason = String.format("[%s] %s", e.getClass().getSimpleName(), e.getMessage());
			log.error(reason, e);
			status.setValid(false);
			status.setReason(reason);
			return status;
		}
		
		// process response
		log.debug("Response: " + response);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		try {
			root = mapper.readTree(response.getBody());
		} catch (JsonProcessingException e) {
			String reason = "Error processing response";
			log.error(reason, e);
			status.setValid(false);
			return status;
		}
		
		JsonNode result = root.path("result");
		if(result != null) {
			
			try {
				PostalDetail postalDetail = mapper.readValue(new TreeTraversingParser(result), PostalDetail.class);
				
				status.setDetail(postalDetail);
				status.setValid(true);
				status.setReason("Success");

			} catch (Exception e) {
				String reason = "Error retrieving postal detail"; 
				log.error(reason, e);
				status.setValid(false);
				status.setReason(reason);
			}
			
		} else {
			status.setValid(false);
			status.setReason("Empty payload");
			
		}
		
		return status;
	}

}
