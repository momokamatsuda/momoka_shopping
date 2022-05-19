package com.example.demo.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Ordered;


@Repository
public interface OrderedRepository extends JpaRepository<Ordered, Integer>{

}
