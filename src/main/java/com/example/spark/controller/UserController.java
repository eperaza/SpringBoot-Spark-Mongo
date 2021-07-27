package com.example.spark.controller;

import java.util.List;
import com.example.spark.model.User;
import com.example.spark.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="/api")
@Component
public class UserController {

    @Autowired
    IUserService userService;
    
    @PostMapping(path="/add")
    public ResponseEntity<Object> addUsers(){
		String response = userService.addUsers();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    @GetMapping(path="/findall")
    public ResponseEntity<Object> findAll(){
		String response = userService.findAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
}
