package com.grandmagic.edustore.protocol;

import java.util.List;

/**
 * Created by lps on 2017/5/18.
 *
 * @version 1
 * @see
 * @since 2017/5/18 15:05
 */


public class GradeResponse  {

    /**
     * data : [{"grade_id":"1","grade_name":"高中三年级"},{"grade_id":"2","grade_name":"高中二年级"},{"grade_id":"3","grade_name":"高中一年级"},{"grade_id":"4","grade_name":"初中三年级"},{"grade_id":"5","grade_name":"初中二年级"},{"grade_id":"6","grade_name":"初中一年级"},{"grade_id":"7","grade_name":"小学六年级"},{"grade_id":"8","grade_name":"小学五年级"},{"grade_id":"9","grade_name":"小学四年级"},{"grade_id":"10","grade_name":"小学三年级"},{"grade_id":"11","grade_name":"小学二年级"},{"grade_id":"12","grade_name":"小学一年级"},{"grade_id":"13","grade_name":"小升初"}]
     * status : {"succeed":1}
     */

    private StatusBean status;
    private List<DataBean> data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * grade_id : 1
         * grade_name : 高中三年级
         */

        private String grade_id;
        private String grade_name;

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }
    }
}
