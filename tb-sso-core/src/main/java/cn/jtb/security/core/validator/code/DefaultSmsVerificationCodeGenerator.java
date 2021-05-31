package cn.jtb.security.core.validator.code;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import cn.jtb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jtb
 * @date 2021/5/9 22:48
 */
@Slf4j
public class DefaultSmsVerificationCodeGenerator implements VerificationCodeGenerator<VerificationCode> {

    @Autowired
    private SecurityProperties securityProperties;
    public static final String SPRING_SECURITY_SMS_VERIFICATION_CODE = "SPRING_SECURITY_IMAGE_SMS_CODE";

    @Override
    public VerificationCode generateCode(HttpServletRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getValidatorCode().getSms().getLength());
        log.info("短信验证码：{}", code);
        long expireIn = System.currentTimeMillis() + 10 * 1000;
        return new VerificationCode(code, expireIn);
    }

}
