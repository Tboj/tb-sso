package cn.jtb.security.browser.support;

/**
 * @author jtb
 * @date 2021/4/24 22:57
 * @description
 */
public class SimpleResponse {

    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
