package cn.jtb.security.core.validator.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author jtb
 * @date 2021/5/2 22:40
 * @description
 */
public class ValidatorException extends AuthenticationException {


    public ValidatorException(String msg) {
        super(msg);
    }
}
