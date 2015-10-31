package com.smartchd.smartchandigarh.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.utils.Constants;
import com.smartchd.smartchandigarh.utils.HomePagerAdapter;
import com.smartchd.smartchandigarh.utils.QuickstartPreferences;
import com.smartchd.smartchandigarh.utils.RegistrationIntentService;

/**
 * Created by raghav on 30/10/15.
 */
public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private Fragment[] pagerFragments;
    private String[] titles;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init_stringArray();
        init_fragments();
        init_instances();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d("token","sent");
                } else {
                    Log.d("token","not sent");
                }
            }
        };
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }

    private void init_stringArray(){
        titles = getResources().getStringArray(R.array.fragment_titles);
    }

    private void init_fragments(){
        HomeFragment homeFragment = new HomeFragment();
        EmergencyFragment emergencyFragment = new EmergencyFragment();
        Fragment alertsFragment = new AlertsFragment();
        ProfileFragment  profileFragment = new ProfileFragment();
        SettingsFragment settingsFragment = new SettingsFragment();

        pagerFragments = new Fragment[Constants.HOME_PAGER_FRAGMENT_COUNT];
        pagerFragments[0] = homeFragment;
        pagerFragments[1] = emergencyFragment;
        pagerFragments[2] = alertsFragment;
        pagerFragments[3] = profileFragment;
        pagerFragments[4] = settingsFragment;
    }

    private void init_instances() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.home_activity_tab_layout);

        viewPager = (ViewPager)findViewById(R.id.home_activity_view_pager);
        viewPager.setAdapter(
                new HomePagerAdapter(getSupportFragmentManager(), pagerFragments, titles));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(R.layout.custom_home_tab);
        tabLayout.getTabAt(1).setCustomView(R.layout.custom_emergency_tab);
        tabLayout.getTabAt(2).setCustomView(R.layout.custom_alerts_tab);
        tabLayout.getTabAt(3).setCustomView(R.layout.custom_profile_tab);
        tabLayout.getTabAt(4).setCustomView(R.layout.custom_settings_tab);

        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0, true);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("MainActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
