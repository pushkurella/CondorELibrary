package com.condorlibrary.condorelibrary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewItemActivity extends AppCompatActivity {
TextView tvItem;Button btnShare;
String data="data";Button btnPay;Button btnDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        tvItem=findViewById(R.id.tvItem);
        final Intent intent=this.getIntent();
        if(intent!=null && intent.getExtras()!=null){
            data=intent.getExtras().getString("data");
            tvItem.setText(data);
        }
        btnShare=findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT,
                        data);
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1,"Share item"));
            }
        });
        btnPay=findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(intent1);
            }
        });
        btnDownload=findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Your item will be downloaded shortly,check the notification panel",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(getApplicationContext(),ReadBookActivity.class);
                startActivity(intent1);
            }
        });
    }
}
