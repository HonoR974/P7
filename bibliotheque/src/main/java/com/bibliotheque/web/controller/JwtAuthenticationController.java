package com.bibliotheque.web.controller;


import com.bibliotheque.configuration.JwtTokenUtil;
import com.bibliotheque.dto.UserDTO;
import com.bibliotheque.model.JwtRequest;
import com.bibliotheque.model.JwtResponse;
import com.bibliotheque.configuration.JwtUserDetailsService;
import com.bibliotheque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class JwtAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private JwtUserDetailsService userDetailsService;



    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        System.out.println("\n \n test  \n "  );

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }



    private void authenticate(String username, String password) throws Exception {
        System.out.println("\n \n "+ username+ " \n "  + password );
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        System.out.println("\n \n Hello \n "  );

        return ResponseEntity.ok(userService.save(user));
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByToken(@RequestParam String jwt)
    {

        System.out.println("\n \n test \n " + jwt);
        String username = "0" ;
        try {
            username = jwtTokenUtil.getUsernameFromToken(jwt);
            System.out.println("\n "+ username  +" \n " );

        } catch (Exception e )
        {
            System.out.println("\n error \n " );
        }

        return new ResponseEntity<String>(username,HttpStatus.ACCEPTED);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test()
    {
        String test = "test";
        return new ResponseEntity<String>(test, HttpStatus.ACCEPTED);
    }


}
