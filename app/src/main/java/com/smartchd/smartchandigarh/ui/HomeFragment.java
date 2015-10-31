package com.smartchd.smartchandigarh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.smartchd.smartchandigarh.R;

/**
 * Created by raghav on 30/10/15.
 */
public class HomeFragment extends Fragment {

    private Button trafficMenuButton, healthMenuButton;

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
        return rootLayout;
    }
}
