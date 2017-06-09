package com.grandmagic.edustore.protocol;

/**
 * Created by lps on 2017/6/9.
 *
 * @version 1
 * @see
 * @since 2017/6/9 15:32
 */


public class CommentResponse {

    /**
     * data : {"commentInfo":{"comment_id":"24","username":"18228170109","target_username":"","comment_content":"23","show_name":null,"show_target_name":"","news_id":"24"}}
     * status : {"succeed":1}
     */

    private DataBean data;
    private StatusBean status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * commentInfo : {"comment_id":"24","username":"18228170109","target_username":"","comment_content":"23","show_name":null,"show_target_name":"","news_id":"24"}
         */

        private CommentInfoBean commentInfo;

        public CommentInfoBean getCommentInfo() {
            return commentInfo;
        }

        public void setCommentInfo(CommentInfoBean commentInfo) {
            this.commentInfo = commentInfo;
        }

        public static class CommentInfoBean {
            /**
             * comment_id : 24
             * username : 18228170109
             * target_username :
             * comment_content : 23
             * show_name : null
             * show_target_name :
             * news_id : 24
             */

            private String comment_id;
            private String username;
            private String target_username;
            private String comment_content;
            private String show_name;
            private String show_target_name;
            private String news_id;

            public String getComment_id() {
                return comment_id;
            }

            public void setComment_id(String comment_id) {
                this.comment_id = comment_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getTarget_username() {
                return target_username;
            }

            public void setTarget_username(String target_username) {
                this.target_username = target_username;
            }

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public String getShow_name() {
                return show_name;
            }

            public void setShow_name(String show_name) {
                this.show_name = show_name;
            }

            public String getShow_target_name() {
                return show_target_name;
            }

            public void setShow_target_name(String show_target_name) {
                this.show_target_name = show_target_name;
            }

            public String getNews_id() {
                return news_id;
            }

            public void setNews_id(String news_id) {
                this.news_id = news_id;
            }
        }
    }

    public static class StatusBean {
        /**
         * succeed : 1
         */

        private int succeed;

        public int getSucceed() {
            return succeed;
        }

        public void setSucceed(int succeed) {
            this.succeed = succeed;
        }
    }
}
