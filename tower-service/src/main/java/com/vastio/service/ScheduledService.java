package com.vastio.service;

import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.StandBook;
import com.vastio.mapper.ContractMapper;
import com.vastio.mapper.StandBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 11:30 2018/7/5
 */

@Service
public class ScheduledService {
    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private StandBookMapper standBookMapper;

    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void timer() {
        contractMapper.deleteAllToPayContract();
        contractMapper.deleteAllRenewContract();
        if (!standBookMapper.findToPayStandBook().isEmpty())
            contractMapper.createToPayContract(standBookMapper.findToPayStandBook());
        List<StandBook> standBookList = standBookMapper.findOverTimePayStandBook();
        List<OverTimeContract> newOverTime = new ArrayList<>();
        standBookList.forEach(e -> {
            OverTimeContract overTimeContract = new OverTimeContract();
            overTimeContract.setCode(e.getCode());
            overTimeContract.setContractEnd(e.getContractEnd());
            overTimeContract.setContractStart(e.getContractStart());
            overTimeContract.setContractName(e.getContractName());
            overTimeContract.setRegion(e.getRegion());
            overTimeContract.setSiteName(e.getSiteName());
            overTimeContract.setStart(e.getStart());
            overTimeContract.setEnd(e.getEnd());
        });
        List<OverTimeContract> oldOverTime = contractMapper.getOverPayContract("");
        newOverTime.removeAll(oldOverTime);
        if (!newOverTime.isEmpty())
            contractMapper.createOverTimeContract(newOverTime);
        if (!standBookMapper.findRenewContractStandBook().isEmpty())
            contractMapper.createRenewContract(standBookMapper.findRenewContractStandBook());
    }
}
