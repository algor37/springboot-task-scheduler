package com.ondif.tools.springboottaskscheduler.service;

import java.util.List;

import com.ondif.tools.springboottaskscheduler.dao.Coffee;
import com.ondif.tools.springboottaskscheduler.dao.Item;
import com.ondif.tools.springboottaskscheduler.dao.Tea;
import com.ondif.tools.springboottaskscheduler.dto.ReqItem;

public interface DataService {
    boolean addItem(ReqItem item);

    boolean addTea(Tea tea);

    boolean addCoffee(Coffee coffee);

    List<Item> getItems();

}
