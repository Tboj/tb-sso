package cn.jtb.security.core.validator.code;

/**
 * @author jtb
 * @date 2021/5/9 22:56
 */
public interface SmsCodeSender {

    void send(String mobile, String code);

}
