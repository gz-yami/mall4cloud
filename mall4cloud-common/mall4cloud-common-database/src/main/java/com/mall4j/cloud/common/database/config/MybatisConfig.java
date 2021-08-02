package com.mall4j.cloud.common.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author FrozenWatermelon
 * @date 2020/6/24
 */
@Configuration
@MapperScan({ "com.mall4j.cloud.**.mapper" })
public class MybatisConfig {


}
