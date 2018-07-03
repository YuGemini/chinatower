package com.vastio.controller.base;

import com.vastio.bean.base.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 所有 controller 父类
 *
 * @author xlch
 * @Date 2018-03-02 10:23
 */
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    protected  <T> ApiResponse<T> success(int total, List<T> results) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(HttpServletResponse.SC_OK);
        apiResponse.setTotal(total);
        apiResponse.setResults(results);
        return apiResponse;
    }

    protected  <T> ApiResponse<T> error(Integer code, String message) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(code);
        apiResponse.setMessage(message);
        return apiResponse;
    }

}
