package com.condorlibrary.condorelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateAccountActivity extends AppCompatActivity {
EditText editForename;Button btnFname;
    EditText editSurname;Button btnSname;
    EditText editEmail;Button btnEmail;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);
        editForename=findViewById(R.id.editTextFname);
        btnFname=findViewById(R.id.btn_upd_fname);
        btnFname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEmail.setEnabled(false);
                editSurname.setEnabled(false);
                editForename.setEnabled(true);
                editForename.setFocusable(true);
                editForename.setCursorVisible(true);
            }
        });
        editSurname=findViewById(R.id.editTextSname);
        btnSname=findViewById(R.id.btn_upd_sname);
        btnSname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editEmail.setEnabled(false);
                editForename.setEnabled(false);
                editSurname.setEnabled(true);
                editSurname.setFocusable(true);
                editSurname.setCursorVisible(true);
            }
        });
        editEmail=findViewById(R.id.editTextEmail);
        btnEmail=findViewById(R.id.btn_upd_Email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editForename.setEnabled(false);
                editSurname.setEnabled(false);
                editEmail.setEnabled(true);
                editEmail.setFocusable(true);
                editEmail.setCursorVisible(true);
            }
        });
        btnUpdate=findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname= editForename.getText().toString();
                String sname= editSurname.getText().toString();
                String email= editEmail.getText().toString();
                LibraryDatabase.Library library=new LibraryDatabase.Library(getApplicationContext());
                int transactionId=library.UpdateAccount(fname,sname ,email );
                if(transactionId>0) {
                    Toast.makeText(getApplicationContext(), "Account details have been updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Incorrect email!!!" ,Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}
