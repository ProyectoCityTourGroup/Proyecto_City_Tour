package com.example.citytour;

public class Bar {
	private String name, description;
	
	public Bar(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
}
