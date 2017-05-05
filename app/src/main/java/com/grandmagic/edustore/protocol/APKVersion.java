package com.grandmagic.edustore.protocol;

/**
 * Created by lps on 2017/5/5.
 *
 * @version 1
 * @see
 * @since 2017/5/5 16:04
 */


public class APKVersion {

    /**
     * code : 200
     * message : 请更新客户端版本
     * data : {"is_update":true,"is_required":false,"latest_version":"1.2.1","update_note":null,"down_link":null}
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
         * is_update : true
         * is_required : false
         * latest_version : 1.2.1
         * update_note : null
         * down_link : null
         */

        private boolean is_update;
        private boolean is_required;
        private String latest_version;
        private String update_note;
        private String down_link;

        public boolean is_update() {
            return is_update;
        }

        public void setIs_update(boolean mIs_update) {
            is_update = mIs_update;
        }

        public boolean is_required() {
            return is_required;
        }

        public void setIs_required(boolean mIs_required) {
            is_required = mIs_required;
        }

        public String getLatest_version() {
            return latest_version;
        }

        public void setLatest_version(String mLatest_version) {
            latest_version = mLatest_version;
        }

        public String getUpdate_note() {
            return update_note;
        }

        public void setUpdate_note(String mUpdate_note) {
            update_note = mUpdate_note;
        }

        public String getDown_link() {
            return down_link;
        }

        public void setDown_link(String mDown_link) {
            down_link = mDown_link;
        }
    }
}
