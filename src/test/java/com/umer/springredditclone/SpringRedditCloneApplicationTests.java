package com.umer.springredditclone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.umer.springredditclone.controller.AuthController;

@SpringBootTest
class SpringRedditCloneApplicationTests {
	@Autowired
	private AuthController authController;
	

	@Test
	void contextLoads() {
		authController.healthCheck();
		
	}
	
	@Test
	public void testHealthCheck() {
		authController.healthCheck();
	}

}
