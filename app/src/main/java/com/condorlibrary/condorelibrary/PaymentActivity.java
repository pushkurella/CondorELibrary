package com.condorlibrary.condorelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {
String[] paymentMethods;Spinner paymentSpinner;Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        paymentSpinner=findViewById(R.id.paymentSpinner);
        paymentMethods=new String[]{"Credit Card","Debit Card","Visa","Paypal"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,paymentMethods);
        paymentSpinner.setAdapter(adapter);
        btnPay=findViewById(R.id.tvPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Your payment is done successfully!!!",Toast.LENGTH_LONG).show();
            }
        });

    }
}
