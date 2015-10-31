package com.smartchd.smartchandigarh.data;

import java.util.ArrayList;

public class Constants {
    public static final String BASE_URL = "http://ec2-52-32-168-233.us-west-2.compute.amazonaws.com/";
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

    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME =
            "com.smartchd.smartchandigarh";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

}
