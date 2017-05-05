package com.grandmagic.edustore.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lps on 2017/5/5.
 *
 * @version 1
 * @see
 * @since 2017/5/5 13:09
 */


public class NewsList {

    /**
     * code : 200
     * message : 获取新闻资讯成功
     * data : {"total_num":"3","num":3,"info":[{"news_id":"2","title":"青年榜样","sketch":"五四青年节","created_at":"2017-05-04 10:54:12","updated_at":"2017-05-04 11:02:20","banner":[{"banner_id":"9","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/1102021146110246978712200626A.jpg"}]},{"news_id":"3","title":"test","sketch":"测试","created_at":"2017-05-04 11:03:10","updated_at":"2017-05-04 11:27:11","banner":[{"banner_id":"13","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/2ccb00a1f5454e438b851b4949b1db6a.jpg"}]},{"news_id":"4","title":"成都拉面小哥重回黄龙溪拉面","sketch":"成都拉面小哥走红后辞职不足两月 重回黄龙溪拉面","created_at":"2017-05-04 11:24:28","updated_at":"2017-05-04 17:23:35","banner":[{"banner_id":"16","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/46922228027d42f5afc8f47fa22e2055.jpg"}]}]}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * total_num : 3
         * num : 3
         * info : [{"news_id":"2","title":"青年榜样","sketch":"五四青年节","created_at":"2017-05-04 10:54:12","updated_at":"2017-05-04 11:02:20","banner":[{"banner_id":"9","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/1102021146110246978712200626A.jpg"}]},{"news_id":"3","title":"test","sketch":"测试","created_at":"2017-05-04 11:03:10","updated_at":"2017-05-04 11:27:11","banner":[{"banner_id":"13","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/2ccb00a1f5454e438b851b4949b1db6a.jpg"}]},{"news_id":"4","title":"成都拉面小哥重回黄龙溪拉面","sketch":"成都拉面小哥走红后辞职不足两月 重回黄龙溪拉面","created_at":"2017-05-04 11:24:28","updated_at":"2017-05-04 17:23:35","banner":[{"banner_id":"16","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/46922228027d42f5afc8f47fa22e2055.jpg"}]}]
         */


        private int total_page;
        private ArrayList<InfoBean> info;

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int mTotal_page) {
            total_page = mTotal_page;
        }

        public ArrayList<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(ArrayList<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * news_id : 2
             * title : 青年榜样
             * sketch : 五四青年节
             * created_at : 2017-05-04 10:54:12
             * updated_at : 2017-05-04 11:02:20
             * banner : [{"banner_id":"9","banner_url":"http://192.168.1.115/huishiwang/backend/web/news_image/20170504/1102021146110246978712200626A.jpg"}]
             */

            private String news_id;
            private String title;
            private String sketch;
            private String created_at;
            private String updated_at;
            private BannerBean banner;

            public String getNews_id() {
                return news_id;
            }

            public void setNews_id(String news_id) {
                this.news_id = news_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSketch() {
                return sketch;
            }

            public void setSketch(String sketch) {
                this.sketch = sketch;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public BannerBean getBanner() {
                return banner;
            }

            public void setBanner(BannerBean banner) {
                this.banner = banner;
            }

            public static class BannerBean {
                /**
                 * banner_id : 9
                 * banner_url : http://192.168.1.115/huishiwang/backend/web/news_image/20170504/1102021146110246978712200626A.jpg
                 */

                private String banner_id;
                private String banner_url;

                public String getBanner_id() {
                    return banner_id;
                }

                public void setBanner_id(String banner_id) {
                    this.banner_id = banner_id;
                }

                public String getBanner_url() {
                    return banner_url;
                }

                public void setBanner_url(String banner_url) {
                    this.banner_url = banner_url;
                }
            }
        }
    }
}
