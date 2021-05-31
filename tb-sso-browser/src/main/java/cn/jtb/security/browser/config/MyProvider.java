package cn.jtb.security.browser.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;


/**
 * @author cn.jtb
 * @date 2021/4/24 14:30
 * @description
 */
//@Component
public class MyProvider implements AuthenticationProvider {

    static Map<String, String> operates = new HashMap<>();
    static {
        operates.put("jtb", "123");
        operates.put("jtb1", "1234");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authentication
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) token.getPrincipal();
        String password = (String) token.getCredentials();
        if (operates.get(username) != null && operates.get(username).equals(password)) {
            return token;
        } else {
            throw new SecurityException("账号或密码不正确");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
