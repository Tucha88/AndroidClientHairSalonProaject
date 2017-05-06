package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.telran.borislav.hairsalonclientproject.models.Master;
import com.telran.borislav.hairsalonclientproject.models.MasterArray;
import com.telran.borislav.hairsalonclientproject.providers.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 19.04.2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private MapView mMapView;
    private GoogleMap googleMap;
    private RadioGroup radioGroup;
    private EditText findByAddres;
    private ArrayList<String> str = new ArrayList<String>();
    private String[] strTest;
    private Handler handler;
    private static final String PATH = "/master/arraylist";
    private MasterArray masterArray = new MasterArray();
    public static final String TAG = "ONTAG";
    private Geocoder geocoder;
    LatLng latLng = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        strTest = getResources().getStringArray(R.array.addresses);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.check_box);
        findByAddres = (EditText) rootView.findViewById(R.id.finder_field_edit_text);
        handler = new Handler();
        geocoder =  new Geocoder(getActivity().getBaseContext());
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getAllMasters();

        mMapView.getMapAsync(this);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        googleMap.setOnMarkerClickListener(this);
        // For showing a move to my location button
//         googleMap.setMyLocationEnabled(true);

//        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
//                View v = getActivity().getLayoutInflater().inflate(R.layout.info, null);

                return null;
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: "+marker.getTitle());
        return false;
    }

    private void getAllMasters() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AUTH", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("TOKEN", "");
        Log.d(TAG, "getAllMasters: " + token);

        MediaType type = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(type, "");
        Request request = new Request.Builder()
                .url("https://hair-salon-personal.herokuapp.com/" + PATH)
                .get()
                .addHeader("Content-Type","application/json; charset=utf-8")
                .addHeader("Authorization", token)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                handler.post(new ErrorRequest("wrong Number"));

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d(TAG, "onResponse: "+ response.code());

                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    MasterArray masterArray = gson.fromJson(response.body().string(), MasterArray.class);
                    if (masterArray != null) {
                        handler.post(new RequestOk(masterArray));

                    }


                } else if (response.code() == 401) {
                    new ErrorRequest("WTF");
                }

            }
        });

    }

    private void addMarkersToMap(){
        for (Master master : masterArray.getMasters()) {
            Log.d(TAG, "addMarkersToMap: " + master.getEmail() + master.getAddresses());
            if (master.getAddresses() !=null){
                try {
                    List<Address> address = geocoder.getFromLocationName(master.getAddresses(), 1);
                    Address location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    googleMap.addMarker(new MarkerOptions().position(latLng).title(master.getName()).snippet(master.getEmail()));
                }catch (Exception e){
                    e.printStackTrace();

                }

            }


        }
    }




    class RequestOk implements Runnable {
        private MasterArray masterOkArray;

        public RequestOk(MasterArray masterOkArray) {
            this.masterOkArray = masterOkArray;
        }

        @Override
        public void run() {
            masterArray = masterOkArray;
            Log.d(TAG, "RequestOk: " + masterArray.getMasters());
            addMarkersToMap();

        }
    }
    private class ErrorRequest implements Runnable {
        protected  String s;
        public ErrorRequest(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        }
    }

}



