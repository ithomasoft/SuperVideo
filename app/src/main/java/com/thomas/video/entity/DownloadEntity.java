package com.thomas.video.entity;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/8
 * @updatelog
 * @since
 */
public class DownloadEntity extends LitePalSupport {
    private String title;//文件名
    private String imgUrl;//文件预览图片
    @Column(unique = true)
    private String downloadUrl;//下载地址
    private String createDate;//开始下载时间
    private String finishDate;//下载完成时间
    private int state;//当前下载状态

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
