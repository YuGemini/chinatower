package com.vastio.mapper;

import com.vastio.bean.model.ContractInfo;
import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.RenewContract;
import com.vastio.bean.model.StandBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 10:16 2018/7/4
 */

@Repository
public interface ContractMapper {
    List<ContractInfo> getToPayContract(@Param("siteName") String siteName);

    List<OverTimeContract> getOverPayContract(@Param("siteName") String siteName);

    List<RenewContract> getRenewContract(@Param("siteName") String siteName);

    void createToPayContract(List<StandBook> standBooks);

    void createOverTimeContract(List<OverTimeContract> standBooks);

    void deleteAllToPayContract();

    void deleteAllRenewContract();

    void createRenewContract(List<StandBook> standBooks);
}
