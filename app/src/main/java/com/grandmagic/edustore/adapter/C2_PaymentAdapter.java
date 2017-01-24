package com.grandmagic.edustore.adapter;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.grandmagic.edustore.R;
import com.grandmagic.edustore.protocol.PAYMENT;

import java.util.List;


public class C2_PaymentAdapter extends BaseAdapter {
	
	private Context context;
	private List<PAYMENT> list;
	private LayoutInflater inflater;

	public C2_PaymentAdapter(Context context, List<PAYMENT> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	
	
	@Override
	public int getCount() {		
		return list.size();
	}

	@Override
	public Object getItem(int position) {		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}


	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("getView");
		final ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.c2_payment_cell, null);
			holder.name = (TextView) convertView.findViewById(R.id.payment_item_name);
			holder.pay_logo = (ImageView) convertView.findViewById(R.id.iv_pay_logo);
			holder.pay_spec = (TextView) convertView.findViewById(R.id.tv_pay_spec);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(list.get(position).pay_name);
		if (list.get(position).pay_name.equals("支付宝")) {
			holder.pay_logo.setImageResource(R.drawable.zhifubao);
			holder.pay_logo.setVisibility(View.VISIBLE);
			holder.pay_spec.setText("支持支付宝支付的用户使用");

		} else if (list.get(position).pay_name.equals("微信支付")) {

			holder.pay_logo.setImageResource(R.drawable.weixin);
			holder.pay_logo.setVisibility(View.VISIBLE);
			holder.pay_spec.setText("亿万用户的选择，更快更安全");
		} else {
			holder.pay_logo.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	class ViewHolder {
		private TextView name;
		private ImageView pay_logo;
		private TextView pay_spec; //支付方式的说明
	}

}
