package com.example.citytour;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.citytour.models.Route;

public class RutaAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	private ArrayList<Route> routes;
	RelativeLayout[] viewArray;
	
	private class ViewHolder{
		TextView textView1;
		TextView textView2;
	}
	
	public RutaAdapter(Context context, ArrayList<Route> routes){
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewArray = new RelativeLayout[routes.size()];
		this.routes = routes;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return routes.size();
	}

	@Override
	public Route getItem(int position) {
		// TODO Auto-generated method stub
		return routes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.ruta_layout, null);
			holder.textView1 = (TextView)convertView.findViewById(R.id.ruta_name);
			holder.textView2 = (TextView)convertView.findViewById(R.id.ruta_description);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.textView1.setText(routes.get(position).getName());
		holder.textView2.setText(routes.get(position).getDescription());

		return convertView;
	}

}
