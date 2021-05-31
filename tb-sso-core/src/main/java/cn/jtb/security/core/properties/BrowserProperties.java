package cn.jtb.security.core.properties;

/**
 * @author jtb
 * @date 2021/4/24 23:02
 * @description
 */
public class BrowserProperties {

    private String loginPage = "/tb-signIn.html";

    private LoginType loginType = LoginType.JSON;

    private int rememberMeSeconds = 10 * 60;

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
