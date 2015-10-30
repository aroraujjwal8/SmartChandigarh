package com.smartchd.smartchandigarh.ui;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.data.Constants;
import com.smartchd.smartchandigarh.data.TrafficData;
import com.smartchd.smartchandigarh.utils.HttpManager;
import com.smartchd.smartchandigarh.utils.JsonParser;
import com.smartchd.smartchandigarh.utils.RequestPackage;
import com.smartchd.smartchandigarh.utils.TrafficAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrafficActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private TrafficAdapter trafficAdapter;
    private ArrayList<TrafficData> trafficDataArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);

        initializeViews();

        fetchData();
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.trafficRecyclerView);

        progressBar = (ProgressBar)findViewById(R.id.trafficProgressBar);

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

    private void reverseGeocode(ArrayList<TrafficData> trafficDatas){
        DecodeLocationTask decodeLocationTask = new DecodeLocationTask(this, trafficDatas);
        decodeLocationTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class FetchTrafficDataTask extends AsyncTask<RequestPackage, Void, ArrayList<TrafficData>>{

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<TrafficData> doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getData(requestPackages[0]);
            return JsonParser.getAllTrafficData(content);
        }

        @Override
        protected void onPostExecute(ArrayList<TrafficData> trafficDataList) {
            reverseGeocode(trafficDataList);
        }
    }

    private class DecodeLocationTask extends AsyncTask<Void,Void,ArrayList<TrafficData>>{

        private Geocoder geocoder;
        private ArrayList<TrafficData> trafficDatas;

        public DecodeLocationTask(Context context, ArrayList<TrafficData> trafficDatas){
            geocoder = new Geocoder(context,Locale.getDefault());
            this.trafficDatas = trafficDatas;
        }

        @Override
        protected ArrayList<TrafficData> doInBackground(Void... voids) {
            for(TrafficData trafficData:trafficDatas){
                List<Address> addresses = null;
                Location location = new Location("");
                location.setLatitude(trafficData.getStart_latitude());
                location.setLongitude(trafficData.getStart_longitude());
                try {
                    addresses = geocoder.getFromLocation(
                            location.getLatitude(),
                            location.getLongitude(),
                            // In this sample, get just a single address.
                            1);
                } catch (IOException | IllegalArgumentException ioException) {
                    // Catch network or other I/O problems.
                    ioException.printStackTrace();
                }

                // Handle case where no address was found.
                if (addresses == null || addresses.size()  == 0) {
                    Log.e("No Address","found ........");
                } else {
                    Address address = addresses.get(0);
                    ArrayList<String> addressFragments = new ArrayList<>();

                    for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        addressFragments.add(address.getAddressLine(i));
                    }
                     trafficData.setStartLocation(TextUtils.join(System.getProperty("line.separator"),
                             addressFragments));
                }
            }

            for(TrafficData trafficData:trafficDatas){
                List<Address> addresses = null;
                Location location = new Location("");
                location.setLatitude(trafficData.getEnd_latitude());
                location.setLongitude(trafficData.getEnd_longitude());
                try {
                    addresses = geocoder.getFromLocation(
                            location.getLatitude(),
                            location.getLongitude(),
                            // In this sample, get just a single address.
                            1);
                } catch (IOException | IllegalArgumentException ioException) {
                    // Catch network or other I/O problems.
                    ioException.printStackTrace();
                }

                // Handle case where no address was found.
                if (addresses == null || addresses.size()  == 0) {
                    Log.e("No Address","found ........");
                } else {
                    Address address = addresses.get(0);
                    ArrayList<String> addressFragments = new ArrayList<>();

                    for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        addressFragments.add(address.getAddressLine(i));
                    }
                    trafficData.setEndLocation(TextUtils.join(System.getProperty("line.separator"),
                            addressFragments));
                }
            }
            return trafficDatas;
        }

        @Override
        protected void onPostExecute(ArrayList<TrafficData> trafficDatas) {
            progressBar.setVisibility(View.GONE);
            trafficDataArrayList.clear();
            trafficDataArrayList.addAll(trafficDatas);
            trafficAdapter.notifyDataSetChanged();
        }
    }



}
