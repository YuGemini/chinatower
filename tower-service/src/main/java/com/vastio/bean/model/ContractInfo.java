package com.vastio.bean.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 10:14 2018/7/4
 */

@Data
public class ContractInfo extends RenewContract{
    private Date start;
    private Date end;
}
