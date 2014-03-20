package com.example.citytour;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;

public class GlobalVariables{
	
	private static GlobalVariables instance;

	public String[] rutaMA, ruta1, bars, coordBars, rutas;
	public ArrayList<Bar> bares;
	public ArrayList<LatLng> barCoords;
	public ArrayList<Route> routes;
	
	private GlobalVariables(){
		// Restrict the constructor from being instantiated
	}
	
	public void setRutaMA(String[] rutaMA){
		this.rutaMA = rutaMA;
	}
	
	public void setRuta1(String[] ruta1){
		this.ruta1 = ruta1;
	}
	
	public void setBares(String[] bars){
		this.bars = bars;
	}
	
	public void setRutas(String[] rutas){
		this.rutas = rutas;
	}
	
	public void setBars(ArrayList<Bar> bares){
		this.bares = bares;
	}
	
	public void setBarCoord(ArrayList<LatLng> barCoords){
		this.barCoords = barCoords;
	}
	
	public void setRoutes(ArrayList<Route> routes){
		this.routes = routes;
	}
	
	public static synchronized GlobalVariables getInstance(){
	     if(instance==null){
	       instance = new GlobalVariables();
	     }
	     return instance;
	   }

	public String[] getRutaMA(){
		return rutaMA;
	}
	
	public String[] getRuta1(){
		return ruta1;
	}
	
	public String[] getBares(){
		return bars;
	}
	
	public ArrayList<Bar> getBars(){
		return bares;
	}
	
	public ArrayList<LatLng> getBarCoord(){
		return barCoords;
	}
	
	public String[] getBCoord(){
		return coordBars;
	}
	
	public String[] getRutas(){
		return rutas;
	}
	
	public ArrayList<Route> getRoutes(){
		return routes;
	}
	
//	private ArrayList<LatLng> getCoordinates(String[] coordinates){
//		ArrayList<LatLng> coord = new ArrayList<LatLng>();
//		for(int i=0; i<coordinates.length; i++){
//			String[] aux = coordinates[i].split(",");
//			LatLng latLng = new LatLng(Double.parseDouble(aux[0]), Double.parseDouble(aux[1]));
//			coord.add(latLng);
//		}
//		return coord;
//	}
//	
//	private ArrayList<Route> makeRoutes(String[] routes){
//		ArrayList<Route> rutas = new ArrayList<Route>();
//		for(int i=0; i<routes.length-1; i+=2){
//			Route item = new Route(routes[i], routes[i+1]);
//			rutas.add(item);
//		}
//		return rutas;
//	}
	
	
}

