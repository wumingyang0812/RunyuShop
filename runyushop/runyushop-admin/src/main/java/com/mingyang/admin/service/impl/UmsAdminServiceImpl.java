package com.mingyang.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingyang.admin.entity.UmsAdmin;
import com.mingyang.admin.mapper.UmsAdminMapper;
import com.mingyang.admin.security.AdminLoginParam;
import com.mingyang.admin.service.IUmsAdminService;
import com.mingyang.common.api.RespBean;
import com.mingyang.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author Mingyang
 * @since 2021-04-15
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public RespBean login(String username, String password) {
        // 根据用户名获取userDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 判断userDetails是否为空，或者密码与前端传来的密码是否匹配
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        }
        // 判断该用户状态是否启用
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员！");
        }
        // 更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 通过用户名获取用户对象
     *
     * @param username
     * @return
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdmin admin = umsAdminMapper.selectOne(new QueryWrapper<UmsAdmin>().eq("username", username).eq("status", 1));
        if (null == admin) {
            return null;
        }
        return admin;
    }

    @Override
    public RespBean register(AdminLoginParam adminLoginParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setUsername(adminLoginParam.getUsername());
        String encodePassword = passwordEncoder.encode(adminLoginParam.getPassword());
        umsAdmin.setPassword(encodePassword);
        umsAdmin.setStatus(1);
        umsAdmin.setCreateTime(LocalDateTime.now());
        int insert = umsAdminMapper.insert(umsAdmin);
        if (insert == 0) {
            return RespBean.error("注册失败");
        }
        return RespBean.success("注册成功");
    }
}
