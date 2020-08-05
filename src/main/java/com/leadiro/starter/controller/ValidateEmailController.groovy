package com.leadiro.starter.controller

import javax.validation.constraints.Email

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin

import groovy.transform.CompileStatic

@CrossOrigin
@RestController
@CompileStatic
@Validated
class ValidateEmailController {


	@GetMapping('/validate/email')
	public void validate(@RequestParam('email') @Email String email) {
	}
}
