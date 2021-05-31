package cn.jtb.security.core.validator.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author jtb
 * @date 2021/5/24 23:32
 */
public abstract class AbstractVerificationCodeProcessor<C extends VerificationCode> implements VerificationCodeProcessor<C> {

    protected VerificationCodeGenerator<C> verificationCodeGenerator;

    @Override
    public C create(ServletWebRequest servletWebRequest) {
        if (verificationCodeGenerator == null) {
            throw new RuntimeException("缺少验证码生成器");
        }
        // 1.生成
        C verificationCode = verificationCodeGenerator.generateCode(servletWebRequest.getRequest());
        // 2.保存
        save(servletWebRequest, verificationCode);
        // 3.发送
        send(servletWebRequest, verificationCode);
        return verificationCode;
    }

    protected abstract void save(ServletWebRequest servletWebRequest, C c);

    protected abstract void send(ServletWebRequest servletWebRequest, C c);

}
