package com.thomas.video.helper;

import com.thomas.video.bean.SearchResultBean;
import com.thomas.video.bean.VideoDetailBean;
import com.thomas.video.entity.EpisodeEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas
 * @date 2019/6/28
 * @updatelog
 */
public class JsoupHelper {
    public static SearchResultBean parseSearchResult(String result) throws Exception{
        SearchResultBean resultBean = new SearchResultBean();
        Document doc = Jsoup.parse(result);
        Element element = doc.getElementsByClass("pages").last();
        SearchResultBean.PageBean pageBean = new SearchResultBean.PageBean();
        pageBean.setPagecount(element.getElementsByClass("pagelink_b").size() + 1);
        pageBean.setPageindex(Integer.parseInt(element.getElementsByClass("pagenow").first().html()));
        List<Element> elements = doc.getElementsByClass("tt");
        List<SearchResultBean.DataBean> datas = new ArrayList<>();
        for (Element e : elements) {
            SearchResultBean.DataBean dataBean = new SearchResultBean.DataBean();
            dataBean.setUpdateTime(e.parent().getElementsByClass("xing_vb6").text());
            dataBean.setType(e.parent().getElementsByClass("xing_vb5").text());
            String url = e.parent().getElementsByClass("xing_vb4").select("a").attr("href");
            dataBean.setTargetUrl(url.split("=")[1]);
            dataBean.setId(url.split("-")[3].replace(".html", ""));
            String title = e.parent().getElementsByClass("xing_vb4").text().trim().replace("中字", "").replace("高清", "").replace("泰语", "");
            if (title.contains("HD")) {
                dataBean.setSharp("HD");
                if (title.contains("更新至：")) {
                    dataBean.setLast(title.split("更新至：")[1]);
                    dataBean.setName(title.replace("更新至：", "-").split("-")[0].trim().replace("HD", ""));
                } else if (title.contains("更新至")) {
                    dataBean.setLast(title.split("更新至")[1]);
                    dataBean.setName(title.replace("更新至", "-").split("-")[0].trim().replace("HD", ""));
                } else if (title.contains("完结")) {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("完结", "").replace("HD", ""));
                } else {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("HD", "").trim());
                }
            } else if (title.contains("DVD")) {
                dataBean.setSharp("DVD");
                if (title.contains("更新至：")) {
                    dataBean.setLast(title.split("更新至：")[1]);
                    dataBean.setName(title.replace("更新至：", "-").split("-")[0].trim().replace("DVD", ""));
                } else if (title.contains("更新至")) {
                    dataBean.setLast(title.split("更新至")[1]);
                    dataBean.setName(title.replace("更新至", "-").split("-")[0].trim().replace("DVD", ""));
                } else if (title.contains("完结")) {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("完结", "").replace("DVD", ""));
                } else {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("DVD", "").trim());
                }
            } else if (title.contains("TS")) {
                dataBean.setSharp("TS");
                if (title.contains("更新至：")) {
                    dataBean.setLast(title.split("更新至：")[1]);
                    dataBean.setName(title.replace("更新至：", "-").split("-")[0].trim().replace("TS", ""));
                } else if (title.contains("更新至")) {
                    dataBean.setLast(title.split("更新至")[1]);
                    dataBean.setName(title.replace("更新至", "-").split("-")[0].trim().replace("TS", ""));
                } else if (title.contains("完结")) {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("完结", "").replace("TS", ""));
                } else {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("TS", "").trim());
                }
            } else if (title.contains("BD")) {
                dataBean.setSharp("BD");
                if (title.contains("更新至：")) {
                    dataBean.setLast(title.split("更新至：")[1]);
                    dataBean.setName(title.replace("更新至：", "-").split("-")[0].trim().replace("BD", ""));
                } else if (title.contains("更新至")) {
                    dataBean.setLast(title.split("更新至")[1]);
                    dataBean.setName(title.replace("更新至", "-").split("-")[0].trim().replace("BD", ""));
                } else if (title.contains("完结")) {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("完结", "").replace("BD", ""));
                } else {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("BD", "").trim());
                }
            } else {
                dataBean.setSharp("");
                if (title.contains("更新至：")) {
                    dataBean.setLast(title.split("更新至：")[1]);
                    dataBean.setName(title.replace("更新至：", "-").split("-")[0].trim());
                } else if (title.contains("更新至")) {
                    dataBean.setLast(title.split("更新至")[1]);
                    dataBean.setName(title.replace("更新至", "-").split("-")[0].trim());
                } else if (title.contains("完结")) {
                    dataBean.setLast("完结");
                    dataBean.setName(title.replace("完结", ""));
                } else {
                    dataBean.setLast("完结");
                    dataBean.setName(title.trim());
                }
            }


            datas.add(dataBean);
        }

        resultBean.setPage(pageBean);
        resultBean.setData(datas);

        return resultBean;
    }

    public static VideoDetailBean parseVideoDetail(String detail) throws Exception{
        VideoDetailBean detailBean = new VideoDetailBean();
        Document doc = Jsoup.parse(detail);
        detailBean.setImgUrl(doc.getElementsByClass("lazy").attr("src"));
        detailBean.setIntroduction(doc.getElementsByClass("vodplayinfo").text().split("播放类型")[0]);


        detailBean.setName(Selector.select("h2", doc.getElementsByClass("vodh")).text());
        detailBean.setScore(Selector.select("label", doc.getElementsByClass("vodh")).text());
        List<Element> content = Selector.select("li", doc.getElementsByClass("vodinfobox"));
        detailBean.setAlias(content.get(0).text());
        detailBean.setDirector(content.get(1).text());
        detailBean.setStars(content.get(2).text());
        detailBean.setType(content.get(3).text());
        detailBean.setArea(content.get(4).text());
        detailBean.setLanguage(content.get(5).text());
        detailBean.setDate(content.get(6).text());
        detailBean.setTime(content.get(7).text());
        List<EpisodeEntity> episodes = new ArrayList<>();

        Elements online = doc.getElementById("1").select("li");
        Elements share = doc.getElementById("2").select("li");
        Element download = doc.getElementById("down_1");
        boolean hasDownload;
        if (download == null) {
            hasDownload = false;
        } else if (download.select("li").size() == 0) {
            hasDownload = false;
        } else {
            hasDownload = true;
        }
        for (int i = 0; i < online.text().split(" ").length; i++) {
            EpisodeEntity episode = new EpisodeEntity();
            episode.setName(online.text().split(" ")[i].split("\\$")[0]);

            if (online.text().split(" ")[i].endsWith("m3u8")) {
                episode.setOnlineUrl(online.text().split(" ")[i].split("\\$")[1]);
                episode.setShareUrl(share.text().split(" ")[i].split("\\$")[1]);
            } else {
                episode.setOnlineUrl(share.text().split(" ")[i].split("\\$")[1]);
                episode.setShareUrl(online.text().split(" ")[i].split("\\$")[1]);
            }


            if (hasDownload) {
                episode.setDownloadUrl(download.select("li").text().split("\\$")[1]);
            } else {
                episode.setDownloadUrl("");
            }
            episodes.add(episode);
        }
        detailBean.setEpisodeList(episodes);
        return detailBean;
    }
}
