package com.ondif.tools.springboottaskscheduler.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ondif.tools.springboottaskscheduler.dao.Coffee;
import com.ondif.tools.springboottaskscheduler.dao.Item;
import com.ondif.tools.springboottaskscheduler.repository.CoffeeRepository;
import com.ondif.tools.springboottaskscheduler.repository.ItemRepository;

@Service
public class SyncCoffeeServiceImpl implements SyncCoffeeService{

    private final Logger LOG = LoggerFactory.getLogger(SyncCoffeeServiceImpl.class);

    private ItemRepository itemRepository;
    private CoffeeRepository coffeeRepository;

    @Autowired
    public SyncCoffeeServiceImpl (
        ItemRepository itemRepository
        , CoffeeRepository coffeeRepository
    ) {
        this.itemRepository = itemRepository;
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public int syncCoffeeItem() {
        List<Item> itemList = itemRepository.findAllByType("COFFEE");

        int syncCount = 0; // 동기화 한 컬럼 갯수.
        for (int i=0; i<itemList.size(); i++) {
            Item item = itemList.get(i);
            LOG.info("[syncCoffeeItem] idx:{}, data:{}", i, item);
            Coffee coffee = new Coffee();
            coffee.setName(item.getName());
            coffee.setPrice(item.getPrice());
            if (null != coffeeRepository.save(coffee)) {
                itemRepository.delete(item);
                syncCount++;
            }
        }
        return syncCount;
    }
    
}
