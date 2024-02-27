package com.project.moneymind;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class accounts_page extends AppCompatActivity {private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> accountnames;
    private static final int max_acc=4;
    private int acc_counter=0;
    int userid;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_page);
        Button createacc_btn;
        String fnameuser;

        Intent ihome = getIntent();
        fnameuser=ihome.getStringExtra("name");
        DBHelper db2=new DBHelper(this);
        userid=db2.getuserid(fnameuser);

        listView=findViewById(R.id.listview);
        accountnames=db2.fetch_accnames(userid);

        adapter=new ArrayAdapter<>(this,R.layout.list_item_acc,accountnames);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viw, int position, long id) {
                String selected_acc= adapter.getItem(position);
                int ACC_ID= db2.get_account_id(selected_acc,userid);
                Intent home=new Intent(accounts_page.this,home_page.class);

                home.putExtra("Account_id", ACC_ID);
                home.putExtra("User_id", userid);
                home.putExtra("User_name", fnameuser);
                startActivity(home);
                Toast.makeText(accounts_page.this,"Selected account: "+selected_acc,Toast.LENGTH_SHORT).show();
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
        createacc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}