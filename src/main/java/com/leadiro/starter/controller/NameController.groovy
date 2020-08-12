package com.leadiro.starter.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import java.util.stream.Collectors

import org.springframework.beans.factory.annotation.Autowired

import com.leadiro.starter.service.NameService


import groovy.transform.CompileStatic

@CrossOrigin
@RestController
@CompileStatic
class NameController {
	
	@Autowired private NameService service;
	
	
	@PutMapping('/parse/name')
	public List<String[]> clean(@RequestBody List<String> names) {
		
		return names.stream()
			.map(name -> service.process(name)) // call service to process names
			.filter(array -> array != null)		// do not include null in the result
			.collect(Collectors.toList())		// return list of array
	}
	
}