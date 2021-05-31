package cn.jtb.security.core.validator.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author jtb
 * @date 2021/5/24 23:29
 */
public interface VerificationCodeProcessor<C> {

    C create(ServletWebRequest servletWebRequest);

}
