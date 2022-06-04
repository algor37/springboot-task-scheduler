package com.ondif.tools.springboottaskscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ondif.tools.springboottaskscheduler.dao.Coffee;


public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

}