package com.leadiro.starter.service.starter

import com.leadiro.starter.service.StarterService
import com.leadiro.starter.service.starter.dto.Starter
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@CompileStatic
@SpringBootTest
class StarterServiceTest {
    @Autowired private StarterService service

    /**
     * an example test - this will presently fail
     */
    @Test
    void getStarter() {
        Starter starter = service.getStarter(1)
        Assertions.assertNotNull(starter, "Starter is null")
    }


}
