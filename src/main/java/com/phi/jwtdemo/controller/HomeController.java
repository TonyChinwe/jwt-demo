package com.phi.jwtdemo.controller;
import com.phi.jwtdemo.entities.User;
import com.phi.jwtdemo.models.AuthenticationRequest;
import com.phi.jwtdemo.models.AuthenticationResponse;
import com.phi.jwtdemo.repository.UserRepository;
import com.phi.jwtdemo.services.MyUserDetailsService;
import com.phi.jwtdemo.services.UserRepoService;
import com.phi.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private MyUserDetailsService userDetailsService;
@Autowired
private UserRepoService userRepoService;
@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private JwtUtil jwtTokenUtil;
@GetMapping("/hello")
public String hello(){
    return "Hello world";
}

@PostMapping("/register")
public  ResponseEntity<User> createUser(@RequestBody User user){
if(user.getPassword() == null||user.getPassword().isEmpty()){
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
}else if(user.getUsername()==null||user.getUsername().isEmpty()){
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

}else {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepoService.createUser(user);
    return new ResponseEntity<>(user,HttpStatus.CREATED);
}

}

@PostMapping("/login")
public ResponseEntity<?>createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
  try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

  }catch (BadCredentialsException e){
     return new  ResponseEntity<>(e,HttpStatus.NOT_FOUND);
  }

  final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
  final String jwt=jwtTokenUtil.generateToken(userDetails);
  return  ResponseEntity.ok(new AuthenticationResponse(jwt));
}



}