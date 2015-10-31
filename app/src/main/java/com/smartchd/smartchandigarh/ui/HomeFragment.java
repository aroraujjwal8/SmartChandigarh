package com.smartchd.smartchandigarh.ui;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.data.Constants;

/**
 * Created by raghav on 30/10/15.
 */
public class HomeFragment extends Fragment implements LocationListener {

    private Button trafficMenuButton, healthMenuButton;
    private RelativeLayout trafficCard, healthCard;
    private MapView mapView;
    private GoogleMap googleMap;
    private TextView navigate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootLayout = inflater.inflate(R.layout.fragment_home, container, false);

        trafficMenuButton = (Button)rootLayout.findViewById(R.id.menu_button_home_traffic);
        healthMenuButton = (Button) rootLayout.findViewById(R.id.menu_button_home_health);

        trafficMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trafficIntent = new Intent(getActivity(), TrafficActivity.class);
                startActivity(trafficIntent);
            }
        });

        trafficCard = (RelativeLayout) rootLayout.findViewById(R.id.home_traffic_card);
        healthCard = (RelativeLayout) rootLayout.findViewById(R.id.home_health_card);
        navigate = (TextView) rootLayout.findViewById(R.id.navigate_tv);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) rootLayout.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
// Gets to GoogleMap from the MapView and does initialization stuff
        googleMap = mapView.getMap();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);


        CameraUpdate center;
        if(Constants.trafficDatas.size() > 0)
            center = CameraUpdateFactory.newLatLng(new LatLng(Constants.trafficDatas.get(0).getStart_latitude(), Constants.trafficDatas.get(1).getStart_longitude()));
        else
            center = CameraUpdateFactory.newLatLng(new LatLng(30.4414, 76.47));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);

        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        return rootLayout;
    }

    @Override
    public void onLocationChanged(Location location) {
        ;
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        navigate.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {

        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }
}
