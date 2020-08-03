package com.leadiro.starter.service.museum

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import com.leadiro.starter.Application
import com.leadiro.starter.service.MuseumService

import groovy.transform.CompileStatic

import com.leadiro.starter.service.museum.dto.IdTitle
import java.util.List

import org.junit.Assert

@CompileStatic
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
class MuseumServiceTest {
	
	@Autowired private MuseumService service
	
	@Test
	public void search() {
		
		List<IdTitle>  actual = service.search("Melbourne")
		
		Assert.assertNotNull(actual);
		Assert.assertFalse(actual.isEmpty());
	}
	
	@Test
	public void searchNotFound() {
		
		List<IdTitle>  actual = service.search("asdf23r12as")
		
		Assert.assertNotNull(actual);
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void searchById() {
		
		String  actual = service.getById("items/1238259")
		
		Assert.assertNotNull(actual);
		Assert.assertFalse(actual.isEmpty());
		
		Assert.assertNotEquals("Not found", actual)
	}
	
	@Test
	public void searchByIdNotFound() {
		
		String  actual = service.getById("items/xxxxx")
		
		Assert.assertNotNull(actual);
		Assert.assertFalse(actual.isEmpty());
		
		Assert.assertEquals("Not found", actual)
	}
	
}
