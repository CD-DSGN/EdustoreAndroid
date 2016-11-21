package com.grandmagic.edustore.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.BeeFramework.fragment.BaseFragment;
import com.grandmagic.BeeFramework.model.BusinessResponse;
import com.grandmagic.edustore.R;
import com.grandmagic.edustore.activity.A0_SigninActivity;
import com.grandmagic.edustore.activity.G0_SettingActivity;
import com.grandmagic.edustore.activity.Z1_TeacherPublishActivity;

/**
 * Created by chenggaoyuan on 2016/10/21.
 */
public class Z0_InteractionFragment extends BaseFragment implements BusinessResponse,View.OnClickListener {
    private View view;

    protected Context mContext;

    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    TextView publish;

    private String uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = getActivity().getSharedPreferences("userInfo", 0);
        editor = shared.edit();
        uid = shared.getString("uid", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.z0_interaction,null);

        //headView = LayoutInflater.from(getActivity()).inflate(R.layout., null);

        if (!uid.equals("")){
            publish = (TextView) view.findViewById(R.id.teacher_publish);
            publish.setVisibility(View.VISIBLE);
            publish.setOnClickListener(this);
        }

        return view;
    }


    @Override
    public void onClick(View v) {

        Intent intent;
        switch(v.getId()) {
            case R.id.teacher_publish:
                intent = new Intent(getActivity(), Z1_TeacherPublishActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    break;
            default:
                break;
        }

    }
}
