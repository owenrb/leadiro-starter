package com.leadiro.starter.controller

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@CompileStatic
@SpringBootTest
class HealthControllerTest {
    @Autowired private HealthController controller

    @Test
    void health() {
        def health = controller.healthy()
        Assertions.assertNotNull(health, 'Health is null')
        println(health)
    }

}
