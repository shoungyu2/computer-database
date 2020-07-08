package com.excilys.cdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("LogInController")
public class LogInController {

	@GetMapping
	public String getLogInController(ModelMap modelMap) {
		
		return "index";
		
	}
	
	@PostMapping
	public String postLogInController(ModelMap modelMap) {
		
		modelMap.addAttribute("success", "fdklfh");
		return getLogInController(modelMap);
		
	}
	
}
