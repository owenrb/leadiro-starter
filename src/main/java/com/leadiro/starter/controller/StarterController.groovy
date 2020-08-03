package com.leadiro.starter.controller

import com.leadiro.starter.service.StarterService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@CompileStatic
class StarterController {
    @Autowired private StarterService service;

    @GetMapping('/starter/{id}')
    def getTemplateSummary(@PathVariable int id) {
        return [
            starter: service.getStarter(id)
        ]
    }

}
