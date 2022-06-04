package com.ondif.tools.springboottaskscheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ondif.tools.springboottaskscheduler.dao.Coffee;
import com.ondif.tools.springboottaskscheduler.dao.Item;
import com.ondif.tools.springboottaskscheduler.dao.Tea;
import com.ondif.tools.springboottaskscheduler.dto.ReqItem;
import com.ondif.tools.springboottaskscheduler.repository.CoffeeRepository;
import com.ondif.tools.springboottaskscheduler.repository.ItemRepository;
import com.ondif.tools.springboottaskscheduler.repository.TeaRepository;

@Service
public class DataServiceImpl implements DataService {

    private ItemRepository itemRepository;
    private TeaRepository teaRepository;
    private CoffeeRepository coffeeRepository;

    @Autowired
    public DataServiceImpl (
        ItemRepository itemRepository
        , TeaRepository teaRepository
        , CoffeeRepository coffeeRepository
    ) {
        this.itemRepository = itemRepository;
        this.teaRepository = teaRepository;
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public List<Item> getItems() {
        return this.itemRepository.findAll();
    }

    @Override
    public boolean addItem(ReqItem item) {
        Item tItem = new Item();
        tItem.setName(item.getName());
        tItem.setPrice(item.getPrice());
        tItem.setType(item.getType());
        itemRepository.save(tItem);
        return true;
    }

    @Override
    public boolean addTea(Tea tea) {
        teaRepository.save(tea);
        return true;
    }

    @Override
    public boolean addCoffee(Coffee coffee) {
        coffeeRepository.save(coffee);
        return true;
    }
    
}
