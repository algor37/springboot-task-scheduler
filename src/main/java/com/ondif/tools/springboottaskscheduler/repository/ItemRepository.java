package com.ondif.tools.springboottaskscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ondif.tools.springboottaskscheduler.dao.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByType(String type);
}