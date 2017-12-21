/**
 * Index Controller, project entrance
 */
package com.waysli.tools.security.controller;

import com.waysli.tools.security.demo.converter.LongId;
import com.waysli.tools.security.demo.converter.TestModule;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 服务首页
 *
 * @author waysli
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

    @RequestMapping(value = "/format1", method = RequestMethod.POST)
    public TestModule test1(@RequestBody TestModule module) {
        return module;
    }

    @RequestMapping(value = "/format2", method = RequestMethod.POST)
    public TestModule test2(@ModelAttribute TestModule module) {
        return module;
    }

    @RequestMapping(value = "/format3", method = RequestMethod.POST)
    public Object test3(@RequestParam("id") LongId id) {
        return id;
    }

    @RequestMapping(value = "/format4/{id}", method = RequestMethod.POST)
    public Object test4(@PathVariable("id") LongId id) {
        return id;
    }

    @RequestMapping(value = "/format5", method = RequestMethod.GET)
    public TestModule test5(@ModelAttribute TestModule module) {
        return module;
    }

    @RequestMapping(value = "/format6", method = RequestMethod.GET)
    public Object test6(@RequestParam("id") LongId id) {
        return id;
    }

    @RequestMapping(value = "/format7/{id}", method = RequestMethod.GET)
    public Object test7(@PathVariable("id") LongId id) {
        return id;
    }

}
