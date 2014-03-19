package com.example.citytour;

import android.widget.Checkable;

public class Bar  implements Checkable{
	private String name, description;
	private boolean checked = false;
	
	public Bar(String name, String description){
		this.name = name;
		this.description = description;
		this.checked = false;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
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
