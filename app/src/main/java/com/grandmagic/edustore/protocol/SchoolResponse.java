package com.grandmagic.edustore.protocol;

import java.util.List;

/**
 * Created by lps on 2017/5/18.
 *
 * @version 1
 * @see
 * @since 2017/5/18 14:38
 */


public class SchoolResponse {

    /**
     * data : [{"school_name":"法库县第一初级中学","school_id":"654"},{"school_name":"法库县第二初级中学","school_id":"655"},{"school_name":"法库县第三初级中学","school_id":"656"},{"school_name":"法库县登仕堡学校","school_id":"657"},{"school_name":"法库县叶茂台镇初级中学","school_id":"658"},{"school_name":"法库县秀水河子镇初级中学","school_id":"659"},{"school_name":"法库县包家屯九年制学校","school_id":"660"},{"school_name":"法库县卧牛石学校","school_id":"661"},{"school_name":"法库县双台子学校","school_id":"662"},{"school_name":"法库县四家子蒙古族学校","school_id":"663"},{"school_name":"法库县五台子乡初级中学","school_id":"664"},{"school_name":"法库县三面船镇初级中学","school_id":"665"},{"school_name":"法库县依牛堡九年一贯制学校学校","school_id":"666"},{"school_name":"法库县大孤家子初级中学","school_id":"667"},{"school_name":"法库县冯贝堡初级中学","school_id":"668"},{"school_name":"法库县十间房初级中学","school_id":"669"},{"school_name":"法库县柏家沟学校","school_id":"670"},{"school_name":"法库县和平九年一贯制学校(和平小学)","school_id":"671"},{"school_name":"法库县孟家学校","school_id":"672"},{"school_name":"法库县慈恩寺初级中学","school_id":"673"},{"school_name":"法库县丁家房初级中学","school_id":"674"},{"school_name":"沈阳市法库县高级中学","school_id":"675"},{"school_name":"法库县第二高级中学","school_id":"676"},{"school_name":"法库县爱心学校","school_id":"677"},{"school_name":"法库县经济开发区中心小学","school_id":"678"},{"school_name":"法库县实验小学","school_id":"679"},{"school_name":"法库县太阳升小学","school_id":"680"},{"school_name":"法库镇新城堡小学","school_id":"681"},{"school_name":"法库县石桥小学","school_id":"682"},{"school_name":"法库县叶茂台镇中心小学","school_id":"683"},{"school_name":"法库县秀水河子中心小学","school_id":"684"},{"school_name":"法库县五台子乡中心小学","school_id":"685"},{"school_name":"法库县三面船镇中心小学","school_id":"686"},{"school_name":"法库县大孤家子中心小学","school_id":"687"},{"school_name":"法库县冯贝堡乡中心小学","school_id":"688"},{"school_name":"法库县十间房乡小学","school_id":"689"},{"school_name":"法库县慈恩寺中心小学","school_id":"690"},{"school_name":"法库县丁家房镇中心小学","school_id":"691"},{"school_name":"法库镇大泉眼小学","school_id":"692"},{"school_name":"法库镇红土砬子小学","school_id":"693"},{"school_name":"法库镇蛇山沟小学","school_id":"694"},{"school_name":"法库县叶茂台镇西头台子小学","school_id":"695"},{"school_name":"法库县叶茂台镇西二台子小学","school_id":"696"},{"school_name":"法库县叶茂台镇四架山小学","school_id":"697"},{"school_name":"法库县叶茂台镇当石山小学","school_id":"698"},{"school_name":"法库县叶茂台镇闫荒地小学","school_id":"699"},{"school_name":"法库县叶茂台镇榛子街小学","school_id":"700"},{"school_name":"法库县叶茂台镇庙台山小学","school_id":"701"},{"school_name":"法库县叶茂台镇石庄子小学","school_id":"702"},{"school_name":"法库县秀水河子镇吕家堡小学","school_id":"703"},{"school_name":"法库县秀水河子镇黄家堡小学","school_id":"704"},{"school_name":"法库县秀水河子陈祥堡小学","school_id":"705"},{"school_name":"法库县秀水河子镇义合屯小学","school_id":"706"},{"school_name":"法库县秀水河子镇大觉堡小学","school_id":"707"},{"school_name":"法库县秀水河子镇长岗子小学","school_id":"708"},{"school_name":"法库县秀水河子镇三家子小学","school_id":"709"},{"school_name":"秀水河子镇高三家子小学","school_id":"710"},{"school_name":"法库县秀水河子镇獾子洞小学","school_id":"711"},{"school_name":"法库县秀水河子镇彭家堡小学","school_id":"712"},{"school_name":"法库县秀水河子镇八家子小学","school_id":"713"},{"school_name":"五台子乡孤家子小学","school_id":"714"},{"school_name":"五台子乡小房申小学","school_id":"715"},{"school_name":"五台子乡古井子小学","school_id":"716"},{"school_name":"五台子乡秋皮沟小学","school_id":"717"},{"school_name":"五台子乡红花岭小学","school_id":"718"},{"school_name":"五台子乡罗泉沟小学","school_id":"719"},{"school_name":"法库县三面船镇大造屯小学","school_id":"720"},{"school_name":"法库县三面船镇二台子小学","school_id":"721"},{"school_name":"法库县三面船镇三台子小学","school_id":"722"},{"school_name":"法库县三面船镇大桑林子小学","school_id":"723"},{"school_name":"法库县三面船镇栖霞堡小学","school_id":"724"},{"school_name":"法库县三面船镇小辛屯小学","school_id":"725"},{"school_name":"法库县三面船镇南马家沟小学","school_id":"726"},{"school_name":"法库县大孤家子镇小二道房小学","school_id":"727"},{"school_name":"法库县大孤家子后孤家子小学","school_id":"728"},{"school_name":"法库县大孤家子路房申小学","school_id":"729"},{"school_name":"大孤家子镇敖牛堡小学","school_id":"730"},{"school_name":"大孤家子镇牛其卜小学","school_id":"731"},{"school_name":"大孤家子镇方石砬子小学","school_id":"732"},{"school_name":"法库县冯贝堡乡孙家窝堡小学","school_id":"733"},{"school_name":"法库县冯贝堡乡工夫屯小学","school_id":"734"},{"school_name":"法库县冯贝堡乡务名屯小学","school_id":"735"},{"school_name":"法库县冯贝堡乡黄贝堡小学","school_id":"736"},{"school_name":"法库县冯贝堡乡李家荒地小学","school_id":"737"},{"school_name":"法库县十间房乡樱桃沟小学","school_id":"738"},{"school_name":"法库县十间房乡前卫小学","school_id":"739"},{"school_name":"法库县十间房乡赵贝堡小学","school_id":"740"},{"school_name":"慈恩寺拉马荒小学","school_id":"741"},{"school_name":"慈恩寺汪家沟小学","school_id":"742"},{"school_name":"法库县丁家房镇侯三家子小学","school_id":"743"},{"school_name":"法库县丁家房镇湾柳街小学","school_id":"744"},{"school_name":"法库县丁家房镇红土墙子小学","school_id":"745"},{"school_name":"法库县丁家房乡帮牛堡小学","school_id":"746"},{"school_name":"法库县丁家房镇雷其堡小学","school_id":"747"},{"school_name":"法库县丁家房镇佘家堡小学","school_id":"748"},{"school_name":"法库县丁家房镇大泉眼小学","school_id":"749"},{"school_name":"法库县丁家房镇靠山屯小学","school_id":"750"},{"school_name":"法库县丁家房镇大蛇山子小学","school_id":"751"},{"school_name":"法库县丁家房镇兴隆峪小学","school_id":"752"}]
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
         * school_name : 法库县第一初级中学
         * school_id : 654
         */

        private String school_name;
        private String school_id;

        public String getSchool_name() {
            return school_name;
        }

        public void setSchool_name(String school_name) {
            this.school_name = school_name;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }
    }
}
