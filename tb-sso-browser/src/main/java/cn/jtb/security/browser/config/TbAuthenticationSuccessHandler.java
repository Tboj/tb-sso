package cn.jtb.security.browser.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jtb.security.browser.support.SimpleResponse;
import cn.jtb.security.core.properties.LoginType;
import cn.jtb.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;


/**
 * @author jtb
 * @date 2021/4/29 22:40
 * @description
 */
@Slf4j
@Component
public class TbAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(objectMapper.writeValueAsString(new SimpleResponse(authentication.getPrincipal())));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

}
