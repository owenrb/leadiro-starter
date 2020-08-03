package com.leadiro.starter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidatePostCodeController {

    @GetMapping("/validate/postcode")
	public String validate(@RequestParam("postcode") String postcode) {
    	
    	return "";
	}
}
