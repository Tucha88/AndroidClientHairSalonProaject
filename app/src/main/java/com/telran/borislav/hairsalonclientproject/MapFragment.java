package com.telran.borislav.hairsalonclientproject;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.telran.borislav.hairsalonclientproject.models.Master;
import com.telran.borislav.hairsalonclientproject.models.MasterArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Boris on 19.04.2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    public static final String TAG = "ONTAG";
    private static final String PATH = "/guest/list";

    String result = "OK";

    LatLng latLng = null;
    private MapView mMapView;
    private GoogleMap googleMap;
    private RadioGroup radioGroup;
    private EditText findByAddres;
    private Handler handler;
    private MasterArray masterArray;
    private Geocoder geocoder;
    private showSelectedMasterListener selectedMasterListener;
    private Map<Master, LatLng> masterLatLngMap = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        radioGroup = (RadioGroup) rootView.findViewById(R.id.check_box);
        findByAddres = (EditText) rootView.findViewById(R.id.finder_field_edit_text);
        handler = new Handler();
        geocoder = new Geocoder(getActivity().getBaseContext());
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);


        if (masterLatLngMap.isEmpty()) {
            new GetAllMasters().execute();
            Log.d(TAG, "onCreateView: 1");
        } else {
            handler.post(new FillMap());
            Log.d(TAG, "onCreateView: 2");
        }

        return rootView;
    }

    public void setSelectedMasterListener(showSelectedMasterListener selectedMasterListener) {
        this.selectedMasterListener = selectedMasterListener;
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

        googleMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Master master = new Master();
        ArrayList<Master> masters = masterArray.getMasters();
        for (Master master1 : masters) {
            if (master1.getEmail().equals(marker.getTitle())) {
                master = master1;
                break;
            }
        }
        selectedMasterListener.showMaster(master);
    }


    interface showSelectedMasterListener {
        void showMaster(Master master);
    }


    class GetAllMasters extends AsyncTask<Void, Void, Void> {
        public GetAllMasters() {
        }


        @Override
        protected Void doInBackground(Void... voids) {

            Request request = new Request.Builder()
                    .url("https://hair-salon-personal.herokuapp.com/" + PATH)
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build();
            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    handler.post(new ErrorRequest("wrong Number"));
                }


                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse: " + response.code());

                    if (response.isSuccessful()) {
                        Gson gson = new Gson();
                        masterArray = gson.fromJson(response.body().string(), MasterArray.class);
                        if (masterArray != null) {
                            for (Master master : masterArray.getMasters()) {
                                if (master.getAddresses() != null) {
                                    try {
                                        List<Address> address = geocoder.getFromLocationName(master.getAddresses(), 1);
                                        Address location = address.get(0);
                                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        masterLatLngMap.put(master, latLng);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }

                        }

                    } else if (response.code() == 401) {
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    handler.post(new FillMap());
                }
            });
            return null;
        }
    }


    class FillMap implements Runnable {


        public FillMap() {
        }

        @Override
        public void run() {
            try {
                for (Map.Entry<Master, LatLng> entry : masterLatLngMap.entrySet()) {
                    googleMap.addMarker(new MarkerOptions().position(entry.getValue()).title(entry.getKey().getEmail()).snippet(entry.getKey().getAddresses()));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class ErrorRequest implements Runnable {
        protected String s;

        public ErrorRequest(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }
}



