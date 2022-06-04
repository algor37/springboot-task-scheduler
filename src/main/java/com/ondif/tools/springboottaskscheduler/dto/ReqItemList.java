package com.ondif.tools.springboottaskscheduler.dto;

import java.io.Serializable;
import java.util.ArrayList;

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
public class ReqItemList implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<ReqItem> itemlist;
}
