package com.prueba.jsr.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class ApiApplicationTests {

	@InjectMocks
	ApiApplication aplication;

	@Before
	public void setUpTest(){ initMocks(this); }

	@Test
	public void checkInstance(){ assertNotNull(aplication); }

	@Test
	public void checkNewInstance(){ assertNotNull(new ApiApplication()); }

	@Test(expected = Exception.class)
	public void checkInstanceError(){
		ApiApplication.main(null);
	}

}
