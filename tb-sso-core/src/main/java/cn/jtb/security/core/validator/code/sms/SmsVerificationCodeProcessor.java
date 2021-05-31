package cn.jtb.security.core.validator.code.sms;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;

import cn.jtb.security.core.validator.code.AbstractVerificationCodeProcessor;
import cn.jtb.security.core.validator.code.SmsCodeSender;
import cn.jtb.security.core.validator.code.VerificationCode;
import cn.jtb.security.core.validator.code.VerificationCodeGenerator;

/**
 * @author jtb
 * @date 2021/5/31 23:44
 */
@Component
public class SmsVerificationCodeProcessor extends AbstractVerificationCodeProcessor<VerificationCode> {

    public static final String SPRING_SECURITY_SMS_VERIFICATION_CODE = "SPRING_SECURITY_SMS_CODE";

    @Autowired
    private SmsCodeSender smsCodeSender;

    public SmsVerificationCodeProcessor(VerificationCodeGenerator<VerificationCode> smsCodeValidatorGenerator) {
        super(smsCodeValidatorGenerator);
    }

    @Override
    protected void save(ServletWebRequest servletWebRequest, VerificationCode verificationCode) {
        HttpSession session = servletWebRequest.getRequest().getSession();
        session.setAttribute(SPRING_SECURITY_SMS_VERIFICATION_CODE, verificationCode);
    }

    @Override
    protected void send(ServletWebRequest servletWebRequest, VerificationCode verificationCode) {
        String mobile = servletWebRequest.getRequest().getParameter("mobile");
        if (StringUtils.isBlank(mobile)) {
            throw new RuntimeException("手机号码不能为空");
        }
        smsCodeSender.send(mobile, verificationCode.getCode());
    }
}
