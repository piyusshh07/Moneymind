package com.project.moneymind;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class login extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login;
        TextView newusr;
        EditText usname, paswrd;
        DBHelper DB;


        login = findViewById(R.id.loginbtn);
        newusr =findViewById(R.id.newuser);
        usname=findViewById(R.id.uname);
        paswrd=findViewById(R.id.paswd);
        DB= new DBHelper(this);


        Intent register= new Intent(login.this, registration_page.class);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String user = usname.getText().toString();
                    String password = paswrd.getText().toString();

                    SharedPreferences sharedPreferences = getSharedPreferences("User_Data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Username", user);
                    editor.apply();


                    if (user.equals("") || password.equals("")) {
                        Toast.makeText(login.this, "please enter all the details", Toast.LENGTH_SHORT).show();}
                    else{
                            Boolean checkuser = DB.checkusername(user);
                            if (checkuser == false) {
                                Toast.makeText(login.this, "User does not exist please register", Toast.LENGTH_SHORT).show();}

                        else{

                                    Boolean checkusernamepassword = DB.checkusernamepassword(user, password);
                                    if (checkusernamepassword == true) {
                                        Intent accountspage= new Intent(login.this, accounts_page.class);
                                        Toast.makeText( login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                        SharedPreferences prefs=getSharedPreferences("log_in",MODE_PRIVATE);
                                        SharedPreferences.Editor edit=prefs.edit();
                                        edit.putBoolean("isLoggedIn",true);
                                        edit.apply();

                                        accountspage.putExtra("name",usname.getText().toString());
                                        startActivity(accountspage);}
                                    else {
                                        Toast.makeText(login.this, "enter correct password", Toast.LENGTH_SHORT).show();
                                    }
                    }
                }}});

        newusr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startActivity(register);
            }
        });
    }

    }
