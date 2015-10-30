package com.smartchd.smartchandigarh.ui;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.data.Constants;
import com.smartchd.smartchandigarh.data.TrafficData;
import com.smartchd.smartchandigarh.utils.HttpManager;
import com.smartchd.smartchandigarh.utils.JsonParser;
import com.smartchd.smartchandigarh.utils.RequestPackage;
import com.smartchd.smartchandigarh.utils.TrafficAdapter;

import java.util.ArrayList;

public class TrafficActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private ProgressBar progressBar;
    private EditText start_location, end_location;
    private GoogleApiClient mGoogleApiClient;
    private TrafficAdapter trafficAdapter;
    private ArrayList<TrafficData> trafficDataArrayList;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);

        initializeViews();

        fetchData();

        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        start_location = (EditText)findViewById(R.id.startId);
        end_location = (EditText)findViewById(R.id.endId);

        trafficDataArrayList = Constants.trafficDatas;
        trafficAdapter = new TrafficAdapter(this,trafficDataArrayList);
    }




    private RequestPackage createRequestPackage(){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod(Constants.POST);
        requestPackage.setUri(Constants.BASE_URL+"traffic_data.php");
        return requestPackage;
    }

    private void fetchData(){
        FetchTrafficDataTask fetchTrafficDataTask = new FetchTrafficDataTask();
        fetchTrafficDataTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, createRequestPackage());
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private class FetchTrafficDataTask extends AsyncTask<RequestPackage, Void, ArrayList<TrafficData>>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<TrafficData> doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getData(requestPackages[0]);
            return JsonParser.getAllTrafficData(content);
        }

        @Override
        protected void onPostExecute(ArrayList<TrafficData> trafficDataList) {
            progressBar.setVisibility(View.GONE);
            trafficDataArrayList.clear();
            trafficDataArrayList.addAll(trafficDataList);
            trafficAdapter.notifyDataSetChanged();
        }
    }

}
