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
import com.vastio.service.ScheduledService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ScheduledService scheduledService;

    @GetMapping("/standBook")
    public ResponseResult<StandBook> getStandBook(RequestCondition param) throws ParseException {
        PageHelper.startPage(param.getCurPage(), param.getPageSize());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNoneBlank(param.getStartTime())) {
            param.setStartTime(String.valueOf(formatter.parse(param.getStartTime()).getTime()));
        }
        if (StringUtils.isNoneBlank(param.getEndTime())) {
            param.setEndTime(String.valueOf(formatter.parse(param.getEndTime()).getTime()));
        }
        List<StandBook> standBookList = contractService.getStandBook(param);
        PageInfo<StandBook> pageInfo = new PageInfo<>(standBookList);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @PostMapping("/standBook")
    public ResponseResult<String> addStandBook(@RequestBody StandBook standBook) {
        contractService.addStandBook(standBook);
        return success("success");
    }

    @DeleteMapping("/standBook/{id}")
    public ResponseResult<String> deleteStandBook(@PathVariable("id") int id) {
        contractService.deleteStandBookById(id);
        return success("success");
    }

    @PutMapping("/standBook")
    public ResponseResult<String> updateStandBook(StandBook standBook) {
        contractService.updateStandBook(standBook);
        return success("success");
    }

    @GetMapping("/overTimeContract")
    public ResponseResult<OverTimeContract> getOverTimeContract(@RequestParam(name = "siteName", required = false) String siteName,
                                                                @RequestParam("curPage") int curPage,
                                                                @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<OverTimeContract> overTimeContract = contractService.getOverTimeContract(siteName);
        PageInfo<OverTimeContract> pageInfo = new PageInfo<>(overTimeContract);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @GetMapping("/toPayContract")
    public ResponseResult<ContractInfo> getContractInfo(@RequestParam(name = "siteName", required = false) String siteName,
                                                        @RequestParam("curPage") int curPage,
                                                        @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<ContractInfo> contractInfos = contractService.getToPayContract(siteName);
        PageInfo<ContractInfo> pageInfo = new PageInfo<>(contractInfos);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @GetMapping("/renewContract")
    public ResponseResult<RenewContract> getRenewContract(@RequestParam(name = "siteName", required = false) String siteName,
                                                          @RequestParam("curPage") int curPage,
                                                          @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<RenewContract> renewContracts = contractService.getRenewContract(siteName);
        PageInfo<RenewContract> pageInfo = new PageInfo<>(renewContracts);
        return success(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @PostMapping("standbook/import")
    public ResponseResult<String> addUser(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        contractService.batchImportNew(fileName, file);
        return success("success");
    }

    @PutMapping("task")
    public ResponseResult<String> addUser() {
        scheduledService.timer();
        return success("success");
    }

    @GetMapping("order/code")
    public ResponseResult<Map> findByOrderByCode() {
        List<Map> result = contractService.findOrderByCode();
        return success(result, result.size());
    }

    @GetMapping("order/year")
    public ResponseResult<Map> findOrderByYear() {
        List<Map> result = contractService.findOrderByYear();
        return success(result, result.size());
    }
}
