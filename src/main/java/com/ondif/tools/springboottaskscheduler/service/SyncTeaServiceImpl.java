package com.ondif.tools.springboottaskscheduler.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ondif.tools.springboottaskscheduler.dao.Item;
import com.ondif.tools.springboottaskscheduler.dao.Tea;
import com.ondif.tools.springboottaskscheduler.repository.ItemRepository;
import com.ondif.tools.springboottaskscheduler.repository.TeaRepository;

@Service
public class SyncTeaServiceImpl implements SyncTeaService{

    private final Logger LOG = LoggerFactory.getLogger(SyncTeaServiceImpl.class);

    private ItemRepository itemRepository;
    private TeaRepository teaRepository;

    @Autowired
    public SyncTeaServiceImpl (
        ItemRepository itemRepository
        , TeaRepository teaRepository
    ) {
        this.itemRepository = itemRepository;
        this.teaRepository = teaRepository;
    }

    @Override
    public int syncTeaItem() {
        List<Item> itemList = itemRepository.findAllByType("TEA");

        int syncCount = 0; // 동기화 한 컬럼 갯수.
        for (int i=0; i<itemList.size(); i++) {
            Item item = itemList.get(i);
            LOG.info("[syncTeaItem] idx:{}, data:{}", i, item);
            Tea tea = new Tea();
            tea.setName(item.getName());
            tea.setPrice(item.getPrice());
            if (null != teaRepository.save(tea)) {
                itemRepository.delete(item);
                syncCount++;
            }
        }
        return syncCount;
    }
    
}
