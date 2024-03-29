package com.project.moneymind.views.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.moneymind.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView appicon;
        TextView appname;

        Intent login = new Intent(MainActivity.this, com.project.moneymind.views.activties.login.class);


        appicon = findViewById(R.id.app_icon);
        appname=findViewById(R.id.app_name);
        Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        appname.setAnimation(alpha);
        appicon.setAnimation(alpha);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences prefs=getSharedPreferences("log_in",MODE_PRIVATE);
                Boolean isLogged_In=prefs.getBoolean("isLoggedIn", false);

                if(isLogged_In){
                    startActivity(new Intent(MainActivity.this, accounts_page.class));
                    finish();
                }
                else {
                startActivity(login);

                finish();}
            }
        }, 2000);
    }
}