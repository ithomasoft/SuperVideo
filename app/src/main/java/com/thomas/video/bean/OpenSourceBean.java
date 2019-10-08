package com.thomas.video.bean;

/**
 * @author Thomas
 * @describe
 * @date 2019/8/20
 * @updatelog
 * @since
 */
public class OpenSourceBean {
    private String name;
    private String url;

    public OpenSourceBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
