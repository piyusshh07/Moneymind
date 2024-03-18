package com.project.moneymind.views.activties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.project.moneymind.database.DBHelper;
import com.project.moneymind.R;

import java.util.ArrayList;

public class accounts_page extends AppCompatActivity {private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> accountnames;
    private static final int max_acc=4;
    private int acc_counter=0;
    int userid;
    int ACC_ID;
    int accuserid;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_page);
        Button createacc_btn;
       String fnameuser ;

        SharedPreferences sharedPreferences =getSharedPreferences("User_Data",MODE_PRIVATE);
        fnameuser=sharedPreferences.getString("Username","user");
        Log.d("fname",fnameuser);

        DBHelper db2=new DBHelper(accounts_page.this);
        userid=db2.getuserid(fnameuser);
        accountnames=db2.fetch_accnames(userid);

        listView=findViewById(R.id.listview);

        adapter=new ArrayAdapter<>(this,R.layout.list_item_acc,accountnames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viw, int position, long id) {
                String selected_acc= adapter.getItem(position);
                ACC_ID = db2.getAccountId(selected_acc,userid);

                if(ACC_ID<=0){
                    Log.d("fetch error",String.valueOf(ACC_ID));
                }
                else{
                    Log.d("fetch","acc id fetched");
                }
                accuserid =db2.getaccuserid(ACC_ID);
                if(accuserid<=0){
                    Log.d("fetch error","accuser id not getting");
                }
                else{
                    Log.d("fetch","accuser id fetched");
                }

                Toast.makeText(accounts_page.this,"Selected account: "+selected_acc,Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",fnameuser);
                editor.putInt("account_id", ACC_ID);
                editor.putInt("user_id", accuserid);
                editor.apply();

                Intent home=new Intent(accounts_page.this, home_page.class);
                startActivity(home);

            }
        });


        createacc_btn=findViewById(R.id.create_btn);
        AlertDialog.Builder builder=new AlertDialog.Builder(accounts_page.this);
        View view = getLayoutInflater().inflate(R.layout.add_account,null);

        Button create, cancel;


        builder.setView(view);
        dialog = builder.create();
        create=view.findViewById(R.id.create_account);
        cancel=view.findViewById(R.id.cancel_account);
        createacc_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        create.setOnClickListener(v -> {
            EditText acname_ed= view.findViewById(R.id.account_name_edittext);
            EditText inamount=view.findViewById(R.id.initial_amount_edittext);
            String newaccname = acname_ed.getText().toString();
            String initial_acbalance=inamount.getText().toString();
            Boolean check_acc = db2.checkAccountname(newaccname, userid );
            if (check_acc == true) {
                Toast.makeText(accounts_page.this, "already exists", Toast.LENGTH_SHORT).show();
            } else {
                if (!newaccname.isEmpty()) {
                    db2.CreateAccount(userid, newaccname , Integer.valueOf(initial_acbalance));
                    accountnames.clear();
                    accountnames.addAll(db2.fetch_accnames(userid));
                    adapter.notifyDataSetChanged();
                    acname_ed.setText("");
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(view1 -> dialog.dismiss());
    }
}