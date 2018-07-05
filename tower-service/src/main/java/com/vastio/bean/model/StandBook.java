package com.vastio.bean.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:05 2018/7/3
 */

@Data
public class StandBook extends ContractInfo {
    private String attribute;
    private String phoneNumber;
    private String ownerName;
    private Date payTime;
    private double rent;
    private double tax;
}
