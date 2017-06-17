package com.telran.borislav.masterhairsalonproject.Fragments.ClientFragments;

import android.app.Fragment;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.Models.MasterArray;
import com.telran.borislav.masterhairsalonproject.Models.MasterCustom;
import com.telran.borislav.masterhairsalonproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boris on 19.04.2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    public static final String TAG = "ONTAG";
    private static final String PATH = "/guest/list";


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
        } else {
            handler.post(new FillMap());
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
        MasterCustom master = new MasterCustom();
        ArrayList<MasterCustom> masters = masterArray.getMasters();
        for (MasterCustom master1 : masters) {
            if (master1.getEmail().equals(marker.getTitle())) {
                master = master1;
                break;
            }
        }
        selectedMasterListener.showMaster(master);
    }


    public interface showSelectedMasterListener {
        void showMaster(MasterCustom master);
    }


    class GetAllMasters extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }


    class FillMap implements Runnable {


        private FillMap() {
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
        private String s;

        private ErrorRequest(String s) {
            this.s = s;
        }

        @Override
        public void run() {
            Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        }
    }
}



