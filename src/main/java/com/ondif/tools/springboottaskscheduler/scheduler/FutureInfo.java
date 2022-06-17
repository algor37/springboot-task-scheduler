package com.ondif.tools.springboottaskscheduler.scheduler;

import java.util.concurrent.ScheduledFuture;


public class FutureInfo {
    private ScheduledFuture future;
    private CommScheduler service;

    public FutureInfo(CommScheduler service) {
        this.service = service;
    }

    public ScheduledFuture getFuture() {
        return future;
    }
    public void setFuture(ScheduledFuture future) {
        this.future = future;
    }
    public String getKey() {
        return this.service.getKey();
    }

    public boolean isActive() {
        return this.service.isActive();
    }
    public void setActive(boolean active) {
        this.service.setActive(active);;
    }

    public CommScheduler getService() {
        return this.service;
    }
}
