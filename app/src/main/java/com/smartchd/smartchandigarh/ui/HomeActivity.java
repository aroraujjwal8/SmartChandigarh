package com.smartchd.smartchandigarh.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.smartchd.smartchandigarh.utils.HttpManager;
import com.smartchd.smartchandigarh.utils.JsonParser;
import com.smartchd.smartchandigarh.utils.MySharedPreferences;
import com.smartchd.smartchandigarh.utils.QuickstartPreferences;
import com.smartchd.smartchandigarh.utils.RegistrationIntentService;
import com.smartchd.smartchandigarh.utils.RequestPackage;

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, AlertFragment.OnFragmentInteractionListener {

    public static final int CALL_USER_SIGN_UP = 100;

    private ViewPager viewPager;
    private Fragment[] pagerFragments;
    private String[] titles;
    private MySharedPreferences sp;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUser();
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
        Fragment alertsFragment = new AlertFragment();
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

    private RequestPackage createRequestPackage(String fname, String lname, String phone, String email){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(com.smartchd.smartchandigarh.data.Constants.BASE_URL);
        requestPackage.setMethod(com.smartchd.smartchandigarh.data.Constants.POST);
        requestPackage.setParam("fname", fname);
        requestPackage.setParam("lname", lname);
        requestPackage.setParam("phone", phone);
        requestPackage.setParam("email",email);
        return requestPackage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CALL_USER_SIGN_UP:
                if (resultCode == RESULT_OK) {
                    String fname = data.getStringExtra("fname");
                    String lname = data.getStringExtra("lname");
                    String phone = data.getStringExtra("phone");
                    String email = data.getStringExtra("email");

                    sp.setFirstName(data.getStringExtra("fname"));
                    sp.setLastName(data.getStringExtra("lname"));
                    sp.setPhone(data.getStringExtra("phone"));
                    sp.setEmail(data.getStringExtra("email"));

                    AddUserTask addUserTask = new AddUserTask();
                    RequestPackage requestPackage = createRequestPackage(fname, lname, phone, email);
                    addUserTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,requestPackage);
                }
                break;
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class AddUserTask extends AsyncTask<RequestPackage,Void,Boolean>{

        @Override
        protected Boolean doInBackground(RequestPackage... requestPackages) {
            String content = HttpManager.getData(requestPackages[0]);

            if(content != null)
            Log.d("content",content);

            return JsonParser.getStatus(content);
        }

    }
}
