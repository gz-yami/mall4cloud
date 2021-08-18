package com.mall4j.cloud.auth.config;

import cn.hutool.core.io.FileUtil;
import com.anji.captcha.model.common.CaptchaTypeEnum;
import com.anji.captcha.model.common.Const;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author FrozenWatermelon
 * @date 2020/8/12
 */
@Configuration
public class CaptchaConfig {

	@Bean
	public CaptchaService captchaService() {
		Properties config = new Properties();
		config.put(Const.CAPTCHA_CACHETYPE, "redis");
		config.put(Const.CAPTCHA_WATER_MARK, "");
		// 滑动验证
		config.put(Const.CAPTCHA_TYPE, CaptchaTypeEnum.BLOCKPUZZLE.getCodeValue());
	//	config.put(Const.ORIGINAL_PATH_JIGSAW, FileUtil.getAbsolutePath("classpath:captcha"));
		return CaptchaServiceFactory.getInstance(config);
	}

}
