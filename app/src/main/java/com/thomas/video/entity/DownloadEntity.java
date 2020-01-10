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

    @Column(unique = true)
    private int taskId;

    private int progress;
    private long speed;
    private int soFarBytes;
    private int totalFarBytes;
    private int status;

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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public int getSoFarBytes() {
        return soFarBytes;
    }

    public void setSoFarBytes(int soFarBytes) {
        this.soFarBytes = soFarBytes;
    }

    public int getTotalFarBytes() {
        return totalFarBytes;
    }

    public void setTotalFarBytes(int totalFarBytes) {
        this.totalFarBytes = totalFarBytes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
