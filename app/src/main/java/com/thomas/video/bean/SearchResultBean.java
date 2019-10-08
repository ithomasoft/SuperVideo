package com.thomas.video.bean;

import java.util.List;

/**
 * @author Thomas
 * @date 2019/6/28
 * @updatelog
 */
public class SearchResultBean {


    /**
     * page : {"pageindex":"1","pagecount":"1","pagesize":"30","recordcount":"1"}
     * data : [{"vod_id":"30831","vod_cid":"10","vod_name":"老师好","vod_title":"","vod_type":"","vod_keywords":"","vod_actor":"于谦 / 汤梦佳 / 王广源 / 秦鸣悦 / 徐子力","vod_director":"张栾","vod_content":"1985年的南宿一中，苗宛秋老师推自行车昂首走在校园，接受着人们艳羡的目光和纷至沓来的恭维。桀傲不驯的洛小乙、温婉可人的安静、新潮前卫的关婷婷、大智若愚的脑袋、舞痴兄弟文明建设、八婆海燕、奸商耗子\u2026\u2026三班是一个永远也不缺故事的集体。苗宛秋怎么也不会想到，他即将走进的这个三班将会成为他以及他身边这辆自行车的噩梦。三班的同学也没有想到，这位新来的老师改变了他们的一生。","vod_pic":"https://img.pic-imges.com/pic/upload/vod/2019-03/201903251553510979.jpg","vod_area":"大陆","vod_language":null,"vod_year":"2019","vod_addtime":"2019-05-01 22:04:52","vod_filmtime":0,"vod_server":"","vod_play":"kuyun$$$ckm3u8","vod_url":"HD中字$https://youku.com-okzy.com/share/0777d5c17d4066b82ab86dff8a46af6f$$$HD中字$https://youku.com-okzy.com/20190501/163_d35d02c7/index.m3u8","vod_inputer":null,"vod_reurl":"http://www.okzyzy.com/?m=vod-detail-id-30831.html","vod_length":0,"vod_weekday":null,"vod_copyright":0,"vod_state":"","vod_version":"","vod_tv":"","vod_total":0,"vod_continu":"HD","vod_status":1,"vod_stars":0,"vod_hits":null,"vod_isend":1,"vod_douban_id":0,"vod_series":"","list_name":"剧情片"}]
     * list : [{"list_id":1,"list_name":"电影片"},{"list_id":2,"list_name":"连续剧"},{"list_id":3,"list_name":"综艺片"},{"list_id":4,"list_name":"动漫片"},{"list_id":5,"list_name":"动作片"},{"list_id":6,"list_name":"喜剧片"},{"list_id":7,"list_name":"爱情片"},{"list_id":8,"list_name":"科幻片"},{"list_id":9,"list_name":"恐怖片"},{"list_id":10,"list_name":"剧情片"},{"list_id":11,"list_name":"战争片"},{"list_id":12,"list_name":"国产剧"},{"list_id":13,"list_name":"香港剧"},{"list_id":14,"list_name":"韩国剧"},{"list_id":15,"list_name":"欧美剧"},{"list_id":16,"list_name":"台湾剧"},{"list_id":17,"list_name":"日本剧"},{"list_id":18,"list_name":"海外剧"},{"list_id":19,"list_name":"纪录片"},{"list_id":20,"list_name":"微电影"},{"list_id":21,"list_name":"伦理片"},{"list_id":22,"list_name":"福利片"},{"list_id":23,"list_name":"国产动漫"},{"list_id":24,"list_name":"日韩动漫"},{"list_id":25,"list_name":"欧美动漫"},{"list_id":26,"list_name":"内地综艺"},{"list_id":27,"list_name":"港台综艺"},{"list_id":28,"list_name":"日韩综艺"},{"list_id":29,"list_name":"欧美综艺"},{"list_id":31,"list_name":"港台动漫"},{"list_id":32,"list_name":"海外动漫"}]
     */

    private PageBean page;
    private List<DataBean> data;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageBean {
        /**
         * pageindex : 1
         * pagecount : 1
         */

        private int pageindex;
        private int pagecount;

        public int getPageindex() {
            return pageindex;
        }

        public void setPageindex(int pageindex) {
            this.pageindex = pageindex;
        }

        public int getPagecount() {
            return pagecount;
        }

        public void setPagecount(int pagecount) {
            this.pagecount = pagecount;
        }

    }

    public static class DataBean {
      private String id;
      private String name;
      private String type;
      private String updateTime;
      private String targetUrl;
      private String sharp;
      private String last;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getTargetUrl() {
            return targetUrl;
        }

        public void setTargetUrl(String targetUrl) {
            this.targetUrl = targetUrl;
        }

        public String getSharp() {
            return sharp;
        }

        public void setSharp(String sharp) {
            this.sharp = sharp;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }

}
