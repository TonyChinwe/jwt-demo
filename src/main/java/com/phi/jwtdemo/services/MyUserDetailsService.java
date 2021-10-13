package com.phi.jwtdemo.services;

import com.phi.jwtdemo.entities.User;
import com.phi.jwtdemo.models.MyUserDetails;
import com.phi.jwtdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.getUserByUsername(s);
        System.out.println("Anyi 1 "+user.getUsername());

        if(user==null){
            System.out.println("Anyi 2");

            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("Anyi 3");

        MyUserDetails myUserDetails=new MyUserDetails(user);
        System.out.println(myUserDetails.getUsername());
        System.out.println(myUserDetails.getPassword());
        System.out.println(myUserDetails.getAuthorities());

        return new MyUserDetails(user);
    }
}
