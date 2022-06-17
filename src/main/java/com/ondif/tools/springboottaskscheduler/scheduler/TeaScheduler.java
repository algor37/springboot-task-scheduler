package com.ondif.tools.springboottaskscheduler.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ondif.tools.springboottaskscheduler.configuration.PublicInfoProperties;
import com.ondif.tools.springboottaskscheduler.service.ScheduleConfigService;
import com.ondif.tools.springboottaskscheduler.service.SyncTeaService;

@Service
public class TeaScheduler implements CommScheduler {

    private boolean active = false;
    private final String KEYNAME = "TEA";
    private int maxCount = 10;
    private int delay = 1000; // milli second

    
    private final Logger LOG = LoggerFactory.getLogger(TeaScheduler.class);


    private PublicInfoProperties publicProp;
    private SyncTeaService syncTeaService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public TeaScheduler(
        SyncTeaService syncTeaService
        , PublicInfoProperties publicProp
    ) {
        this.syncTeaService = syncTeaService;
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
        LOG.info("[TeaScheduler] delay {} milliseconds...", this.delay);
        return this.delay;
    }


    @Override
    public void getTask() {
        final long now = System.currentTimeMillis() / 1000;
        LOG.info("[TeaScheduler] schedule tasks with dynamic delay - {}", now);

          int syncCount = syncTeaService.syncTeaItem();
          LOG.info("[TeaScheduler] ----- sync item counter: {}", syncCount);
  
          LOG.info("[TeaScheduler] ----- Run Job Counter: {}", this.publicProp.getTeaSyncJobCounter());
          this.publicProp.setTeaSyncJobCounter(this.publicProp.getTeaSyncJobCounter()+1);
          if (this.publicProp.getTeaSyncJobCounter() > maxCount) {
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
        this.publicProp.setTeaSyncJobCounter(0);
    }
}