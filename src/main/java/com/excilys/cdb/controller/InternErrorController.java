package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ErrorController")
public class InternErrorController{
	
	@PostMapping
	public void postErrorController(ModelMap modelMap){
		getErrorController(modelMap);
	}
	
	@GetMapping
	public String getErrorController(ModelMap modelMap){
		return "500";
	}

}
