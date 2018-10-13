package com.shadow;

import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShadowRestApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void givenUsingPlainJava_whenGeneratingRandomStringUnbounded_thenCorrect() {
	    String generatedString = RandomStringUtils.randomAlphabetic(8);
	 
	    System.out.println("generatedString" + generatedString);
	}

}
