package com.sauja.springmvc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class HelloWorldController {

	@RequestMapping(method = RequestMethod.GET)
	public String sayHello(ModelMap model) {
		model.addAttribute("greeting", "Hello World from Spring 4 MVC");
		return "index";
	}

	@RequestMapping(value = "customLogin",method = RequestMethod.GET)
	public String getCustomLoginPage(ModelMap model) {
		return "customLogin";
	}

	@RequestMapping(value = "securedResource", method = RequestMethod.GET)
	public String sayHelloAgain(ModelMap model) {
		System.out.println("abc");

		model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
		return "authenticatedUser";
	}
	@RequestMapping(value="logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        System.out.println("User logout");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/customLogin";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}

    @RequestMapping(value = "403",method = RequestMethod.GET)
    public String get403Page(ModelMap model) {
        return "403";
    }

    @RequestMapping(value = "admin",method = RequestMethod.GET)
    public String getAdminPage(ModelMap model) {
        return "admin";
    }
}