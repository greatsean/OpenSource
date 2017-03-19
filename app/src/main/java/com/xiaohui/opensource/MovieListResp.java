package com.xiaohui.opensource;

import java.util.List;

/**
 * Created by xiaohui on 2017/1/21.
 */

public class MovieListResp {
    private String ret_code;
    private String msg;
    private List<DataBean> data;

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MovieListResp{" +
                "ret_code='" + ret_code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * id : 178384393
         * movieid : 178384393
         * moviename : 谍影重重5
         * language : null
         * type : 动作/悬疑/惊悚
         * director : 保罗·格林格拉斯
         * actors : 马特·达蒙 / 朱丽娅·斯蒂尔斯 / 艾丽西亚·维坎德 / 汤米·李·琼斯 / 文森特·卡索 / 里兹·阿迈德 / 内芙·加切夫 / 比尔·坎普 / 斯科特· 谢泼德
         * length : 120
         * highlight : null
         * logo : http://static.wepiao.com/movie/2016/4/21_0/201604211448224890.jpg
         * releasedate : 1472745600000
         * generalmark : 6.8
         * gcedition :
         * state : 美国
         * englishname : Jason Bourne
         * todayOpis : null
         * videoUrl : http://v.qq.com/iframe/player.html?vid=m030619uzr6&tt=《谍影重重5》动作预告 马特达蒙陷激烈枪战
         */

        private long id;
        private String movieid;
        private String moviename;
        private Object language;
        private String type;
        private String director;
        private String actors;
        private String length;
        private Object highlight;
        private String logo;
        private long releasedate;
        private String generalmark;
        private String gcedition;
        private String state;
        private String englishname;
        private Object todayOpis;
        private String videoUrl;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMovieid() {
            return movieid;
        }

        public void setMovieid(String movieid) {
            this.movieid = movieid;
        }

        public String getMoviename() {
            return moviename;
        }

        public void setMoviename(String moviename) {
            this.moviename = moviename;
        }

        public Object getLanguage() {
            return language;
        }

        public void setLanguage(Object language) {
            this.language = language;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActors() {
            return actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public Object getHighlight() {
            return highlight;
        }

        public void setHighlight(Object highlight) {
            this.highlight = highlight;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public long getReleasedate() {
            return releasedate;
        }

        public void setReleasedate(long releasedate) {
            this.releasedate = releasedate;
        }

        public String getGeneralmark() {
            return generalmark;
        }

        public void setGeneralmark(String generalmark) {
            this.generalmark = generalmark;
        }

        public String getGcedition() {
            return gcedition;
        }

        public void setGcedition(String gcedition) {
            this.gcedition = gcedition;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getEnglishname() {
            return englishname;
        }

        public void setEnglishname(String englishname) {
            this.englishname = englishname;
        }

        public Object getTodayOpis() {
            return todayOpis;
        }

        public void setTodayOpis(Object todayOpis) {
            this.todayOpis = todayOpis;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", movieid='" + movieid + '\'' +
                    ", moviename='" + moviename + '\'' +
                    ", language=" + language +
                    ", type='" + type + '\'' +
                    ", director='" + director + '\'' +
                    ", actors='" + actors + '\'' +
                    ", length='" + length + '\'' +
                    ", highlight=" + highlight +
                    ", logo='" + logo + '\'' +
                    ", releasedate=" + releasedate +
                    ", generalmark='" + generalmark + '\'' +
                    ", gcedition='" + gcedition + '\'' +
                    ", state='" + state + '\'' +
                    ", englishname='" + englishname + '\'' +
                    ", todayOpis=" + todayOpis +
                    ", videoUrl='" + videoUrl + '\'' +
                    '}';
        }
    }
}
