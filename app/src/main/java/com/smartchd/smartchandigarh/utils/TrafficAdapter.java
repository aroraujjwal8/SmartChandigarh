package com.smartchd.smartchandigarh.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.data.TrafficData;

import java.util.ArrayList;

public class TrafficAdapter extends RecyclerView.Adapter<TrafficAdapter.TrafficViewHolder> implements
        GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{

    private Context context;
    private ArrayList<TrafficData> trafficDataArrayList;

    public TrafficAdapter(Context context, ArrayList<TrafficData> trafficDataArrayList){
        this.context = context;
        this.trafficDataArrayList = trafficDataArrayList;
    }

    @Override
    public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.traffic_card,parent,false);
        return new TrafficViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrafficViewHolder holder, int position) {
        TrafficData trafficData = trafficDataArrayList.get(position);
        holder.congestionType.setText(trafficData.getCongestionType());
        holder.startTextView.setText(
                reverseGeocode(trafficData.getStart_latitude(), trafficData.getStart_longitude()));
        holder.endTextView.setText(
                reverseGeocode(trafficData.getEnd_latitude(), trafficData.getEnd_longitude()));
    }

    private String reverseGeocode(double latitude, double longitude){

        return latitude+","+longitude;
    }

    @Override
    public int getItemCount() {
        return trafficDataArrayList.size();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected class TrafficViewHolder extends RecyclerView.ViewHolder{

        private TextView congestionType;
        private TextView startTextView, endTextView;
        public TrafficViewHolder(View itemView) {
            super(itemView);
            congestionType = (TextView)itemView.findViewById(R.id.typeOfCongestion);
            startTextView = (TextView)itemView.findViewById(R.id.trafficFrom);
            endTextView = (TextView)itemView.findViewById(R.id.trafficUpto);
        }
    }


}
