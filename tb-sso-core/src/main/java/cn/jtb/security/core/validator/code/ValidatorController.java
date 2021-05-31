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

/**
 * @author jtb
 * @date 2021/5/2 20:44
 * @description
 */
@RestController
//@Slf4j
public class ValidatorController {
    private static final Logger log = LoggerFactory.getLogger(ValidatorController.class);

    @Resource(name = "imageCodeValidatorGenerator")
    private VerificationCodeGenerator<ImageCode> imageCodeValidatorGenerator;

    @Resource(name = "smsCodeValidatorGenerator")
    private VerificationCodeGenerator<VerificationCode> smsCodeValidatorGenerator;

    @Autowired
    private ImageVerificationCodeProcessor imageVerificationCodeProcessor;

    @Autowired
    private SmsCodeSender smsCodeSender;

    @RequestMapping("/validate/image/code")
    public void imageValidateCode(ServletWebRequest servletWebRequest) throws IOException {
        HttpServletResponse response = servletWebRequest.getResponse();
        setImgResponseContentType(response);
        imageVerificationCodeProcessor.create(servletWebRequest);
        //HttpSession session = request.getSession();
        //// 1.生成验证码
        //ImageCode imageCode = imageCodeValidatorGenerator.generateCode(request);
        //// 2.写到session
        //session.setAttribute(DefaultImageVerificationCodeGenerator.SPRING_SECURITY_IMAGE_VERIFICATION_CODE, imageCode);
        //// 3.发送，写到流里
        //ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @RequestMapping("/validate/sms/code")
    public void smsValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        // 1.生成验证码
        VerificationCode imageCode = smsCodeValidatorGenerator.generateCode(request);
        // 2.写到session
        session.setAttribute(DefaultSmsVerificationCodeGenerator.SPRING_SECURITY_SMS_VERIFICATION_CODE, imageCode);
        // 3.发送，服务商的发送
        String mobile = request.getParameter("mobile");
        smsCodeSender.send(mobile, imageCode.getCode());
    }

    private void setImgResponseContentType(HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
    }

}
