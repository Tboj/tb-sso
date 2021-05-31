package cn.jtb.security.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.jtb.security.core.properties.SecurityProperties;

/**
 * @author jtb
 * @date 2021/4/24 23:07
 * @description
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
