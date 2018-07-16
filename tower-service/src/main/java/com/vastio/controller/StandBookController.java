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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public ResponseResult<String> updateStandBook(@RequestBody StandBook standBook) {
        contractService.updateStandBook(standBook);
        return success("success");
    }

    @GetMapping("/overTimeContract")
    public ResponseResult<OverTimeContract> getOverTimeContract(@RequestParam(name = "siteName", required = false) String siteName,
                                                                @RequestParam(name = "flag") Integer flag,
                                                                @RequestParam("curPage") int curPage,
                                                                @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<OverTimeContract> overTimeContract = contractService.getOverTimeContract(siteName, flag);
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

    @PostMapping("/standbook/import")
    public ResponseResult<String> addUser(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        contractService.batchImportNew(fileName, file);
        return success("success");
    }

    @PutMapping("/task")
    public ResponseResult<String> addUser() {
        scheduledService.timer();
        return success("success");
    }

    @GetMapping("/statistic")
    public ResponseResult<Map<String, Object>> findOrderByYear() {
        List<Map> result = contractService.findOrderByYear();
        Map<String, Object> map = new HashMap<>();
        Set<String> stringSet = new LinkedHashSet<>();
        Set<String> regionSet = new LinkedHashSet<>();
        result.forEach(e -> {
            stringSet.add(e.get("year").toString());
            regionSet.add(e.get("region").toString());
        });
        map.put("year", stringSet.toArray());
        map.put("region", regionSet.toArray());
        Map<String, Object> avgMap = new HashMap<>();
        Map<String, Object> taxMap = new HashMap<>();
        List<Map> innerRentMap = new ArrayList<>();
        List<Map> outRentMap = new ArrayList<>();
        List<Map> innerCountMap = new ArrayList<>();
        List<Map> outCountMap = new ArrayList<>();
        regionSet.forEach(t -> {
            List<Double> avg = new ArrayList<>();
            List<Double> tax = new ArrayList<>();
            double rent = 0;
            long count = 0;
            Map<String, Object> map2 = new HashMap<>();
            Map<String, Object> map3 = new HashMap<>();
            for (Map e : result) {
                Map<String, Object> map1 = new HashMap<>();
                Map<String, Object> map4 = new HashMap<>();
                if (e.get("region").toString().equals(t)) {
                    avg.add(transfor((double) e.get("avg_rent")));
                    tax.add((double) e.get("tax"));
                    rent += (double) e.get("rent");
                    count += (long) e.get("count");
                    map1.put("name", t + "-" + e.get("year").toString());
                    map1.put("value", e.get("rent"));
                    map4.put("name", t + "-" + e.get("year").toString());
                    map4.put("value", e.get("count"));
                    outRentMap.add(map1);
                    outCountMap.add(map4);
                }
            }
            avgMap.put(t, avg);
            taxMap.put(t, tax);
            map2.put("name", t);
            map3.put("name", t);
            map2.put("value", rent);
            map3.put("value", count);
            innerRentMap.add(map2);
            innerCountMap.add(map3);
        });
        map.put("avg", avgMap);
        map.put("tax", taxMap);
        map.put("innerRent", innerRentMap);
        map.put("outRent", outRentMap);
        List<String> rentName = new ArrayList<>();
        innerRentMap.forEach(e -> rentName.add(e.get("name").toString()));
        outRentMap.forEach(e -> rentName.add(e.get("name").toString()));
        map.put("rentName", rentName);
        map.put("innerCount", innerCountMap);
        map.put("outCount", outCountMap);
        List<String> countName = new ArrayList<>();
        innerCountMap.forEach(e -> countName.add(e.get("name").toString()));
        outCountMap.forEach(e -> countName.add(e.get("name").toString()));
        map.put("countName", countName);
        map.putAll(contractService.getCount());
        return successResult(map, "统计数据");
    }

    private double transfor(double a) {
        BigDecimal b = new BigDecimal(a);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
}
