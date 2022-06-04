package com.ondif.tools.springboottaskscheduler.configuration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@Configuration
@ConfigurationProperties(prefix = "publiceinfo")
public class PublicInfoProperties {
    private String      filedirname;
    private String      userid;
    private String      username;
    private String      email;
    private DetailInfo  detailinfo = new DetailInfo();

    private int         coffeeSyncJobCounter;
    private int         teaSyncJobCounter;


    private final Logger LOG = LoggerFactory.getLogger(PublicInfoProperties.class);

    @PostConstruct
    public void init() {
        coffeeSyncJobCounter = 0;
        teaSyncJobCounter = 0;
    }


    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Validated
    public class DetailInfo {
        private String userid;
        private String regnumber;
        private String regname;
        private String regaddr;
        private String regtel;
        private String regemail;
        private String regpartname;
        private String certtype;
        private String certinfo;
        private ItemInfo iteminfo = new ItemInfo();

        @Getter
        @Setter
        @ToString
        @AllArgsConstructor
        @NoArgsConstructor
        @Validated
        public class ItemInfo {
            private String name;
            private String serialnumber;
            private int price;
        }
    }
}
