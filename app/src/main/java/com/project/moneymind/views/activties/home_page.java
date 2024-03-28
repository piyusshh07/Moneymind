package com.project.moneymind.views.activties;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.google.android.material.textfield.TextInputEditText;
import com.project.moneymind.ViewModel.MainViewModel;
import com.project.moneymind.models.transaction;
import com.project.moneymind.views.fragements.AddTransactionFragement;
import com.project.moneymind.database.DBHelper;
import com.project.moneymind.R;

public class home_page extends AppCompatActivity {

    public MainViewModel viewModel;
    @SuppressLint("MissingInflatedId")
    String t1;
    TextView fname, balance ,acc_Name;
    CardView ac_bal , Expensecard , Budget_GoalsCard;
    String us ,nameuser ;

    Integer acc_id,user_id ,acc_balance;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    DBHelper db;
    FloatingActionButton fabtn;
     AlertDialog baldialog;

    double ba;




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

    @SuppressLint("MissingInflatedId")
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
        Budget_GoalsCard=findViewById(R.id.bud_goal);

        transaction transactions=new transaction();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //sharedpreference code
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        acc_id = sharedPreferences.getInt("account_id", -1);
        user_id = sharedPreferences.getInt("user_id", -1);
        nameuser = sharedPreferences.getString("username","fnameuser");

        transactions.setAcid(acc_id);
        transactions.setUser_id(user_id);
        Log.d("ids",String.valueOf(transactions.getAcid()));
        Log.d("ids",String.valueOf(transactions.getUser_id()));

        Intent his_page=new Intent(home_page.this, history_page.class);
        db=new DBHelper(home_page.this);

// Check if acc_id and user_id are correctly retrieved
        Log.d("Debug", "acc_id: " + acc_id + ", user_id: " + user_id);

// If acc_id and user_id are correct, use them to get account balance
        if (acc_id != -1 && user_id != -1) {
            acc_balance = (int) db.getAccountBalanceById(acc_id, user_id);

        } else {
            // Handle the case where acc_id or user_id is -1
            Log.e("Error", "Invalid acc_id or user_id");
        }



        viewModel.totalAccountBalance.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                balance.setText(String.valueOf(aDouble));
                Log.d("income", String.valueOf(viewModel.totalIncome));
            }
        });
        String name=db.getfname(user_id);
    fname.setText("Hello "+ name);

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
                   baldialog.show();
                } else if (itid==R.id.ex_history_nav) {
                    Intent his=new Intent(home_page.this,history_page.class);
                    startActivity(his);

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
        Budget_GoalsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent budgoals=new Intent(home_page.this, budget_goals.class);
                startActivity(budgoals);

            }
        });

        if(getSupportActionBar()!=null){
        }
        toolbar.setTitle("Home");



    }

    public void getTransaction() {
    }
}