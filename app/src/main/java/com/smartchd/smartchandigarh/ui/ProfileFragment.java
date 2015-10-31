package com.smartchd.smartchandigarh.ui;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.utils.MySharedPreferences;

import java.io.File;

/**
 * Created by raghav on 30/10/15.
 */
public class ProfileFragment extends Fragment {

    private TextView name, phone, email;
    private MySharedPreferences sp;
    private ImageView profilePic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootLayout = inflater.inflate(R.layout.fragment_profile, container, false);
        sp = new MySharedPreferences(getActivity());

        name = (TextView) rootLayout.findViewById(R.id.profile_name_value);
        phone = (TextView) rootLayout.findViewById(R.id.profile_phone_value);
        email = (TextView) rootLayout.findViewById(R.id.profile_email_value);

        name.setText(sp.getFirstName() +" " +sp.getLastName() );
        phone.setText(sp.getPhone());
        email.setText(sp.getEmail());
        profilePic = (ImageView)rootLayout.findViewById(R.id.image_profile_pic);
        String path = new File(getActivity().getApplicationInfo().dataDir, "profile-pic.png").getAbsolutePath();
        profilePic.setImageBitmap(BitmapFactory.decodeFile(path));

        return rootLayout;
    }
}
