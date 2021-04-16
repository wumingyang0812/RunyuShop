package com.mingyang.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mingyang.admin.entity.UmsAdmin;
import com.mingyang.admin.security.AdminLoginParam;
import com.mingyang.common.api.RespBean;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author Mingyang
 * @since 2021-04-15
 */
public interface IUmsAdminService extends IService<UmsAdmin> {
    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password);

    /**
     * 通过用户名获取用户对象
     *
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 用户注册
     *
     * @param adminLoginParam
     * @return
     */
    RespBean register(AdminLoginParam adminLoginParam);
}
