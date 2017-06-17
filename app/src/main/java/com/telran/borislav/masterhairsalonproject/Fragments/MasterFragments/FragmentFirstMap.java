package com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.telran.borislav.masterhairsalonproject.Autocomplite.PlaceAutocompleteAdapter;
import com.telran.borislav.masterhairsalonproject.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Esca on 14.05.2017.
 */

public class FragmentFirstMap extends Fragment implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    public static final String TAG = "ONTAG";
    private static final String PATH = "/guest/list";
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    public ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        String placeId = "";

        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                places.release();
                return;
            }
            final Place place = places.get(0);
            Log.d("PlaceId:", String.valueOf(place.getPlaceTypes().get(0)));
            placeId = String.valueOf(place.getPlaceTypes().get(0));

        }
    };
    protected GoogleApiClient mGoogleApiClient;
    private GoogleMap googleMap;
    private ArrayList<String> str = new ArrayList<String>();
    private String[] strTest;
    private Handler handler;
    private Geocoder geocoder;
    private LatLng latLng;
    private Button btnSearch;
    private AutoCompleteTextView inputAddress;
    private PlaceAutocompleteAdapter mAdapter;
    private LinearLayout linear;
    private String location = "";
    private MapView mMapView;
    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see GeoDataApi#getPlaceById(GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);
            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getActivity().getApplicationContext(), "Clicked: " + primaryText, Toast.LENGTH_SHORT).show();

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_map, null);
        mMapView = (MapView) view.findViewById(R.id.map_first_fragment);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
//        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_first_fragment);
//        mapFragment.getMapAsync(this);

        handler = new Handler();

        try {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), 0 /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {

        }
        btnSearch = (Button) view.findViewById(R.id.map_first_btn_search);
        inputAddress = (AutoCompleteTextView) view.findViewById(R.id.map_first_input_search_address);
        inputAddress.setOnItemClickListener(mAutocompleteClickListener);
        inputAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                inputAddress.setSelection(0);
            }
        });
        mAdapter = new PlaceAutocompleteAdapter(getActivity(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        inputAddress.setAdapter(mAdapter);
        geocoder = new Geocoder(getActivity().getBaseContext());
        btnSearch.setOnClickListener(this);

        linear = (LinearLayout) view.findViewById(R.id.linear);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ACTION", getActivity().MODE_PRIVATE);

        mMapView.getMapAsync(this);
        return view;
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
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);
//        static final LatLng MELBOURNE = new LatLng(-37.813, 144.962);
//        googleMap.addMarker(new MarkerOptions().position(MELBOURNE).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//        googleMap.addMarker(new MarkerOptions().position(latLng).title(master.getEmail()).snippet(master.getAddresses()).icon(BitmapDescriptorFactory.fromResource(R.drawable.directory_icon)));
//        googleMap.setBuildingsEnabled(true);
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG, "onMarkerClick: " + marker.getTitle());
        return false;
    }

    //Bitmap btm = BitmapFactory.decodeResource(this.getResources(), R.drawable.directory_icon);
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_first_btn_search:
                List<Address> addressList = null;
                location = inputAddress.getText().toString();

                if (null != googleMap) {
                    if (location != null || !location.equals("")) {
                        Geocoder geocoder = new Geocoder(getActivity());
                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(location).draggable(true));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

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
