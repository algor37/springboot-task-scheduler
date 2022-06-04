package com.ondif.tools.springboottaskscheduler.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonSerialize
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReqItem {
    private String name;
    private int price;
    private String type;
}
