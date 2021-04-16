package com.mingyang.admin.controller;

import com.mingyang.admin.entity.UmsAdmin;
import com.mingyang.admin.security.AdminLoginParam;
import com.mingyang.admin.service.IUmsAdminService;
import com.mingyang.common.api.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 登录
 *
 * @author Mingyang
 */
@RestController
@Api(tags = "LoginController")
public class LoginController {

    @Autowired
    private IUmsAdminService umsAdminService;

    /**
     * 用户登录
     *
     * @param adminLoginParam
     * @return token
     */
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam) {
        return umsAdminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
    }

    /**
     * 用户注册
     *
     * @param adminLoginParam
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public RespBean register(@RequestBody AdminLoginParam adminLoginParam) {
        return umsAdminService.register(adminLoginParam);
    }

    /**
     * 获取当前登录用户的信息
     *
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public UmsAdmin getAdminInfo(Principal principal) {
        if (principal == null) {
            return null;
        }
        String username = principal.getName();
        UmsAdmin admin = umsAdminService.getAdminByUsername(username);
        admin.setPassword(null);
        return admin;
    }

    /**
     * 注销登录
     * 只是返回200状态码，因为有JWT验证，所以只需要前端删除token即可
     *
     * @return
     */
    @ApiOperation(value = "注销登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功！");
    }


}
