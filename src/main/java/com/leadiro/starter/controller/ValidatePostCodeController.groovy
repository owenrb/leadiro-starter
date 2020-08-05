package com.leadiro.starter.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import groovy.transform.CompileStatic

import com.leadiro.starter.service.ValidateService

@CrossOrigin
@RestController
@CompileStatic
public class ValidatePostCodeController {
	
	@Autowired private ValidateService service
	
	@GetMapping("/validate/postcode")
	def validate(@RequestParam("postcode") String postcode) {
		
		return service.postalCode(postcode)
	}
}
