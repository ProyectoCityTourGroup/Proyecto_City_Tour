package com.example.citytour.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.citytour.activities.DisplayOnMapActivity;
import com.example.citytour.core.CityTourApi;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alvaro on 14/09/15.
 */
public class ObtainRouteForMap extends AsyncTask<LatLng, Void, List<LatLng>> {

    private final DisplayOnMapActivity activity;
    private ProgressDialog progressDialog;

    public ObtainRouteForMap(DisplayOnMapActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(this.activity);
        progressDialog.setMessage("Calculando ruta...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected List<LatLng> doInBackground(LatLng... params) {
        LatLng origin = params[0];
        LatLng destination = params[1];

        String url = CityTourApi.googleMapsRouteURL(origin.latitude, origin.longitude, destination.latitude, destination.longitude);

        String response = CityTourApi.getResponse(url);
        final JSONObject json;
        String encodedPoints = "";
        try {
            json = new JSONObject(response);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            encodedPoints = overviewPolylines.getString("points");

            //TODO: handle errors

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return decodePoly(encodedPoints);
    }

    public static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    @Override
    protected void onPostExecute(List<LatLng> result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (result != null) {
            activity.drawPath(result);
        }
    }
}
