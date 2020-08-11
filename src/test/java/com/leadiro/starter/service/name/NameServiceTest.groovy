package com.leadiro.starter.service.name

import com.leadiro.starter.service.NameService
import groovy.transform.CompileStatic
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import com.leadiro.starter.Application

@CompileStatic
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@Ignore
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
	
	@Test
	public void remove() {
		Assert.assertArrayEquals("Csar Leadiro User", new String[] {"Leadiro", "User"}, name.process("Csar Leadiro User"));
		Assert.assertArrayEquals("Dr Leadiro User", new String[] {"Leadiro", "User"}, name.process("Dr Leadiro User"));
		Assert.assertArrayEquals("D.R. Leadiro User", new String[] {"Leadiro", "User"}, name.process("D.R. Leadiro User"));
		Assert.assertArrayEquals("Rev. Leadiro User", new String[] {"Leadiro", "User"}, name.process("Rev. Leadiro User"));
		Assert.assertArrayEquals("Leadiro (John) User", new String[] {"Leadiro", "User"}, name.process("Leadiro (John) User"));
		Assert.assertArrayEquals("Leadiro \"Rambo\" User", new String[] {"Leadiro", "User"}, name.process("Leadiro \"Rambo\" User"));
		Assert.assertArrayEquals("Leadiro 'Rambo' User", new String[] {"Leadiro", "User"}, name.process("Leadiro 'Rambo' User"));
		Assert.assertArrayEquals("Leadiro User a.k.a The Terminator", new String[] {"Leadiro", "User"}, name.process("Leadiro User a.k.a The Terminator"));
		Assert.assertArrayEquals("Leadiro User M.B.A.", new String[] {"Leadiro", "User"}, name.process("Leadiro User M.BA."));
		Assert.assertArrayEquals("Leadiro J. R. User", new String[] {"Leadiro", "User"}, name.process("Leadiro J. R. User"));
		Assert.assertArrayEquals("Leadiro User, Bsc", new String[] {"Leadiro", "User"}, name.process("Leadiro User, Bsc"));
		Assert.assertArrayEquals("Leadiro User - Bsc", new String[] {"Leadiro", "User"}, name.process("Leadiro User - Bsc"));
		Assert.assertArrayEquals("Leadiro User | Bsc", new String[] {"Leadiro", "User"}, name.process("Leadiro User | Bsc"));
		Assert.assertArrayEquals("~~~ Leadiro User ~~~", new String[] {"Leadiro", "User"}, name.process("~~~ Leadiro User ~~~"));
		//Assert.assertArrayEquals("Leadiro User Certified Professional", new String[] {"Leadiro", "User"}, name.process("Leadiro User Certified Professional"));
		Assert.assertArrayEquals("Leadiro User 99", new String[] {"Leadiro", "User"}, name.process("Leadiro User 99"));
		
	}
	
	@Test
	public void replace() {
		Assert.assertArrayEquals("Leadiro User II.", new String[]{"Leadiro", "User"}, name.process("Leadiro User II."));
		Assert.assertArrayEquals("Leadiro User Jr.", new String[]{"Leadiro", "User"}, name.process("Leadiro User Jr."));
	}
	
	
	@Test
	public void suffix() {
		//Assert.assertArrayEquals("Leadiro User Dip Ed", new String[]{"Leadiro", "User"}, name.process("Leadiro User Dip Ed"));
		Assert.assertArrayEquals("Leadiro User DipEd", new String[]{"Leadiro", "User"}, name.process("Leadiro User DipEd"));
		Assert.assertArrayEquals("Leadiro R User MSc MPH DRes/PhD", new String[]{"Leadiro", "User"}, name.process("Leadiro R User MSc MPH DRes/PhD"));
		Assert.assertArrayEquals("Leadiro User Phd", new String[]{"Leadiro", "User"}, name.process("Leadiro User Phd"));
		Assert.assertArrayEquals("Leadiro User MacA", new String[]{"Leadiro", "User"}, name.process("Leadiro User MacA"));
		//Assert.assertArrayEquals("Leadiro User assoc prof", new String[]{"Leadiro", "User"}, name.process("Leadiro User assoc prof"));
	}

	@Test
	public void badNames() {
		Assert.assertNull("Harendra 8866605136", name.process("Harendra 8866605136"));
		Assert.assertNull(".. ..", name.process(".. .."));
		Assert.assertNull(" ..", name.process("*N* *O*"));
		Assert.assertArrayEquals("~~~ Leadiro User ~~~", new String[]{"Leadiro", "User"}, name.process("~~~ Leadiro User ~~~"));
		Assert.assertArrayEquals("~~~ Leadiro J User ~~~", new String[]{"Leadiro", "User"}, name.process("~~~ Leadiro J User ~~~"));
	}

	@Test
	public void capitalisation() {
		Assert.assertArrayEquals("HEMANT AHIRKAR", new String[]{"Hemant", "Ahirkar"}, name.process("HEMANT AHIRKAR"));
		Assert.assertArrayEquals("hemant ahirkar", new String[]{"Hemant", "Ahirkar"}, name.process("hemant ahirkar"));
		//Assert.assertArrayEquals("Hemant deAhirkar", new String[]{"Hemant", "deAhirkar"}, name.process("Hemant deAhirkar"));
	}

	@Test
	public void nonAlpha() {
		//Assert.assertArrayEquals(new String[]{"Hemant", "Ahirkar"}, name.process("'HEMANT AHIRKAR'"));
		//Assert.assertArrayEquals(new String[]{"Hemant", "Ahirkar"}, name.process("-hemant ahirkar"));
	}

	@Test
	public void invalid() {
		Assert.assertNull(name.process("'Leadiro"));
		Assert.assertNull(name.process("'Leadir O'"));
		Assert.assertNull(name.process("'L Eadiro'"));
		Assert.assertNull(name.process("'L. E. Adiro'"));
	}

}
