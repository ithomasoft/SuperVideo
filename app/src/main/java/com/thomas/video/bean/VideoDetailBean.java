package com.thomas.video.bean;

import com.thomas.video.entity.EpisodeEntity;

import java.util.List;

public class VideoDetailBean {
    private String imgUrl;//海报地址
    private String name;//名称-
    private String score;//评分
    private String alias;//别名
    private String type;//类型
    private String language;//语言
    private String time;//片长
    private String date;//上映日期
    private String director;//导演-
    private String stars;//主演-
    private String area;//地区
    private String introduction;//简介-
    private List<EpisodeEntity> episodeList;//剧集

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<EpisodeEntity> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<EpisodeEntity> episodeList) {
        this.episodeList = episodeList;
    }

}
