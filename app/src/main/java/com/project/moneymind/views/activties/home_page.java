package com.project.moneymind.views.activties;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.project.moneymind.views.fragements.AddTransactionFragement;
import com.project.moneymind.database.DBHelper;
import com.project.moneymind.R;
import com.project.moneymind.models.baldialog;

public class home_page extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    String t1;
    TextView fname, balance ,acc_Name;
    CardView ac_bal , Expensecard;
    String us ,nameuser ;

    Integer acc_id,user_id ,acc_balance;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DBHelper db;
    FloatingActionButton fabtn;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if(itemId==R.id.setting){
            Intent settings=new Intent(home_page.this, settings_page.class);
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
        ac_bal=findViewById(R.id.acbal_card);
        fname=findViewById(R.id.hellouser);
        balance=findViewById(R.id.baltext);
        toolbar=findViewById(R.id.toolBar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigationview);
        fabtn=findViewById(R.id.fab);
        Expensecard=findViewById(R.id.history);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        acc_id = sharedPreferences.getInt("account_id", -1);
        user_id = sharedPreferences.getInt("user_id", -1);
        nameuser =sharedPreferences.getString("username","fnameuser");

        Intent his_page=new Intent(home_page.this, history_page.class);

// Check if acc_id and user_id are correctly retrieved
        Log.d("Debug", "acc_id: " + acc_id + ", user_id: " + user_id);

// If acc_id and user_id are correct, use them to get account balance
        if (acc_id != -1 && user_id != -1) {
            acc_balance = db.getAccountBalance(acc_id, user_id);
            balance.setText(String.valueOf(acc_balance));
        } else {
            // Handle the case where acc_id or user_id is -1
            Log.e("Error", "Invalid acc_id or user_id");
        }


        db=new DBHelper(this);
        acc_balance= db.getAccountBalance(acc_id,user_id);

        nameuser=sharedPreferences.getString("Username","fnameuser");
      balance.setText(String.valueOf(acc_balance));
    fname.setText("Hello "+nameuser);

        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddTransactionFragement().show(getSupportFragmentManager(),null);

            }
        });
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendawer,R.string.closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itid=item.getItemId();

                if (itid==R.id.abal_nav){
                    opendialog();
                } else if (itid==R.id.ex_history_nav) {
                    startActivity(his_page);

                }
                return true;
            }
        });

        Expensecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(his_page);
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