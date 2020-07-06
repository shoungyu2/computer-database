package com.excilys.cdb.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("BadRequestController")
public class BadRequestController{
	
	@PostMapping
	public String postBadRequestController(ModelMap modelMap) throws ServletException, IOException {
		
		return getBadRequestController(modelMap);
		
	}
	
	@GetMapping
	public String getBadRequestController(ModelMap modelMap) throws ServletException, IOException {
		
		return "400";
		
	}

}
