package com.smartchd.smartchandigarh.utils;

import com.smartchd.smartchandigarh.data.Constants;
import com.smartchd.smartchandigarh.data.TrafficData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {

    public static ArrayList<TrafficData> getAllTrafficData(String content){
        ArrayList<TrafficData> trafficDataArrayList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double start_latitude = jsonObject.getDouble(Constants.TRAFFIC_CONSTANTS.START_LAT);
                double start_longitude = jsonObject.getDouble(Constants.TRAFFIC_CONSTANTS.START_LONG);
                double end_lattitude = jsonObject.getDouble(Constants.TRAFFIC_CONSTANTS.END_LAT);
                double end_longitude = jsonObject.getDouble(Constants.TRAFFIC_CONSTANTS.END_LONG);
                String type = jsonObject.getString(Constants.TRAFFIC_CONSTANTS.TYPE);
                int level = jsonObject.getInt(Constants.TRAFFIC_CONSTANTS.LEVEL);
                trafficDataArrayList.add(new TrafficData(start_latitude, start_longitude,
                                                        end_lattitude, end_longitude, type, level));
            }
        } catch (JSONException|NullPointerException e){
            e.printStackTrace();
        }
        return trafficDataArrayList;
    }

}
