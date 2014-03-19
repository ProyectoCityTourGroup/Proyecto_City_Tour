package com.example.citytour;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private ArrayList<Bar> bars;
//	private boolean[] mChecked;
//	CheckBox[] checkBoxArray;
	RelativeLayout[] viewArray;

	private class ViewHolder{
		TextView textView1;
		TextView textView2;
//		CheckBox checkBox;
	}
	
	public CustomAdapter(Context context, ArrayList<Bar> bars){
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		mChecked = new boolean[bars.size()];
//		for(int i=0; i< mChecked.length;i++){
//			// inicializamos los checkboxes sin marcar
//			mChecked[i] = false;
//		}
//		checkBoxArray = new CheckBox[mChecked.length];
//		viewArray = new RelativeLayout[mChecked.length];
		viewArray = new RelativeLayout[bars.size()];
		this.bars = bars;
	}
	
	public int getCount(){
		return bars.size();
	}
	
//	public int getNumChecked(){
//		int count = 0;
//		for(int i=0; i<mChecked.length;i++){
//			if(mChecked[i]){
//				count++;
//			}
//		}
//		return count;
//	}
	
	public SparseBooleanArray getCheckedItemPositions(){
		SparseBooleanArray booleanArray = new SparseBooleanArray();
//		for(int i=0; i<mChecked.length;i++){
//			if(mChecked[i]==true){
//				booleanArray.append(i, true);
//			}else{
//				booleanArray.append(i, false);
//			}
//		}
		return booleanArray;
	}
	
	public Bar getItem(int position){
		return bars.get(position);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.bar_layout, null);
			holder.textView1 = (TextView)convertView.findViewById(R.id.name);
			holder.textView2 = (TextView)convertView.findViewById(R.id.description);
//			holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.textView1.setText(bars.get(position).getName());
		holder.textView2.setText(bars.get(position).getDescription());
//		holder.checkBox.setTag(Integer.valueOf(position));
//		holder.checkBox.setChecked(mChecked[position]);
//		holder.checkBox.setOnCheckedChangeListener(mListener);
		return convertView;
	}
	
//	OnCheckedChangeListener mListener = new OnCheckedChangeListener(){
//		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
//			mChecked[(Integer)buttonView.getTag()] = isChecked;
//		}
//	};

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		// TODO Auto-generated method stub
//		mChecked[(Integer)buttonView.getTag()] = isChecked;
//	}
}
