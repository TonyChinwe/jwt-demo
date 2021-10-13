package com.phi.jwtdemo.repository;

import com.phi.jwtdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User getUserByUsername(String username);
    public User getUserByPassword(String password);

}
