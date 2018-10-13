package com.shadow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ShadowRestApplication {
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}


	public static void main(String[] args) throws JAXBException, IOException {
		SpringApplication.run(ShadowRestApplication.class, args);
		
		
		 	File file = new File("sample.xml");
	        JAXBContext jaxbContext = JAXBContext.newInstance(HttpApiResponse.class);
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        
	        String content = new String(Files.readAllBytes(Paths.get("sample.xml")));
	        
	        System.out.println("content " + content);
	        
	       StringReader reader = new StringReader(content);
	        
	        HttpApiResponse httpApiResponse = (HttpApiResponse) unmarshaller.unmarshal(reader);
	        System.out.println(httpApiResponse.getDescription());
	}
}
