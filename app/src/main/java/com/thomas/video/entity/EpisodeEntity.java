package com.thomas.video.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * @author Thomas
 * @date 2019/7/4
 * @updatelog
 */
public class EpisodeEntity extends LitePalSupport {
    @Column(unique = true)
    private String name;
    private String onlineUrl;
    private String shareUrl;
    private String downloadUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
