package com.vastio.bean.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 16:36 2018/7/4
 */

@Data
public class RenewContract {
    private int id;
    private String region;
    private String code;
    private String siteName;
    private String contractName;
    private Date contractStart;
    private Date contractEnd;
}
