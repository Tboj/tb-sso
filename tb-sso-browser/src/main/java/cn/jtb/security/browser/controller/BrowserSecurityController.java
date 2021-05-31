package cn.jtb.security.browser.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jtb.security.browser.support.SimpleResponse;
import cn.jtb.security.core.properties.SecurityProperties;


/**
 * @author jtb
 * @date 2021/4/24 22:43
 * @description
 */
@RestController
public class BrowserSecurityController {
    static Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);

    // 拿到引发跳转的请求：保存请求到session，登录后跳转
    private final RequestCache requestCache = new HttpSessionRequestCache();
    // 重定向
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获重定向到此控制器前的URL是什么。并判断跳转的是html还是状态码
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的路径是：{}", redirectUrl);
            // 如果是html请求则跳转到登陆页，否则返回401
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证， 请引导用户到登陆页");

    }

}
