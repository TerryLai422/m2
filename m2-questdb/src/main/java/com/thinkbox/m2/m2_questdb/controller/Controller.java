package com.thinkbox.m2.m2_questdb.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class Controller {
	@GetMapping
    // Method 
    public String helloGFG() 
    { 
        return "Hello GeeksForGeeks"; 
    } 
}
