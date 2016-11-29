package geethika608.nearbyplace;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Places extends SupportMapFragment {


    GoogleMap mapView;
    CameraPosition cameraPosition;

    JSONArray array = new JSONArray();
    JSONArray locationarray = new JSONArray();
    View view;
    double lat, lon;

    public String TAG = "EventsandLocationsSM";


    @Override
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup arg1,
                             Bundle arg2) {

        return super.onCreateView(mInflater, arg1, arg2);

    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onInflate(Activity arg0, AttributeSet arg1, Bundle arg2) {
        super.onInflate(arg0, arg1, arg2);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle mArgs = getArguments();
        lat = Double.parseDouble(mArgs.getString("lat").toString());
        lon = Double.parseDouble(mArgs.getString("lang").toString());
        try {
            array = new JSONArray(mArgs.get("location").toString());
            int l = array.length();
            for (int i = 0; i < l; i++) {
                locationarray.put(array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapView = googleMap;
                if (locationarray.length() != 0) {
                    removemarker(locationarray, false);
                } else {
                    mapView.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("This is your location").
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(15).build();
                    mapView.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

            }
        });


    }


    public void removemarker(final JSONArray jsonArray, boolean clearflag) {
        if (clearflag) {
            mapView.clear();
        }
        View v = getLayoutInflater(null).inflate(R.layout.stadium_events_layout, null);
       /* TextView tt = (TextView) v.findViewById(R.id.title);*/
        Bitmap bitmap = ViewToImageConverter.Convert(v);
        final ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                // tt.setText(array.getJSONObject(i).getString("name"));

                Marker marker = mapView.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(jsonArray.getJSONObject(i).get("lat").toString()),
                                Double.parseDouble(jsonArray.getJSONObject(i).get("lng").toString()))).snippet("hi").
                                icon(BitmapDescriptorFactory.fromBitmap(bitmap)).anchor(0.5f, 1));
                temp.add(marker.getId());
                if (i == (jsonArray.length() - 1)) {
                   /* cameraPosition = new CameraPosition.Builder().target(new LatLng(Double.parseDouble(jsonArray.getJSONObject(i).get("lat").toString()), Double.parseDouble(jsonArray.getJSONObject(i).get("lng").toString()))).zoom(10).build();
                    mapView.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                    mapView.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title("This is your location").
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(13).build();
                    mapView.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
               /* mapView.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        final int pos = temp.indexOf(marker.getId());
                        if (pos != -1) {
                            // ShowAddedLocations(pos);
                            ShowLocationList();
                        }
                        return true;
                    }
                });*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}

