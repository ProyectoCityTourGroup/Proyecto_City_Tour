package com.example.citytour.models;

import android.widget.Checkable;

public class Bar  implements Checkable{
	private String name, description, avgPrice;
	private boolean checked = false;
	
	public Bar(String name, String description, String avgPrice){
		this.name = name;
		this.description = description;
		this.avgPrice = avgPrice;
		this.checked = false;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getAvgPrice(){
		return avgPrice;
	}

	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		this.checked = checked;
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return checked;
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		setChecked(!checked);
	}
}
