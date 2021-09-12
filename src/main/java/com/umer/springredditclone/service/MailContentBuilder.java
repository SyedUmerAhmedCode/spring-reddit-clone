package com.umer.springredditclone.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {
	

	private static final String MAIL_TEMPLATE = "mailTemplate";
	private static final String MESSAGE = "message";
	private final TemplateEngine templateEngine;
	
	String build(String message) {
		Context context=new Context();
		context.setVariable(MESSAGE, message);
		return templateEngine.process(MAIL_TEMPLATE, context); 
	}
	
	
	
}
