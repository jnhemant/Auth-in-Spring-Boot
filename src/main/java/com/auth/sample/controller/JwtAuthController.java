package com.auth.sample.controller;

import com.auth.sample.config.JwtTokenUtil;
import com.auth.sample.model.JwtRequest;
import com.auth.sample.model.JwtResponse;
import com.auth.sample.model.UserAuth;
import com.auth.sample.service.JwtUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(JwtAuthController.class);

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserAuth user) throws Exception {
        log.debug("Initiating registration of new user with username " + user.getUsername());
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @GetMapping(value = "/home")
    public ResponseEntity<String> getHome(){
        return ResponseEntity.ok("Public API");
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<String> getProfile(){
        return ResponseEntity.ok("User Profile");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.debug("Request by user with username " + username + " authenticated.");
        } catch (DisabledException e) {
            log.error("DisabledEcception occurred with authenticating user", e);
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.error("Invalid credentials provided by user", e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


}