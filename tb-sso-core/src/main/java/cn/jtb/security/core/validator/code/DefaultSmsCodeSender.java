package cn.jtb.security.core.validator.code;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jtb
 * @date 2021/5/9 22:58
 */
@Slf4j
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        log.info("手机号：{}, 短信验证码：{} 发送成功！", mobile,  code);
    }

}
