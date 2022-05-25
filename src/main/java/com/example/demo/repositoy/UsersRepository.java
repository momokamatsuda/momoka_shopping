package com.example.demo.repositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	//select * from users where email ='' and password =''と同じ意味; 
	List<Users> findAllByEmailAndPassword(String email, String password);
	List<Users> findAllByEmail(String email);
	
}
