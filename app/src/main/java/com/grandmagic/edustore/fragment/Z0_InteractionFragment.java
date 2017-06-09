package com.grandmagic.edustore.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.XListView;
import com.grandmagic.BeeFramework.Utils.BitmapUtil;
import com.grandmagic.BeeFramework.Utils.ScreenUtils;
import com.grandmagic.BeeFramework.fragment.BaseFragment;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.A0_SigninActivity;
import com.grandmagic.edustore.activity.Z1_TeacherPublishActivity;
import com.grandmagic.edustore.adapter.Z0_TeacherCommentsAdapter;
import com.grandmagic.edustore.model.TeacherCommentsModel;
import com.grandmagic.edustore.model.TeacherPublishModel;
import com.grandmagic.edustore.model.UserInfoModel;
import com.grandmagic.edustore.protocol.ApiInterface;
import com.grandmagic.edustore.protocol.PAGINATED;
import com.grandmagic.edustore.protocol.TEACHERCOMMENTS;
import com.grandmagic.edustore.protocol.USER;
import com.grandmagic.edustore.protocol.teacherpublishResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenggaoyuan on 2016/10/21. 汇师圈对应的fragment
 */
public class Z0_InteractionFragment extends BaseFragment implements View.OnClickListener, XListView.IXListViewListener, Z0_TeacherCommentsAdapter.DeleteListener {
    private static final int REQUESTCODE_PUBLISH = 1;
    private View view;

    protected Context mContext;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    TextView publish;

    private String uid;

    private View not_login;
    private View not_publish;

    private XListView commentsListView;

    private TeacherCommentsModel teacherCommentsModel;

    private Z0_TeacherCommentsAdapter teacherCommentsAdapter;

    private UserInfoModel userInfoModel;

    private USER _user;
    boolean keywordisShow = false;//键盘是否弹出


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == teacherCommentsModel) {
            teacherCommentsModel = new TeacherCommentsModel(this.getActivity());
        }
        teacherCommentsModel.addResponseListener(this);

        if (null == userInfoModel) {
            userInfoModel = new UserInfoModel(getActivity());
        }
        userInfoModel.addResponseListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.z0_interaction, null);
        Log.d("chenggaoyuan", "oncreateview");
        shared = getActivity().getSharedPreferences("userInfo", 0);
        editor = shared.edit();
        uid = shared.getString("uid", "");

        not_login = view.findViewById(R.id.notlogin);
        not_publish = view.findViewById(R.id.notpublish);
        publish = (TextView) view.findViewById(R.id.teacher_publish);
        commentsListView = (XListView) view.findViewById(R.id.interaction_list);

        if (uid.equals("")) {
            not_login.setVisibility(View.VISIBLE);
            not_publish.setVisibility(View.GONE);
            commentsListView.setVisibility(View.GONE);
            publish.setVisibility(View.GONE);
        } else {
            userInfoModel.getUserInfo();
            teacherCommentsModel.fetchComments();
        }

        publish.setOnClickListener(this);
        not_login.findViewById(R.id.login).setOnClickListener(this);

        commentsListView.setPullLoadEnable(true);
        commentsListView.setRefreshTime();
        commentsListView.setXListViewListener(this, 1);
        addGloableListener();
        return view;
    }

    private void addGloableListener() {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                view.getWindowVisibleDisplayFrame(rect);
                if (getActivity() == null)
                    return;//fragment切换的时候OnGlobalLayoutListener会被调用但此时geactivity为null
                int mScreenheight = ScreenUtils.getScreenSize(getActivity()).y;
                int keyheight = mScreenheight - (rect.bottom - rect.top);
                if (keyheight > mScreenheight * 0.3) {//检测键盘是否弹起
                    keywordisShow = true;
                } else {
                    keywordisShow = false;
                }
            }
        });
    }

    private void setContent() {

        if (teacherCommentsModel.singleTeacherCommentList.size() == 0) {
            not_publish.setVisibility(View.VISIBLE);
            not_login.setVisibility(View.GONE);
            commentsListView.setVisibility(View.GONE);
        } else {
            not_publish.setVisibility(View.GONE);
            not_login.setVisibility(View.GONE);
            commentsListView.setVisibility(View.VISIBLE);
            if (teacherCommentsAdapter == null) {
                teacherCommentsAdapter = new Z0_TeacherCommentsAdapter(this.getActivity(), teacherCommentsModel.singleTeacherCommentList);
                commentsListView.setAdapter(teacherCommentsAdapter);
                teacherCommentsAdapter.setDeleteListener(this);
            } else {
                teacherCommentsAdapter.dataList = teacherCommentsModel.singleTeacherCommentList;
                teacherCommentsAdapter.notifyDataSetChanged();
            }
        }
    }

    private TeacherPublishModel teacherPublishModel;

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        needrefresh = true;
        if (requestCode == REQUESTCODE_PUBLISH && resultCode == Activity.RESULT_OK) {
            needrefresh = false;
//            onRefresh(-1);
            content = data.getStringExtra("content");
            ArrayList<String> gridList = data.getStringArrayListExtra("image");
            addLocalItem(gridList, content);
            asyncpublish(gridList);//异步发送

        }
    }

    ArrayList<String> upFilelist = new ArrayList<>();
    String content;

    private void asyncpublish(final ArrayList<String> mGridList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String string : mGridList) {
                    byte[] tempbyte = BitmapUtil.compressImage(BitmapUtil.getBitmapFromFile(string, 720, 1280));//限制200k
                    String s = Base64Coder.encodeLines(tempbyte);
                    upFilelist.add(s);
                }
                teacherPublishModel = new TeacherPublishModel(getActivity());
                teacherPublishModel.addResponseListener(Z0_InteractionFragment.this);
                teacherPublishModel.publish_teacher_message(content, upFilelist);
            }
        }).start();
    }

    /**
     * 根据本地数据添加item
     *
     * @param mGridList
     * @param mContent
     */
    private void addLocalItem(ArrayList<String> mGridList, String mContent) {
        TEACHERCOMMENTS mTEACHERCOMMENTS = new TEACHERCOMMENTS();
        ArrayList<TEACHERCOMMENTS.Img> mImgList = new ArrayList<>();
        for (String localurl : mGridList) {
            TEACHERCOMMENTS.Img mImg = new TEACHERCOMMENTS.Img();
            mImg.img_thumb = localurl;
            mImgList.add(mImg);
        }
        mTEACHERCOMMENTS.photoArray = mImgList;
        mTEACHERCOMMENTS.publish_time = System.currentTimeMillis() + "";
        mTEACHERCOMMENTS.teacher_comments = mContent;
        mTEACHERCOMMENTS.isLocal = true;
        mTEACHERCOMMENTS.teacher_name = shared.getString("show_name", "");
        mTEACHERCOMMENTS.course_name = shared.getString("teacher_course", "");
        teacherCommentsModel.singleTeacherCommentList.add(0, mTEACHERCOMMENTS);
        if (teacherCommentsAdapter == null) {
            teacherCommentsAdapter = new Z0_TeacherCommentsAdapter(getActivity(), teacherCommentsModel.singleTeacherCommentList);
        } else {
            teacherCommentsAdapter.dataList = teacherCommentsModel.singleTeacherCommentList;
            teacherCommentsAdapter.notifyDataSetChanged();
        }
        not_publish.setVisibility(View.GONE);
        commentsListView.setVisibility(View.VISIBLE);
    }

    boolean needrefresh = true;//主要用于onresume的时候判断是否需要刷新，默认是需要的，如果从发布汇师圈回来则先不刷新

    @Override
    public void onResume() {
        super.onResume();
        uid = shared.getString("uid", "");
        if (!uid.equals("") && needrefresh) {
            userInfoModel.getUserInfo();
            not_login.setVisibility(View.GONE);
            teacherCommentsModel.fetchComments();
        } else if (TextUtils.isEmpty(uid)) {
            not_login.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        userInfoModel.removeResponseListener(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        Intent intent;
        switch (v.getId()) {
            case R.id.teacher_publish:
                intent = new Intent(getActivity(), Z1_TeacherPublishActivity.class);
                startActivityForResult(intent, REQUESTCODE_PUBLISH);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.cancle:
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                break;
            case R.id.login:
                startActivity(new Intent(getActivity(), A0_SigninActivity.class));
                getActivity().overridePendingTransition(R.anim.push_buttom_in, R.anim.push_buttom_out);
                break;
            default:
                break;
        }

    }

    @Override
    public void onRefresh(int id) {
        if (!uid.equals("")) {
            teacherCommentsModel.fetchComments();
        }
    }

    @Override
    public void onLoadMore(int id) {
        teacherCommentsModel.fetchCommentsMore();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.FETCH_COMMENTS)) {
            commentsListView.stopLoadMore();
            commentsListView.stopRefresh();
            commentsListView.setRefreshTime();

            setContent();
            PAGINATED paginated = new PAGINATED();
            paginated.fromJson(jo.optJSONObject("paginated"));
            if (0 == paginated.more) {
                commentsListView.setPullLoadEnable(false);
            } else {
                commentsListView.setPullLoadEnable(true);
            }
            Log.d("chenggaoyuan", String.valueOf(teacherCommentsModel.follow_or_not));
            if (0 == teacherCommentsModel.follow_or_not) {
                not_publish.setVisibility(View.VISIBLE);
                not_login.setVisibility(View.GONE);
                commentsListView.setVisibility(View.GONE);

            } else if (1 == teacherCommentsModel.follow_or_not) {
                not_publish.setVisibility(View.GONE);
                not_login.setVisibility(View.GONE);
                commentsListView.setVisibility(View.VISIBLE);
            }
        } else if (url.endsWith(ApiInterface.USER_INFO)) {
            _user = userInfoModel.user;
            if (_user.is_teacher.equals("0")) {
                publish.setVisibility(View.GONE);
            } else {
                publish.setVisibility(View.VISIBLE);
            }
        } else if (url.endsWith(ApiInterface.DELETE_ONE_COMMENT)) {
            commentsListView.stopLoadMore();
            commentsListView.stopRefresh();
            commentsListView.setRefreshTime();
            setContent();
        } else if (url.endsWith(ApiInterface.DELETE_ONE_COMMENT)) {
            if (mCommentPopupWindow != null && mCommentPopupWindow.isShowing()) {
                mCommentPopupWindow.dismiss();
            }
            commentsListView.stopLoadMore();
            commentsListView.stopRefresh();
            commentsListView.setRefreshTime();
            setContent();
        } else if (url.endsWith(ApiInterface.TEACHER_PUBLISH)) {
            teacherpublishResponse response = new teacherpublishResponse();
            try {
                response.fromJson(jo);
            } catch (JSONException mE) {
                mE.printStackTrace();
                showRetryDialog();
            }
            if (response!=null&&response.status!=null&&response.status.succeed == 1) {
                onRefresh(-1);//发送完成刷新
                resettemData();
                ToastView mToastView = new ToastView(getActivity(), "发送成功");
                mToastView.setGravity(17, 0, 0);
                mToastView.show();
            } else {//失败
                showRetryDialog();
            }
        }else if (url.endsWith(ApiInterface.COMMENT_PUBLISH)){
           teacherCommentsAdapter.notifyDataSetChanged();
        }
    }

    AlertDialog mRetryDialog;

    /**
     * 发送失败时候问要重试或者放弃
     */
    private void showRetryDialog() {
        if (mRetryDialog == null) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            mBuilder.setMessage("发送失败是否重试");
            mBuilder.setTitle("发送失败");
            mBuilder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    teacherPublishModel.publish_teacher_message(content, upFilelist);
                }
            });
            mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mRetryDialog.dismiss();
                    resettemData();
                    onRefresh(-1);
                }
            });
            mRetryDialog = mBuilder.create();
        }
        mRetryDialog.show();
    }
//发送成功，清理保存需要提交的变量
    private void resettemData() {
        upFilelist.clear();
        content="";
    }


    Dialog mDialog;//询问是否删除的弹窗

    /**
     * 删除动态
     *
     * @param mTeacher_uid      uid
     * @param mPublish_time_tmp time
     * @param mPosition         position
     */
    @Override
    public void delete(final String mTeacher_uid, final String mPublish_time_tmp, final int mPosition) {
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete, null);
        mView.findViewById(R.id.cancle).setOnClickListener(this);
        mView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                teacherCommentsModel.delete(mTeacher_uid, mPublish_time_tmp, mPosition);
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        mDialog = new Dialog(getActivity(), R.style.dialog);
        mDialog.setContentView(mView);
        mDialog.show();
        WindowManager.LayoutParams mAttributes = mDialog.getWindow().getAttributes();
        mAttributes.width = ScreenUtils.getScreenSize(getActivity()).x * 2 / 3;
        mDialog.getWindow().setAttributes(mAttributes);
    }

    PopupWindow mCommentPopupWindow;

    /**
     * 对动态评论
     *
     * @param newsid    主题id
     * @param mPosition
     */
    @Override
    public void commentnews(String newsid, int mPosition) {
        showCommentPop(newsid, null, null, mPosition, null);
    }

    EditText mEditText = null;

    /**
     * 弹出评论框，进行评论
     *
     * @param mNewsid
     * @param mTargetcommentid
     * @param mTarget_username 被回复人的昵称
     * @param position
     */
    TextView sendBtn = null;

    private void showCommentPop(final String mNewsid, final String mTargetcommentid,
                                final String mTarget_username, final int position, final String show_name) {
        if (mCommentPopupWindow == null) {
            final View mcomentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_comment, null);
            mEditText = (EditText) mcomentView.findViewById(R.id.et_comment);
            mEditText.setFocusable(true);
            sendBtn = (TextView) mcomentView.findViewById(R.id.send);
            mCommentPopupWindow = new PopupWindow(mcomentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
            mCommentPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mCommentPopupWindow.setOutsideTouchable(false);
            mCommentPopupWindow.setFocusable(true);
            mCommentPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mCommentPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    CloseInput();
                }
            });

        }
        mEditText.requestFocus();
        mEditText.setHint(show_name == null ? "" : "回复" + show_name);
        toogleInput();
        mEditText.setText("");
        mCommentPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                if (TextUtils.isEmpty(mEditText.getText())) {
                    Toast.makeText(getActivity(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mTargetcommentid == null) {
                    teacherCommentsModel.sendnewComment(mEditText.getText().toString(), mNewsid, position);
                } else {
                    teacherCommentsModel.replyComment(mEditText.getText().toString(), mNewsid, mTargetcommentid, position);
                }
                mEditText.setText("");
                mCommentPopupWindow.dismiss();
            }
        });

    }


    private void CloseInput() {
        if (keywordisShow) toogleInput();
    }

    /**
     * 切换输入法显示状态
     */
    private void toogleInput() {
        InputMethodManager inputMethodManager
                = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 回复动态中的评论
     *
     * @param newsid           主题id
     * @param targetcommentid  被回复的评论的id
     * @param mTarget_username
     */
    @Override
    public void replycomment(String newsid, String targetcommentid, String mTarget_username, int position, String show_name) {
        showCommentPop(newsid, targetcommentid, mTarget_username, position, show_name);
    }

}
