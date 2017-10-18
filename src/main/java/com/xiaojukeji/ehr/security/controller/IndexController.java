/**
 * Index Controller, project entrance
 */
package com.xiaojukeji.ehr.security.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 服务首页
 *
 * @author wangyongsheng
 * @date 16/10/9 下午3:31
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @ApiOperation(value = "首页跳转", notes = "无需参数")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @ApiOperation(value = "查看状态", notes = "无需参数")
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Object status() throws IOException {
        return "ok!";
    }

}
