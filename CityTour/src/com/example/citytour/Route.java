package com.example.citytour;

import android.widget.Checkable;

public class Route implements Checkable{
	private String name, description;
	
	public Route(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	private boolean checked = false;
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
