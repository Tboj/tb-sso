package cn.jtb.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jtb
 * @date 2021/4/24 23:06
 * @description
 */
@ConfigurationProperties(prefix = "tb.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private ValidatorCodeProperties validatorCode = new ValidatorCodeProperties();;

    public ValidatorCodeProperties getValidatorCode() {
        return validatorCode;
    }

    public void setValidatorCode(ValidatorCodeProperties validatorCode) {
        this.validatorCode = validatorCode;
    }

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
