package cn.jtb.security.browser.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cn.jtb
 * @date 2021/4/24 15:32
 * @description
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    static Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    static Map<String, String> operates = new HashMap<>();
    static {
        operates.put("jtb", "123");
        operates.put("jtb1", "1234");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        logger.info("登录用户名: {}", username);
        // 从数据查出来的密码，应当在创建用户的时候编码。默认在DaoAuthenticationProvider的additionalAuthenticationChecks()中对输入的密码和该密码进行校验
        // 如果自定义Provider，则需要自己校验密码
        String pass = passwordEncoder.encode("123");
        return new User(username, pass, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
