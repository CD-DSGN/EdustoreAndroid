package com.grandmagic.edustore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.external.androidquery.callback.AjaxStatus;
import com.external.imageselector.MultiImageSelector;
import com.external.imageselector.MultiImageSelectorActivity;
import com.external.imageselector.utils.ScreenUtils;
import com.grandmagic.BeeFramework.Utils.BitmapUtil;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.BeeFramework.view.ToastView;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.AddImgAdapter;
import com.grandmagic.edustore.fragment.Base64Coder;
import com.grandmagic.edustore.model.TeacherPublishModel;
import com.grandmagic.edustore.protocol.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenggaoyuan on 2016/10/25.
 */
public class Z1_TeacherPublishActivity extends BaseActivity implements OnClickListener, BusinessResponse, AddImgAdapter.ImgListener {
    public static final int IMG_SELECT = 2;
    public static final int IMG_DELETE = 1;
    private ImageView publish_back;
    private EditText publish_content;
    private Button teacher_publish;
    private TeacherPublishModel teacherPublishModel;
    private GridView gridView;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z1_teacher_publish);
        initView();
    }


    private void initView() {
        publish_back = (ImageView) findViewById(R.id.publish_back);
        publish_content = (EditText) findViewById(R.id.teacher_publish_content);
        teacher_publish = (Button) findViewById(R.id.teacher_publish_button);
        publish_back.setOnClickListener(this);
        publish_content.setOnClickListener(this);
        teacher_publish.setOnClickListener(this);
        initgridview();
    }

    List<String> gridList;
    AddImgAdapter adapter;

    private void initgridview() {
        gridView = (GridView) findViewById(R.id.grid_img);
        gridView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenSize(this).x / 3 + 50));
        gridList = new ArrayList<>();
        adapter = new AddImgAdapter(this, gridList);
        adapter.setImgListener(this);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_back:
                finish();
                closeKeyBoard();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                break;
            case R.id.teacher_publish_button:
                String content = publish_content.getText().toString();
                Resources resource = (Resources) getBaseContext().getResources();
                if ("".equals(content)) {
                    String con = resource.getString(R.string.publish_content_not_empty);
                    ToastView toast = new ToastView(this, con);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    publish_content.requestFocus();
                }
                teacherPublishModel = new TeacherPublishModel(this);
                teacherPublishModel.addResponseListener(this);
                List<String> upFilelist = new ArrayList<>();
                for (String string : gridList) {
                    Bitmap bitmap = BitmapUtil.getBitmapFromFile(string, 200, 300);
                    String s = Base64Coder.encodeLines(BitmapUtil.getBytesFromBitmap(bitmap));
                    upFilelist.add(s);
                }
                teacherPublishModel.publish_teacher_message(content, upFilelist);
        }

    }

    public void closeKeyBoard() {
        publish_content.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(publish_content.getWindowToken(), 0);
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {
        if (url.endsWith(ApiInterface.TEACHER_PUBLISH)) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    @Override
    public void addimg() {
        MultiImageSelector.create().count(3)
                .start(Z1_TeacherPublishActivity.this, IMG_SELECT);
    }

    @Override
    public void deleteimg(int position) {
        Intent intent = new Intent(Z1_TeacherPublishActivity.this, DeleteSelectedImgAct.class);
        this.position = position;
        intent.putExtra(DeleteSelectedImgAct.IMAGE_PATH, gridList.get(this.position));
        startActivityForResult(intent, IMG_DELETE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && data != null && requestCode == IMG_SELECT) {
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (gridList.size() + path.size() > 3) {
                new ToastView(this, "最多只能选择三张").show();
                return;
            }
            gridList.addAll(path);
            adapter.setGridList(gridList);

        }
        if (RESULT_OK == resultCode && requestCode == IMG_DELETE) {
            gridList.remove(position);
            adapter.setGridList(gridList);
        }
    }
}
