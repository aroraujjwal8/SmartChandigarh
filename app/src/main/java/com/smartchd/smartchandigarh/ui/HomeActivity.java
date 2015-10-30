package com.smartchd.smartchandigarh.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.utils.Constants;
import com.smartchd.smartchandigarh.utils.HomePagerAdapter;

/**
 * Created by raghav on 30/10/15.
 */
public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private Fragment[] pagerFragments;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init_stringArray();
        init_fragments();
        init_instances();
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
}
