package com.vastio.mapper;

import com.vastio.bean.model.StandBook;
import com.vastio.bean.request.RequestCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:45 2018/7/3
 */

@Repository
public interface StandBookMapper {
    List<StandBook> findStandBook(@Param("param") RequestCondition param);

    void deleteStandBook();
}