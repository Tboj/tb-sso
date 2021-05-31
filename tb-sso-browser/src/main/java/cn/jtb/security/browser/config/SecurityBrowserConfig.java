package cn.jtb.security.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

import cn.jtb.security.core.config.VerificationCodeFilter;
import cn.jtb.security.core.properties.SecurityProperties;

/**
 * @author cn.jtb
 * @date 2021/4/15 23:11
 * @description
 */
@Configuration
public class SecurityBrowserConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Autowired
    private TbAuthenticationSuccessHandler tbAuthenticationSuccessHandler;
    @Autowired
    private TbAuthenticationFailureHandler tbAuthenticationFailureHandler;
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private DataSource dataSource;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.httpBasic().and().authorizeRequests().anyRequest().permitAll().and().logout().permitAll();
        http.formLogin()
                .loginPage("/authentication/require")
                .loginProcessingUrl("/authentication/form")
                .successHandler(tbAuthenticationSuccessHandler)
                .failureHandler(tbAuthenticationFailureHandler)
                .and()
            .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(myUserDetailService)
                .and()
                //.authenticationProvider(myProvider)
            .authorizeRequests()
                .antMatchers("/authentication/require", securityProperties.getBrowser().getLoginPage(), "/validate/image/code", "/error").permitAll()
                .anyRequest()
                .authenticated().and().csrf().disable();
        VerificationCodeFilter verificationCodeFilter = new VerificationCodeFilter();
        verificationCodeFilter.setAuthenticationFailureHandler(tbAuthenticationFailureHandler);
        verificationCodeFilter.setSecurityProperties(securityProperties);
        verificationCodeFilter.afterPropertiesSet();
        http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
