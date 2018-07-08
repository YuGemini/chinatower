package com.vastio.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vastio.bean.base.ResponseResult;
import com.vastio.bean.model.ContractInfo;
import com.vastio.bean.model.OverTimeContract;
import com.vastio.bean.model.RenewContract;
import com.vastio.bean.model.StandBook;
import com.vastio.bean.request.RequestCondition;
import com.vastio.controller.base.BaseController;
import com.vastio.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Chen Xiaoyu
 * @Description:
 * @Date: Created in 17:43 2018/7/3
 */

@RestController
@RequestMapping("/api")
public class StandBookController extends BaseController {
    @Autowired
    private ContractService contractService;

    @GetMapping(value = "/standBook")
    public ResponseResult<StandBook> getStandBook(RequestCondition param) {
        PageHelper.startPage(param.getCurPage(), param.getPageSize());
        List<StandBook> standBookList = contractService.getStandBook(param);
        PageInfo<StandBook> pageInfo = new PageInfo<>(standBookList);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @PostMapping(value = "/standBook")
    public ResponseResult<String> addStandBook(@RequestBody StandBook standBook) {
        contractService.addStandBook(standBook);
        return success("success");
    }

    @DeleteMapping(value = "/standBook/{id}")
    public ResponseResult<String> deleteStandBook(@PathVariable("id") int id) {
        contractService.deleteStandBookById(id);
        return success("success");
    }

    @PutMapping(value = "/standBook")
    public ResponseResult<String> updateStandBook( StandBook standBook) {
        contractService.updateStandBook(standBook);
        return success("success");
    }

    @GetMapping(value = "/overTimeContract")
    public ResponseResult<OverTimeContract> getOverTimeContract(@RequestParam("siteName") String siteName,
                                                                @RequestParam("curPage") int curPage,
                                                                @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<OverTimeContract> overTimeContract = contractService.getOverTimeContract(siteName);
        PageInfo<OverTimeContract> pageInfo = new PageInfo<>(overTimeContract);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @GetMapping(value = "/toPayContract")
    public ResponseResult<ContractInfo> getContractInfo(@RequestParam("siteName") String siteName,
                                                        @RequestParam("curPage") int curPage,
                                                        @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<ContractInfo> contractInfos = contractService.getToPayContract(siteName);
        PageInfo<ContractInfo> pageInfo = new PageInfo<>(contractInfos);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @GetMapping(value = "/renewContract")
    public ResponseResult<RenewContract> getRenewContract(@RequestParam("siteName") String siteName,
                                                          @RequestParam("curPage") int curPage,
                                                          @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<RenewContract> renewContracts = contractService.getRenewContract(siteName);
        PageInfo<RenewContract> pageInfo = new PageInfo<>(renewContracts);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

}
