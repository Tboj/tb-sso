package cn.jtb.security.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.jtb.security.core.properties.SecurityProperties;
import cn.jtb.security.core.validator.code.DefaultSmsCodeSender;
import cn.jtb.security.core.validator.code.DefaultSmsVerificationCodeGenerator;
import cn.jtb.security.core.validator.code.ImageCode;
import cn.jtb.security.core.validator.code.SmsCodeSender;
import cn.jtb.security.core.validator.code.VerificationCode;
import cn.jtb.security.core.validator.code.VerificationCodeGenerator;
import cn.jtb.security.core.validator.code.DefaultImageVerificationCodeGenerator;

/**
 * @author jtb
 * @date 2021/5/9 00:12
 * @description
 */
@Configuration
public class CoreConfiguration {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeValidatorGenerator")
    public VerificationCodeGenerator<ImageCode> imageCodeValidatorGenerator() {
        DefaultImageVerificationCodeGenerator simpleImageCodeValidatorGenerator = new DefaultImageVerificationCodeGenerator();
        simpleImageCodeValidatorGenerator.setSecurityProperties(securityProperties);
        return simpleImageCodeValidatorGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsCodeValidatorGenerator")
    public VerificationCodeGenerator<VerificationCode> smsCodeValidatorGenerator() {
        DefaultSmsVerificationCodeGenerator simpleImageCodeValidatorGenerator = new DefaultSmsVerificationCodeGenerator();
        return simpleImageCodeValidatorGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
