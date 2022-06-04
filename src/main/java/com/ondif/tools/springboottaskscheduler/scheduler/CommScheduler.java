package com.ondif.tools.springboottaskscheduler.scheduler;

public interface CommScheduler {
    public String getKey();
    public long getDelayMilliSec();
    public int getDelaySec();
    public void getTask();
    public boolean isActive();
    public void setActive(boolean active);

}
