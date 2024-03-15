package com.project.moneymind.models;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.project.moneymind.database.DBHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.project.moneymind.R;
import com.project.moneymind.database.DBHelper;

public class baldialog extends AppCompatDialogFragment {

    private EditText edtbal;

    public Dialog onCreateDialog(Bundle savedInstanceState)

    {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.ac_baledit,null);
        builder.setView(view)
                .setTitle("Edit Balance")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String bal=edtbal.getText().toString();
            double ba=Double.parseDouble(bal);

        }
    });
        edtbal=view.findViewById(R.id.balamount);
        return builder.create();
    }
    }
