package com.leadiro.starter.service.validation

import com.leadiro.starter.service.ValidateService
import com.leadiro.starter.service.validation.dto.PostalDetail
import com.leadiro.starter.service.validation.dto.ValidationStatus
import groovy.transform.CompileStatic
import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import com.leadiro.starter.Application

@CompileStatic
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class ValidateServiceTest {
    @Autowired private ValidateService service

    /**
     * valid test
     */
    @Test
    void testPostCode() {
        def status = service.postalCode("OX49 5NU")
        Assert.assertNotNull(status)
        Assert.assertTrue(status.isValid())
    }

	/**
	 * Invalid postcode test
	 */
    @Test
    void testInvalidPostCode() {
        def status = service.postalCode("OX49 5NU, OX49 5NU")
        Assert.assertNotNull(status)
        Assert.assertNotNull(status.getReason())
        Assert.assertFalse(status.isValid())
    }

}
