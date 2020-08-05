package com.leadiro.starter.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import com.leadiro.starter.service.MuseumService

import groovy.transform.CompileStatic

@CrossOrigin
@RestController
@CompileStatic
class MuseumController {
	@Autowired private MuseumService service;

	@GetMapping('/museum')
	def search(@RequestParam("keywords") String keywords) {
		
		return service.search(keywords)
		
	}
	
	
	@GetMapping(path='/museum/{id}', produces= ['application/json'])
	def getById(@PathVariable String id) {
		
		return service.getById(id)
		
	}
	
	@GetMapping(path='/museum/{item}/{id}', produces= ['application/json'])
	def getByIdHack(@PathVariable String item, @PathVariable String id) {
		
		return service.getById(item + "/" + id)
		
	}

}