package cn.jtb.security.core.validator.code;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jtb
 * @date 2021/5/9 00:02
 * @author jtb
 */
public interface VerificationCodeGenerator<T extends VerificationCode> {

    T generateCode(HttpServletRequest request);

}
