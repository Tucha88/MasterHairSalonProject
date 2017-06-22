package com.telran.borislav.masterhairsalonproject.Fragments.ClientFragments;

import android.content.Context;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.telran.borislav.masterhairsalonproject.Models.MasterArray;
import com.telran.borislav.masterhairsalonproject.Models.MasterCustom;
import com.telran.borislav.masterhairsalonproject.R;
import com.telran.borislav.masterhairsalonproject.Tasks.GetAllMastersTask;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boris on 19.04.2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GetAllMastersTask.GetAllMastersAsyncResponse {
    public static final String TAG = "ONTAG";
    private static final String PATH = "/guest/list";


    private LatLng latLng = null;
    private MapView mMapView;
    private GoogleMap googleMap;
    private RadioGroup radioGroup;
    private EditText findByAddres;
    private Geocoder geocoder;
    private showSelectedMasterListener selectedMasterListener;
    private Map<MasterCustom, LatLng> masterLatLngMap = new HashMap<>();

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
        geocoder = new Geocoder(getActivity().getBaseContext());
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);


        if (masterLatLngMap.isEmpty()) {
            GetAllMastersTask task = new GetAllMastersTask(this, getActivity().getSharedPreferences(Utils.AUTH, Context.MODE_PRIVATE).getString(Utils.TOKEN, ""), "/guest/list", getActivity());
            task.execute();
        } else {
            fillMapFunc();
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
        for (Map.Entry<MasterCustom, LatLng> entry : masterLatLngMap.entrySet()) {
            if (entry.getKey().getEmail().equals(marker.getTitle())) {
                master = entry.getKey();
                break;
            }
        }
        selectedMasterListener.showMaster(master);
    }

    @Override
    public void onSuccess(MasterArray s) {
        for (MasterCustom master : s.getMasters()) {
            latLng = new LatLng(master.getLatitude(), master.getLongitude());
            masterLatLngMap.put(master, latLng);
        }
        fillMapFunc();

    }

    private void fillMapFunc() {
        try {
            for (Map.Entry<MasterCustom, LatLng> entry : masterLatLngMap.entrySet()) {
                googleMap.addMarker(new MarkerOptions().position(entry.getValue()).title(entry.getKey().getEmail()).snippet(entry.getKey().getAddresses()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdate() {

    }


    public interface showSelectedMasterListener {
        void showMaster(MasterCustom master);
    }


}



