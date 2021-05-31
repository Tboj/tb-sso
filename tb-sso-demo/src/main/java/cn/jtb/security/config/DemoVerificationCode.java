package cn.jtb.security.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpServletRequest;

import cn.jtb.security.core.validator.code.ImageCode;
import cn.jtb.security.core.validator.code.VerificationCodeGenerator;

/**
 * @author jtb
 * @date 2021/5/9 15:30
 */
@Component("imageCodeValidatorGenerator")
public class DemoVerificationCode implements VerificationCodeGenerator {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Override
    public ImageCode generateCode(HttpServletRequest request) {
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);
        long expireIn = System.currentTimeMillis() + 10 * 1000;
        ImageCode imageCode = new ImageCode(image, text, expireIn);
        return imageCode;
    }

}
