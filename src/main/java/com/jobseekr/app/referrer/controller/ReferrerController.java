package com.jobseekr.app.referrer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.auth.security.jwt.JwtUtils;
import com.jobseekr.app.referrer.entity.Referrer;
import com.jobseekr.app.referrer.service.ReferrerService;

import jakarta.mail.MessagingException;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/referrers")
public class ReferrerController {
	@Autowired
	private JwtUtils jwt;

    @Autowired
    private ReferrerService referrerService;

    @PostMapping
    public ResponseEntity<Referrer> createReferrer(@RequestBody Referrer referrer,@RequestHeader("Authorization") String authorizationHeader) throws MessagingException {
    	String token = authorizationHeader.substring(7);
    	String email = jwt.getUserNameFromJwtToken(token);

    	Referrer createdReferrer = referrerService.createReferrer(referrer, email);
        return ResponseEntity.ok(createdReferrer);
        
    }
}
	