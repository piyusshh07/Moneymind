package com.project.moneymind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class home_page extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    String t1;
    TextView fname, balance;
    CardView ac_bal;
    String us ,nameuser ;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();

        if(itemId==R.id.setting){
            Intent settings=new Intent(home_page.this,settings_page.class);
            startActivity(settings);
        }
        else if (itemId==android.R.id.home){
           super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ac_bal=findViewById(R.id.ac_bal);
        fname=findViewById(R.id.hellouser);
        balance=findViewById(R.id.baltext);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationview);

        Intent oghome = getIntent();
        nameuser=oghome.getStringExtra("ftname");
        fname.setText("Hello "+nameuser);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendawer,R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itid=item.getItemId();
                return true;
            }
        });
        if(getSupportActionBar()!=null){
        }
        toolbar.setTitle("Home");


        ac_bal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    opendialog();
                }
            });

        }
        public void opendialog()
        {
            baldialog baldialog=new baldialog();
            baldialog.show(getSupportFragmentManager(),"bal dialog");
        }


    }