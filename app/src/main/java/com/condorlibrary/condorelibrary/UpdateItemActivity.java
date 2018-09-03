package com.condorlibrary.condorelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateItemActivity extends AppCompatActivity {
EditText eTItemName;EditText eTItemCost;Button updateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        eTItemName=findViewById(R.id.editTextItemName);
        eTItemCost=findViewById(R.id.editTextCost);
        updateBtn=findViewById(R.id.buttonUpdate);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName=eTItemName.getText().toString();
                String itemCost=eTItemCost.getText().toString();
                LibraryDatabase.Library library=new LibraryDatabase.Library(getApplicationContext());
                long transactionId=library.UpdateBookItem(itemName, Integer.valueOf(itemCost));
                if(transactionId>0){
                    Toast.makeText(getApplicationContext(),"Item cost updated" ,Toast.LENGTH_LONG ).show();
                    onBackPressed();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Item not found!!!" ,Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }
}
