package cn.jtb.security.core.validator.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jtb
 * @date 2021/5/9 22:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {

    private String code;

    private long expireTime;
}
