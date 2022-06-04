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
    private long delay = 200; // milli second
    private final String KEYNAME = "TEA";
    private final int MAX_JOB_COUNT = 20;

    
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
          if (this.publicProp.getTeaSyncJobCounter() > MAX_JOB_COUNT) {
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

}