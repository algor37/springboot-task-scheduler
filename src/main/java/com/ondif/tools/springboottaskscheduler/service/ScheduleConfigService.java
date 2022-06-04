package com.ondif.tools.springboottaskscheduler.service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

import com.ondif.tools.springboottaskscheduler.scheduler.CoffeeScheduler;
import com.ondif.tools.springboottaskscheduler.scheduler.FutureInfo;
import com.ondif.tools.springboottaskscheduler.scheduler.TeaScheduler;

@Service
@Configuration
@EnableScheduling
public class ScheduleConfigService implements SchedulingConfigurer {

    ScheduledTaskRegistrar scheduledTaskRegistrar;

    FutureInfo teaFutureInfo;
    FutureInfo coffeeFutureInfo;
    Map<String, FutureInfo> futureInfoMap = new HashMap<>();

    
    private static Logger LOG = LoggerFactory.getLogger(ScheduleConfigService.class);

    @Autowired
    private TeaScheduler teaScheduler;

    @Autowired
    private CoffeeScheduler coffeeScheduler;

    private void initialize() {
        LOG.info("initialize -----------------------------------");
        this.teaFutureInfo = new FutureInfo(teaScheduler);
        this.coffeeFutureInfo = new FutureInfo(coffeeScheduler);        
    }

    @Bean
    public TaskScheduler poolScheduler() {
        initialize();
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(2);
        scheduler.initialize();
        return scheduler;
    }
    // Initially scheduler has no job
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        LOG.info("[configureTasks] ------------------------10");
        if (scheduledTaskRegistrar == null) {
            LOG.info("[configureTasks] ------------------------20");
            scheduledTaskRegistrar = taskRegistrar;
        }
        if (taskRegistrar.getScheduler() == null) {
            LOG.info("[configureTasks] ------------------------30");
            taskRegistrar.setScheduler(poolScheduler());
        }
        
        logFutureInfoMap("1111111");
        LOG.info("[configureTasks] ------------------------40");
        if (teaFutureInfo.getFuture() == null || (teaFutureInfo.getFuture().isCancelled() && teaFutureInfo.isActive() == true)) {
            LOG.info("[configureTasks] ------------------------41");
            ScheduledFuture future1 = scheduledTaskRegistrar.getScheduler().schedule(() -> teaScheduler.getTask(), t -> {
                Optional<Date> lastCompletionTime = Optional.ofNullable(t.lastCompletionTime());
                Instant nextExecutionTime = lastCompletionTime.orElseGet(Date::new).toInstant().plusMillis(teaScheduler.getDelayMilliSec());
                return Date.from(nextExecutionTime);
            });
            if (teaFutureInfo.getFuture() == null) {
                // 처음인 경우에는 중지 상태를 유지한다.
                LOG.info("[configureTasks] first call method. ---- 45");
                future1.cancel(true);
            }
            teaFutureInfo.setFuture(future1);
            LOG.info("[configureTasks] tickFutureInfo key:{}", teaFutureInfo.getKey());
            futureInfoMap.put(teaFutureInfo.getKey(), teaFutureInfo);
        }

        logFutureInfoMap("2222222");

        LOG.info("[configureTasks] ------------------------50");
        if (coffeeFutureInfo.getFuture() == null || (coffeeFutureInfo.getFuture().isCancelled() && coffeeFutureInfo.isActive() == true)) {
            LOG.info("[configureTasks] ------------------------51");
            ScheduledFuture future2 = scheduledTaskRegistrar.getScheduler().schedule(() -> coffeeScheduler.getTask(), t -> {
                Optional<Date> lastCompletionTime = Optional.ofNullable(t.lastCompletionTime());
                Instant nextExecutionTime = lastCompletionTime.orElseGet(Date::new).toInstant().plusMillis(coffeeScheduler.getDelayMilliSec());
                return Date.from(nextExecutionTime);
            });
            if (coffeeFutureInfo.getFuture() == null) {
                // 처음인 경우에는 중지 상태를 유지한다.
                LOG.info("[configureTasks] first call method.------55");
                future2.cancel(true);
            }
            
            coffeeFutureInfo.setFuture(future2);
            LOG.info("[configureTasks] coffeeFutureInfo key:{}", coffeeFutureInfo.getKey());
            futureInfoMap.put(coffeeFutureInfo.getKey(), coffeeFutureInfo);
            if (futureInfoMap.get(coffeeFutureInfo.getKey()).getFuture().isCancelled()) {
                LOG.info("cancel state");
            } else {
                LOG.info("active state");
            }
        }
        logFutureInfoMap("3333333");

    }


    public boolean jobControl(String jobName, boolean isActive) {
        LOG.info("[jobControl] 추가할 스캐쥴러 name:[{}]", jobName);

        for (String key : futureInfoMap.keySet()) {
            LOG.info("[jobControl] futureInfoMap key:[{}]", key);
        }

        if (futureInfoMap.get(jobName) == null) {
            LOG.error("[jobControl] futureInfoMap에서 name:{} 를 찾지 못함.", jobName);
            return false;
        }
        
        if (isActive) {
            activeJob(jobName);
        } else {
           cancelJob(jobName);
        }

        return true;
    }

    private void logFutureInfoMap(String title) {
        LOG.info("[logFutureInfoMap] {} --------------------------------------", title);
        LOG.info("[logFutureInfoMap] futureInfoMap count: {} -----------------", futureInfoMap.size());
    
        for (String key : futureInfoMap.keySet()) {
            LOG.info("[logFutureInfoMap] key:{}", key);
            FutureInfo tfutureInfo = futureInfoMap.get(key);
            if (null == tfutureInfo) {
                LOG.info("[logFutureInfoMap] key:{}, FutureInfo is null", key);
            } else {
                if (tfutureInfo.getFuture().isCancelled()) {
                    LOG.info("[logFutureInfoMap] key:{}, FutureInfo is 중지.", key);
                } else {
                    LOG.info("[logFutureInfoMap] key:{}, FutureInfo is 동작.", key);
                }
            }
        }
    }

    private boolean activeJob(String jobName) {
        LOG.info("[activeJob] ------------------------ jobName:[{}]", jobName);
        FutureInfo futureInfo = futureInfoMap.get(jobName);
        if (null == futureInfo) {
            LOG.info("FutureInfo is null");
            return false;
        }

        if (futureInfo.getFuture().isCancelled()) {
            LOG.info("[cancelJob] 중지중... 동작요청 시작");
            futureInfo.setActive(true);
            configureTasks(scheduledTaskRegistrar);
            return true;
        } else {
            LOG.warn("현재 중지 중이 아니다!!!!");
        }
        return false;
    }

    private boolean activeAllJob(String jobName) {
        for (String key : futureInfoMap.keySet()) {
            activeJob(key);
        }
        return true;
    }

    private boolean cancelJob(String jobName) {
        LOG.info("[cancelJob] ------------------------ jobName:[{}]", jobName);
        FutureInfo futureInfo = futureInfoMap.get(jobName);
        if (null == futureInfo) {
            LOG.info("FutureInfo is null");
            return false;
        }
        if (!futureInfo.getFuture().isCancelled()) {
            LOG.info("[cancelJob] 동작중... 중지요청 시작");
            futureInfo.getFuture().cancel(true);
            futureInfo.setActive(false);
            configureTasks(scheduledTaskRegistrar);
            return true;
        } else {
            LOG.warn("현재 동작 중이 아니다!!!!");
        }
        return false;
    }


    private boolean cancelAllJob(String jobName) {
        for (String key : futureInfoMap.keySet()) {
            cancelJob(key);
        }
        return true;
    }

}
