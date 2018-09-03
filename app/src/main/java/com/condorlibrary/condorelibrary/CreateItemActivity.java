package com.condorlibrary.condorelibrary;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateItemActivity extends Activity implements OnItemSelectedListener {
    EditText eTItemName,eTItemCost;
    Button btnPicture,btnSubmit;
    Spinner itemsSpinner;
    Spinner itemsSpinnerSpecific;
    String[] collection=null;
    String[] softwareCollection=null;
    String[] biologyCollection=null;
    String[] politicalCollection=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        eTItemName=findViewById(R.id.eTItemName);
        eTItemCost=findViewById(R.id.eTItemCost);
        btnSubmit=findViewById(R.id.btnSubmit);
        String itemName=eTItemName.getText().toString();
        btnPicture=findViewById(R.id.btnPicture);
        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(cameraIntent);
            }
        });
        itemsSpinner=findViewById(R.id.spinnerInputType);
        itemsSpinnerSpecific=findViewById(R.id.spinnerSpecificInputType);
        collection=new String[]{"Computer Science","Biology","Political"};
        softwareCollection=new String[]{"Android","Java","PHP"};
        biologyCollection=new String[]{"Brain cells","Heart","Blood cells"};
        politicalCollection=new String[]{"Local","National","International"};
       ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,collection);
        itemsSpinner.setAdapter(adapter);
        itemsSpinner.setOnItemSelectedListener(this);
        String itemType= itemsSpinner.getSelectedItem().toString();
        String itemTypeSpecific="";
        if(itemsSpinnerSpecific.getSelectedItem()!=null) {
            itemTypeSpecific=itemsSpinnerSpecific.getSelectedItem().toString();

        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookName=eTItemName.getText().toString();
//                String itemType= itemsSpinner.getSelectedItem().toString();
//                String itemTypeSpecific=itemsSpinnerSpecific.getSelectedItem().toString();
                int itemCost=Integer.parseInt(eTItemCost.getText().toString());
                LibraryDatabase.Library student=new LibraryDatabase.Library(getApplicationContext());
                long transactionId=student.InsertItemDetails(bookName, "interesting", itemCost);
                if(transactionId>0){
                    Toast.makeText(getApplicationContext(),"Item details added to database" , Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Somethning went wrong!!!" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){

            case 0:
                ArrayAdapter<String> textAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,softwareCollection);
                itemsSpinnerSpecific.setAdapter(textAdapter);
                break;
            case 1:
                ArrayAdapter<String> audioAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,biologyCollection);
                itemsSpinnerSpecific.setAdapter(audioAdapter);
                break;
            case 2:
                ArrayAdapter<String> videoAdapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,politicalCollection);
                itemsSpinnerSpecific.setAdapter(videoAdapter);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
