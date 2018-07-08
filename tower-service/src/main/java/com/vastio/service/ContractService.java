package com.vastio.service;

import com.vastio.bean.model.ContractInfo;
import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.RenewContract;
import com.vastio.bean.model.StandBook;
import com.vastio.bean.request.RequestCondition;
import com.vastio.mapper.ContractMapper;
import com.vastio.mapper.StandBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 14:10 2018/7/5
 */

@Service
public class ContractService {
    @Autowired
    private StandBookMapper standBookMapper;

    @Autowired
    private ContractMapper contractMapper;

    public List<StandBook> getStandBook(RequestCondition param) {
        return standBookMapper.findStandBook(param);
    }

    public List<OverTimeContract> getOverTimeContract(String siteName) {
        return contractMapper.getOverPayContract(siteName);
    }

    public List<ContractInfo> getToPayContract(String siteName) {
        return contractMapper.getToPayContract(siteName);
    }

    public List<RenewContract> getRenewContract(String siteName) {
        return contractMapper.getRenewContract(siteName);
    }

    public void deleteStandBookById(int id) {
        standBookMapper.deleteStandBookById(id);
    }

    public void addStandBook(StandBook standBook) {
        standBookMapper.addStandBook(standBook);
    }

    public void updateStandBook(StandBook standBook) {
        standBookMapper.updateStandBook(standBook);
    }
}
