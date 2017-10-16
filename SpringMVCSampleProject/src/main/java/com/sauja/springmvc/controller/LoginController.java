package com.sauja.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @RequestMapping(value = "/customLogin", method = RequestMethod.POST)
    public String performLogin(@RequestAttribute String username,@RequestAttribute String password)
    {
        System.out.println(username);
        System.out.println(password);
        return "authenticatedUser";
    }
}
