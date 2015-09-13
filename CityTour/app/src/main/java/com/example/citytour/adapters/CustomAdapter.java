package com.example.citytour.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.citytour.R;
import com.example.citytour.models.Bar;

public class CustomAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private ArrayList<Bar> bars;
	RelativeLayout[] viewArray;

	private class ViewHolder{
		TextView textView1;
		TextView textView2;
		TextView textView3;
	}
	
	public CustomAdapter(Context context, ArrayList<Bar> bars){
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewArray = new RelativeLayout[bars.size()];
		this.bars = bars;
	}
	
	public int getCount(){
		return bars.size();
	}
	
	public Bar getItem(int position){
		return bars.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.bar_layout, null);
			holder.textView1 = (TextView)convertView.findViewById(R.id.name);
			holder.textView2 = (TextView)convertView.findViewById(R.id.description);
			holder.textView3 = (TextView)convertView.findViewById(R.id.avgPrice);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.textView1.setText(bars.get(position).getName());
		holder.textView2.setText(bars.get(position).getDescription());
		holder.textView3.setText(bars.get(position).getAvgPrice());
		return convertView;
	}


}
