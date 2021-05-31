package cn.jtb.security.core.validator.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jtb.security.core.validator.code.image.ImageVerificationCodeProcessor;
import cn.jtb.security.core.validator.code.sms.SmsVerificationCodeProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jtb
 * @date 2021/5/2 20:44
 * @description
 */
@RestController
@Slf4j
public class ValidatorController {

    @Autowired
    private ImageVerificationCodeProcessor imageVerificationCodeProcessor;


    @Autowired
    private SmsVerificationCodeProcessor smsVerificationCodeProcessor;

    @RequestMapping("/validate/image/code")
    public void imageValidateCode(ServletWebRequest servletWebRequest) throws IOException {
        HttpServletResponse response = servletWebRequest.getResponse();
        setImgResponseContentType(response);
        imageVerificationCodeProcessor.create(servletWebRequest);
    }

    @RequestMapping("/validate/sms/code")
    public void smsValidateCode(ServletWebRequest servletWebRequest) throws IOException {
        smsVerificationCodeProcessor.create(servletWebRequest);
    }

    private void setImgResponseContentType(HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
    }

}
