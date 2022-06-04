package com.ondif.tools.springboottaskscheduler.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ondif.tools.springboottaskscheduler.dto.ReqItem;
import com.ondif.tools.springboottaskscheduler.dto.ReqItemList;
import com.ondif.tools.springboottaskscheduler.service.DataService;
import com.ondif.tools.springboottaskscheduler.service.ScheduleConfigService;

// This is a very basic controller just to show how scheduler can be called from outside,
// this is not a good exemplary controller and endpoints are not well designed
@RestController
public class SchedulingController {

    private final Logger LOG = LoggerFactory.getLogger(SchedulingController.class);

    private ScheduleConfigService scheduleConfigService;
    private DataService dataService;

    @Autowired
    public SchedulingController (
        ScheduleConfigService scheduleConfigService
        , DataService dataService
    ) {
        this.scheduleConfigService = scheduleConfigService;
        this.dataService = dataService;
    }

    @PostMapping("/scheduler/add")
    @ResponseBody
    public String addJob(@RequestParam("job") String name) {
        LOG.info("add ------------------------------------------------------------");
        boolean result = scheduleConfigService.jobControl(name, true);
        if (result) {
            return "add job success";
        }
        return "add job fail";
    }

    @PostMapping("/scheduler/remove")
    @ResponseBody
    public String removeJob(@RequestParam("job") String name) {
        LOG.info("remove ------------------------------------------------------------");
        boolean result = scheduleConfigService.jobControl(name, false);
        if (result) {
            return "remove job success";
        }
        return "remove job fail";
    }

    @PostMapping("/item/add")
    @ResponseBody
    public String addItem(@RequestBody ReqItemList reqDataList) {
        LOG.info("add item ------------------------------------------------------------");
        for (int i=0; i< reqDataList.getItemlist().size();i++) {
            ReqItem item = reqDataList.getItemlist().get(i);
            dataService.addItem(item);
        }
        return "add item success";
    }
}
