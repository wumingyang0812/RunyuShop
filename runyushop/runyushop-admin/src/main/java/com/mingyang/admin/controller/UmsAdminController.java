package com.mingyang.admin.controller;


import com.mingyang.admin.entity.UmsAdmin;
import com.mingyang.admin.service.IUmsAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author Mingyang
 * @since 2021-04-15
 */
@RestController
@RequestMapping("/umsAdmin")
public class UmsAdminController {

    @Autowired
    private IUmsAdminService umsAdminService;

    @ApiOperation("测试：获取所有管理员")
    @GetMapping("/")
    public List<UmsAdmin> GetUmsAdminLists() {
        return umsAdminService.list();
    }

}
