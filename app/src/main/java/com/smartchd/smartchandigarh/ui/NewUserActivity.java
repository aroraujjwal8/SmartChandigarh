package com.smartchd.smartchandigarh.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.smartchd.smartchandigarh.R;
import com.smartchd.smartchandigarh.utils.Constants;
import com.smartchd.smartchandigarh.utils.MySharedPreferences;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by raghav on 31/10/15.
 */
public class NewUserActivity extends AppCompatActivity {

    private EditText firstNameET, lastNameET, phoneET, emailET;
    private Button signUpButton;
    private Toolbar newUserToolbar;
    private ImageView profilePic;
    private MySharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_activity);

        sp = new MySharedPreferences(this);
        init_views();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(getApplicationInfo().dataDir, "profile-pic.png"));
        Crop.of(source, outputUri).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            String path = new File(getApplicationInfo().dataDir, "profile-pic.png").getAbsolutePath();
            profilePic.setImageBitmap(BitmapFactory.decodeFile(path));
            profilePic.setTag(path);
            sp.setProfilePic(Uri.fromFile(new File(getApplicationInfo().dataDir, Constants.PROFILE_PIC_PATH)));
        } else if (resultCode == Crop.RESULT_ERROR) {

        }
    }

    private void init_views(){
        firstNameET = (EditText) findViewById(R.id.new_first_name);
        lastNameET = (EditText) findViewById(R.id.new_last_name);
        phoneET = (EditText) findViewById(R.id.new_phone);
        emailET = (EditText) findViewById(R.id.new_email);
        newUserToolbar = (Toolbar) findViewById(R.id.new_user_toolbar);
        setSupportActionBar(newUserToolbar);
        signUpButton = (Button) findViewById(R.id.save_new_user_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String phone = phoneET.getText().toString();
                String email = emailET.getText().toString();
                String profileUri = sp.getProfileUri();

                Intent userDetails = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("fname", firstName);
                bundle.putString("lname", lastName);
                bundle.putString("phone", phone);
                bundle.putString("email", email);
                bundle.putString("profilePic", profileUri);
                userDetails.putExtras(bundle);
                setResult(RESULT_OK, userDetails);
                finish();
            }
        });

        profilePic = (ImageView) findViewById(R.id.new_profile_pic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Crop.pickImage(NewUserActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
