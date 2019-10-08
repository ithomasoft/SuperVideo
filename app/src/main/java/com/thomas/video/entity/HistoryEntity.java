package com.thomas.video.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * @author Thomas
 * @describe
 * @date 2019/8/20
 * @updatelog
 * @since
 */
public class HistoryEntity extends LitePalSupport {

    @Column(unique = true)
    private String videoId;
    private String name;
    private String imgUrl;
    private String createTime;
    private int currentEpisode;
    private String currentName;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCurrentEpisode() {
        return currentEpisode;
    }

    public void setCurrentEpisode(int currentEpisode) {
        this.currentEpisode = currentEpisode;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}
