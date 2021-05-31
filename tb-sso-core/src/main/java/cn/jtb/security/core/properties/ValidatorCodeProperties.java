package cn.jtb.security.core.properties;

import lombok.Data;

/**
 * @author jtb
 * @date 2021/5/3 22:57
 * @description
 */
@Data
public class ValidatorCodeProperties {

    private ImageCodeProperties image;

    private SmsCodeProperties sms;

}
