package com.grandmagic.edustore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.external.androidquery.util.IRequest;
import com.grandmagic.BeeFramework.activity.BaseActivity;
import com.grandmagic.edustore.ErrorCodeConst;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.adapter.SpinnerAdapter;
import com.grandmagic.edustore.model.ApplyReturnGoodsModel;
import com.grandmagic.edustore.protocol.REGIONS;
import com.grandmagic.edustore.protocol.ReasonResponse;
import com.grandmagic.edustore.protocol.addressaddResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.api.share.Base;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

//申请退货的界面
public class ApplyReturnGoodsActivity extends BaseActivity implements View.OnClickListener {
    public static final String GOODS_IMG = "goods_image";
    public static final String GOODS_NAME = "goods_name";
    public static final String GOODS_TOTAL = "goods_total";
    public static final String GOODS_NUM = "goods_number";
    public static final String GOODS_ID = "goods_id";
    private String image, name, goodsid, goodsnum, goodsprice;
    private ImageView mImage, back;
    private TextView mName, mTotal, mNum, submit, top_view_text;
    Spinner mSpinner;
    EditText mET_apply;
    private String param_reson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_return_goods);
        initdata();
        initview();
        initReason();
    }

    //        获取退款原因
    ApplyReturnGoodsModel mModel;

    private void initReason() {
        mModel = new ApplyReturnGoodsModel(this);
        mModel.getReason(new IRequest<ReasonResponse>() {
            @Override
            public void request(ReasonResponse mReasonResponse) {
                spinnerList = mReasonResponse.getData();
                mSpinner.setAdapter(new ReasonSpinnerAdapter());
            }
        });
    }

    /**
     * 获取从上个界面跳转传递的参数
     */
    private void initdata() {
        Intent mIntent = getIntent();
        image = mIntent.getStringExtra(GOODS_IMG);
        name = mIntent.getStringExtra(GOODS_NAME);
        goodsid = mIntent.getStringExtra(GOODS_ID);
        goodsprice = mIntent.getStringExtra(GOODS_TOTAL);
        goodsnum = mIntent.getStringExtra(GOODS_NUM);
    }

    List<String> spinnerList = new ArrayList<>();

    private void initview() {
        mImage = (ImageView) findViewById(R.id.trade_body_image);
        mName = (TextView) findViewById(R.id.trade_body_text);
        mTotal = (TextView) findViewById(R.id.trade_body_total);
        mNum = (TextView) findViewById(R.id.trade_body_num);
        top_view_text = (TextView) findViewById(R.id.top_view_text);
        submit = (TextView) findViewById(R.id.submit);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mET_apply = (EditText) findViewById(R.id.et_apply);
        back = (ImageView) findViewById(R.id.top_view_back);
        ImageLoader.getInstance().displayImage(image, mImage);
        mName.setText(name);
        mTotal.setText(goodsprice);
        mNum.setText(goodsnum);

        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        top_view_text.setText("申请退货");
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> mAdapterView, View mView, int position, long mL) {
                param_reson = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> mAdapterView) {

            }
        });
    }

    class ReasonSpinnerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return spinnerList.size();
        }

        @Override
        public Object getItem(int mI) {
            return null;
        }

        @Override
        public long getItemId(int mI) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sprinner, null);
            TextView mTextView = (TextView) convertView.findViewById(R.id.text1);
            mTextView.setText(spinnerList.get(position));
            return convertView;
        }
    }

    @Override
    public void onClick(View mView) {
        switch (mView.getId()) {
            case R.id.submit:
                returnGoods();
                break;
            case R.id.top_view_back:
                finish();
                break;

        }
    }

    /**
     * \退货申请
     */
    private void returnGoods() {
        mModel.Retuan_Goods(goodsid, param_reson, mET_apply.getText().toString(), new IRequest<addressaddResponse>() {
            @Override
            public void request(addressaddResponse mJSONObject) {
                if (mJSONObject.status.succeed == ErrorCodeConst.ResponseSucceed) {
                    finish();
                }
            }
        });
    }
}
