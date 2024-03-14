package com.project.moneymind.views.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.moneymind.R;

public class settings_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        Button logout;
        logout=findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs=getSharedPreferences("log_in",MODE_PRIVATE);
                SharedPreferences.Editor editor=prefs.edit();
                editor.putBoolean("isLoggedIn",false);
                editor.apply();
                finish();

                Intent login_screen=new Intent(settings_page.this, login.class);
                startActivity(login_screen);
            }
        });
    }
}