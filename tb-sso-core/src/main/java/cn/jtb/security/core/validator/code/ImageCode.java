package cn.jtb.security.core.validator.code;

import java.awt.image.BufferedImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author jtb
 * @date 2021/5/2 20:32
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageCode extends VerificationCode {

    private BufferedImage image;

    public ImageCode() {
    }

    public ImageCode(BufferedImage image, String code, long expireTime) {
        super(code, expireTime);
        this.image = image;
    }
}
