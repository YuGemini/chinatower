package com.vastio.bean.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:05 2018/7/3
 */

@Data
public class StandBook {
    private int id;
    private String region;
    private String code;
    private String attribute;
    private String siteName;
    private String contractName;
    private String phoneNumber;
    private String ownerName;
    private Date contractStart;
    private Date contractEnd;
    private Date payTime;
    private Date start;
    private Date end;
    private double rent;
    private double tax;
}
