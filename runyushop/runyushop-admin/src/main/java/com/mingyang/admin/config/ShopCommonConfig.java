package com.mingyang.admin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mingyang
 * 去runyushop-common中扫描配置相关组件
 */
@Configuration
@ComponentScan({"com.mingyang.common"})
public class ShopCommonConfig {
}
