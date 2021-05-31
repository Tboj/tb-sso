package cn.jtb.security.core.properties;

import lombok.Data;

/**
 * @author jtb
 * @date 2021/5/3 22:38
 * @description
 */
@Data
public class ImageCodeProperties {

    // 高
    private int height = 30;
    // 宽
    private int width = 200;
    // 数量
    private int length = 4;
    // 过期时间
    private int expireTime = 10;
    // 拦截的url
    private String url;

}
