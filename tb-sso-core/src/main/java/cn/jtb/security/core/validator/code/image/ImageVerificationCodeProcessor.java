package cn.jtb.security.core.validator.code.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jtb.security.core.validator.code.AbstractVerificationCodeProcessor;
import cn.jtb.security.core.validator.code.DefaultImageVerificationCodeGenerator;
import cn.jtb.security.core.validator.code.ImageCode;
import cn.jtb.security.core.validator.code.VerificationCodeGenerator;

/**
 * @author jtb
 * @date 2021/5/24 23:51
 */
@Component
public class ImageVerificationCodeProcessor extends AbstractVerificationCodeProcessor<ImageCode> {

    public ImageVerificationCodeProcessor(@Qualifier("imageCodeValidatorGenerator") VerificationCodeGenerator<ImageCode> imageCodeValidatorGenerator) {
        this.verificationCodeGenerator = imageCodeValidatorGenerator;
    }

    @Override
    protected void save(ServletWebRequest servletWebRequest, ImageCode imageCode) {
        HttpSession session = servletWebRequest.getRequest().getSession();
        // 2.写到session
        session.setAttribute(DefaultImageVerificationCodeGenerator.SPRING_SECURITY_IMAGE_VERIFICATION_CODE, imageCode);
    }

    @Override
    protected void send(ServletWebRequest servletWebRequest, ImageCode imageCode) {
        HttpServletResponse response = servletWebRequest.getResponse();
        try {
            assert response != null;
            ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
