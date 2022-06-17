package com.ondif.tools.springboottaskscheduler.scheduler;

public interface CommScheduler {
    public String getKey();
    public long getDelayMilliSec();
    public int getDelaySec();
    public void getTask();
    public boolean isActive();
    public void setActive(boolean active);
    // 동작 중 같은 요청이 들어올 경우 counter를 리셋한다.
    public void resetCount();
}
