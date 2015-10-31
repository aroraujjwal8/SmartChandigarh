package com.smartchd.smartchandigarh.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartchd.smartchandigarh.R;

import java.util.ArrayList;

public class AlertAdapter extends  RecyclerView.Adapter<AlertAdapter.AlertHolder>{

    private Context context;
    private ArrayList<String> alertData;

    public AlertAdapter(Context context, ArrayList<String> alertData){
        this.context = context;
        this.alertData = alertData;
    }

    @Override
    public AlertHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.alert_card,parent,false);
        return new AlertHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AlertHolder holder, int position) {
     String alert_text = alertData.get(position);
        holder.alertText.setText(alert_text);
    }

    @Override
    public int getItemCount() {
        return alertData.size();
    }

    protected class AlertHolder extends RecyclerView.ViewHolder{

        private TextView alertText;

        public AlertHolder(View itemView) {
            super(itemView);
            alertText = (TextView)itemView.findViewById(R.id.alertTextView);
        }
    }
}
