package com.smartchd.smartchandigarh.data;

import java.util.ArrayList;

public class Constants {
    public static final String BASE_URL = "http://ec2-52-32-81-58.us-west-2.compute.amazonaws.com/";
    public static final String GET = "GET";
    public static final String POST = "POST";

    public static class TRAFFIC_CONSTANTS {
        public static final String START_LAT = "start_lat";
        public static final String START_LONG = "start_long";
        public static final String END_LAT = "end_lat";
        public static final String END_LONG = "end_long";
        public static final String TYPE = "type";
        public static final String LEVEL = "level";
    }

    public static ArrayList<TrafficData> trafficDatas = new ArrayList<>();
}
