package cn.jtb.security.core.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jtb.security.core.properties.SecurityProperties;
import cn.jtb.security.core.validator.code.ImageCode;
import cn.jtb.security.core.validator.code.ValidatorException;
import cn.jtb.security.core.validator.code.DefaultImageVerificationCodeGenerator;

/**
 * @author jtb
 * @date 2021/5/2 22:29
 * @description
 */
public class VerificationCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    private SecurityProperties securityProperties;

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    private final Set<String> urlSet = new HashSet<>();

    /**
     * 等bean都实例化后执行这个方法
     * @throws ServletException
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String url = securityProperties.getValidatorCode().getImage().getUrl();
        if (StringUtils.isNotBlank(url)) {
            urlSet.addAll(Arrays.asList(url.split(",")));
        }
        urlSet.add("/authentication/form");
    }

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 传进来的验证码
        String verificationCodeFromReq = request.getParameter("verificationCode");
        // 获取从获取验证码时塞到session的的验证码
        Object verificationCodeFromSession = request.getSession().getAttribute(DefaultImageVerificationCodeGenerator.SPRING_SECURITY_IMAGE_VERIFICATION_CODE);
        // 判断请求的uri是否是需要拦截的uri
        boolean action = false;
        for (String s : urlSet) {
            if (pathMatcher.match(s, request.getRequestURI())) {
                action = true;
            }
        }
        if (action) {
            try {
                verifyCode(verificationCodeFromReq, verificationCodeFromSession);
            } catch (ValidatorException e) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void verifyCode(String verificationCodeFromReq, Object verificationCodeFromSession) {
        if (StringUtils.isBlank(verificationCodeFromReq)) {
            throw new ValidatorException("验证码不可为空");
        }
        if (verificationCodeFromSession == null) {
            throw new ValidatorException("验证码无效，请重新获取验证码");
        }
        ImageCode imageCode = (ImageCode) verificationCodeFromSession;
        if (!verificationCodeFromReq.equalsIgnoreCase(imageCode.getCode())) {
            throw new ValidatorException("验证码不正确，请重新输入");
        }
        if (imageCode.getExpireTime() < System.currentTimeMillis()) {
            throw new ValidatorException("验证码已过期");
        }
    }

}
