package com.ondif.tools.springboottaskscheduler.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author DongJin KIM {@literal algor37@gmail.com}
 * create          2019-11-25 21:48
 */
@Getter
@Setter
@JsonSerialize
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "Coffee")
@Table(name = "COFFEE")
public class Coffee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private long id;

    private String  name;
    private int  price;
}
