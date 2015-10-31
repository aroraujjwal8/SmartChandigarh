package com.smartchd.smartchandigarh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.utils.Constants;
import com.smartchd.smartchandigarh.utils.HomePagerAdapter;
import com.smartchd.smartchandigarh.utils.MySharedPreferences;

/**
 * Created by raghav on 30/10/15.
 */
public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static final int CALL_USER_SIGN_UP = 100;

    private ViewPager viewPager;
    private Fragment[] pagerFragments;
    private String[] titles;
    private MySharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init_stringArray();
        init_fragments();
        init_instances();
        checkUser();
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

    private void checkUser(){
        sp = new MySharedPreferences(this);
        if(sp.getFirstName() == null){
            Intent newUserIntent = new Intent(this, NewUserActivity.class);
            startActivityForResult(newUserIntent, CALL_USER_SIGN_UP);
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CALL_USER_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    sp.setFirstName(data.getStringExtra("fname"));
                    sp.setLastName(data.getStringExtra("lname"));
                    sp.setPhone(data.getStringExtra("phone"));
                    sp.setEmail(data.getStringExtra("email"));
                }
                break;
        }
    }
}
