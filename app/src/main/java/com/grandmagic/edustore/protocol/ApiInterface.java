
package com.grandmagic.edustore.protocol;
public class ApiInterface 
{ 
     public static final String ORDER_AFFIRMRECEIVED  ="/order/affirmReceived";
     public static final String SHOPHELP  ="/shopHelp";
     public static final String ORDER_EXPRESS  ="/order/express";
     public static final String CART_DELETE  ="/cart/delete";
     public static final String CART_UPDATE  ="/cart/update";
     public static final String CART_CREATE  ="/cart/create";
     public static final String PRICE_RANGE  ="/price_range";
     public static final String GOODS  ="/goods";
     public static final String ARTICLE  ="/article";
     public static final String CART_LIST  ="/cart/list";
     public static final String ADDRESS_LIST  ="/address/list";
     public static final String SCHOOL_LIST  ="/school";
     public static final String GRADE_LIST  ="/grade";
     public static final String ADDRESS_SETDEFAULT  ="/address/setDefault";
     public static final String USER_COLLECT_CREATE  ="/user/collect/create";
     public static final String FLOW_DONE  ="/flow/done";
     public static final String BRAND  ="/brand";
     public static final String ORDER_PAY  ="/order/pay";
     public static final String HOME_CATEGORY  ="/home/category";
     public static final String USER_COLLECT_DELETE  ="/user/collect/delete";
     public static final String VALIDATE_INTEGRAL  ="/validate/integral";
     public static final String HOME_DATA  ="/home/data";
     public static final String ADDRESS_ADD  ="/address/add";
     public static final String USER_SIGNIN  ="/user/signin";
     public static final String USER_SIGNUP  ="/user/signup";
     /*begin added by chenggaoyuan*/
     public static final String USER_IMG_UPLOAD  ="/user/post_avatar";
     public static final String USER_SINGUP_CHECK_PHONE = "/user/signup_checkPhoneAndInviteCode";
     public static final String TEACHER_PUBLISH = "/interaction/teacherpublish";
     /*end added by chenggaoyuan*/
     public static final String GOODS_DESC  ="/goods/desc";
     public static final String USER_INFO  ="/user/info";
     public static final String SIMPLE_USER_INFO  ="/user/simple_userinfo";
     public static final String USER_SUBSCRIPTION  ="/user/subscription";
     public static final String ADDRESS_DELETE  ="/address/delete";
     public static final String USER_SIGNUPFIELDS  ="/user/signupFields";
     public static final String SEARCHKEYWORDS  ="/searchKeywords";
     public static final String ADDRESS_UPDATE  ="/address/update";
     public static final String ADDRESS_INFO  ="/address/info";
     public static final String VALIDATE_BONUS  ="/validate/bonus";
     public static final String REGION  ="/region";
     public static final String USER_COLLECT_LIST  ="/user/collect/list";
     public static final String ORDER_LIST  ="/order/list";
     public static final String CONFIG  ="/config";
     public static final String ORDER_CANCLE  ="/order/cancel";
     public static final String COMMENTS  ="/comments";
     public static final String CATEGORY  ="/category";
     public static final String SEARCH  ="/search";
     public static final String FLOW_CHECKORDER  ="/flow/checkOrder";
     public static final String CHECK_VALID_OF_USERNAME_AND_CAPTCHA  ="/user/signup_step_one";
     public static final String USER_SIGNUPCOURSES ="/user/signup_courses";
     public static final String USER_SIGNUP_TEACHER  ="/user/signup_teacher";
     public static final String SEARCH_TEACHER  ="user/search_teacher";
     public static final String ADD_TEACHER  ="/user/add_teacher";
     public static final String DELETE_TEACHER  ="/user/delete_teacher";
     public static final String TEACHER_INFO  ="/user/teacher_info";
     public static final String ALIXPAY_SIGN ="/get_sign";
     public static final String FETCH_COMMENTS ="/user/comments_list";
     public static final String PAYMENT_LIST ="/payment_list";
     public static final String UPDATE_PAYMENT_ORDER ="/flow/update_payment_of_order";
     public static final String DELETE_ONE_COMMENT ="/interaction/delete_one_comment";//删除汇师圈动态
     public static final String RETURN_REASON ="order/return_reason";//获取可选择的退款原因
     public static final String GOODS_RETURN ="order/goods_return";//提交退货申请
     public static final String COMMENT_PUBLISH ="interaction/comment_publish";//获得分成信息

     // TODO: 2017/5/11
     public static final String STUDENT_POINTS ="user/student_point_info";//获取学生积分信息接口
     public static final String TEACHER_CLASS_INFO ="user/teacher_class_info";//获取学生积分信息接口




//     public static final String GET_NEWS="http://192.168.1.115/huishiwang/api/web/getnews/detail";//资讯列表
//     public static final String CHECK_VERSION="http://192.168.1.115/huishiwang/api/web/version/updateversion";//检查版本

     public static final String GET_NEWS="http://60.205.92.85:8000/getnews/detail";//资讯列表
     public static final String CHECK_VERSION="http://60.205.92.85:8000/version/updateversion";//检查版本




}