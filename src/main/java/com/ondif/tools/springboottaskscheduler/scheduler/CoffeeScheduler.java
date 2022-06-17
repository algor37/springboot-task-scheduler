package com.ondif.tools.springboottaskscheduler.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ondif.tools.springboottaskscheduler.configuration.PublicInfoProperties;
import com.ondif.tools.springboottaskscheduler.service.ScheduleConfigService;
import com.ondif.tools.springboottaskscheduler.service.SyncCoffeeService;

@Service
public class CoffeeScheduler implements CommScheduler {

    private boolean active = false;
    private final String KEYNAME = "COFFEE";
    private int maxCount = 10;
    private int delay = 1000; // milli second

    private final Logger LOG = LoggerFactory.getLogger(CoffeeScheduler.class);


    private PublicInfoProperties publicProp;
    private SyncCoffeeService syncItemService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public CoffeeScheduler(
        SyncCoffeeService syncItemService
        , PublicInfoProperties publicProp
    ) {
        this.syncItemService = syncItemService;
        this.publicProp = publicProp;
        this.maxCount = publicProp.getSchedulerinfo().getMaxcount();
        this.delay = publicProp.getSchedulerinfo().getDelay();
    }


    @Override
    public String getKey() {
        return KEYNAME;
    }

    @Override
    public long getDelayMilliSec() {
        System.out.println("[CoffeeScheduler] delay " + this.delay + " milliseconds...");
        return this.delay;
    }


    @Override
    public void getTask() {
        final long now = System.currentTimeMillis() / 1000;
        LOG.info("[CoffeeScheduler] schedule tasks with dynamic delay - " + now);

        int syncCount = syncItemService.syncCoffeeItem();
        LOG.info("[CoffeeScheduler] ----- sync item counter: {}", syncCount);
        // private ScheduleConfigService scheduleConfigService;

        LOG.info("[CoffeeScheduler] ----- Run Job Counter: {}", this.publicProp.getCoffeeSyncJobCounter());
        this.publicProp.setCoffeeSyncJobCounter(this.publicProp.getCoffeeSyncJobCounter()+1);
        if (this.publicProp.getCoffeeSyncJobCounter() > maxCount) {
            ScheduleConfigService scheduleConfigService = context.getBean(ScheduleConfigService.class);
            scheduleConfigService.jobControl(KEYNAME, false);
        }

    }

    @Override
    public int getDelaySec() {
        return (int)(this.delay/1000);
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void resetCount() {
        this.publicProp.setCoffeeSyncJobCounter(0);
    }
}