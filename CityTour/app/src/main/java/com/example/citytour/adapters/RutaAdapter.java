package com.example.citytour.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.citytour.R;
import com.example.citytour.models.Route;

public class RutaAdapter extends BaseAdapter{
	private final LayoutInflater inflater;
	private final ArrayList<Route> routes;

	private class ViewHolder{
		TextView textView1;
		TextView textView2;
	}
	
	public RutaAdapter(Context context, ArrayList<Route> routes){
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout[] viewArray = new RelativeLayout[routes.size()];
		this.routes = routes;
	}
	@Override
	public int getCount() {
		return routes.size();
	}

	@Override
	public Route getItem(int position) {
		return routes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
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
