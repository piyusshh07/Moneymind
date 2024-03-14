package com.project.moneymind.views.activties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.moneymind.database.DBHelper;
import com.project.moneymind.R;

public class registration_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        Button submit;
        DBHelper DB;
        EditText first_nam,last_nam,Mob_n,emai,DO,usernam,passwor;


        first_nam=findViewById(R.id.fname);
        last_nam=findViewById(R.id.sname);
        Mob_n=findViewById(R.id.mobno);
        DO=findViewById(R.id.dob);
        emai=findViewById(R.id.email);
        usernam=findViewById(R.id.username);
        passwor=findViewById(R.id.passwd);
        DB= new DBHelper(this);


        submit=findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String first_name=first_nam.getText().toString();
                String last_name=last_nam.getText().toString();
                String email=emai.getText().toString();
                String Mob_no=Mob_n.getText().toString();
                String DOB=DO.getText().toString();
                String username=usernam.getText().toString();
                String password=passwor.getText().toString();



                Boolean checkuser=DB.checkusername(username);
                if(checkuser==false)
                {
                    Boolean insert=DB.insertData(first_name,last_name,email,Mob_no,DOB,username,password);

                    if(insert==true)
                    {
                        Toast.makeText(registration_page.this,"registered successfully!!! Please login again",Toast.LENGTH_SHORT).show();
                        Intent loginpage=new Intent(registration_page.this, login.class);
                        startActivity(loginpage);
                    }
                }
                else{
                    Toast.makeText(registration_page.this,"user already exists",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}