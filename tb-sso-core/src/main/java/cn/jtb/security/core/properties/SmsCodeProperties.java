package cn.jtb.security.core.properties;

import lombok.Data;

/**
 * @author jtb
 * @date 2021/5/3 22:38
 * @description
 */
@Data
public class SmsCodeProperties {

    // 数量
    private int length = 6;
    // 过期时间
    private int expireTime = 10;
    // 拦截的url
    private String url;

}
