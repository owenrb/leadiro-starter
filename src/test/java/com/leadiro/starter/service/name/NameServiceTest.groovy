package com.leadiro.starter.service.name

import com.leadiro.starter.service.NameService
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
class NameServiceTest {
    @Autowired private NameService name
	
	@Test
	public void simple() {
		Assert.assertArrayEquals("Leadiro User", new String[] {"Leadiro", "User"}, name.process("Leadiro User"))
		Assert.assertArrayEquals("User, Leadiro", new String[] {"Leadiro", "User"}, name.process("User, Leadiro"))
		Assert.assertArrayEquals("leadiro     User", new String[] {"Leadiro", "User"}, name.process(" leadiro     User "))
	}
	
	@Test
	public void surname() {
		Assert.assertArrayEquals("Leadiro John Del User", new String[] {"Leadiro", "Del User"}, name.process("Leadiro John Del User"));
	}

}
