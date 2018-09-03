package com.condorlibrary.condorelibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
ListView lvItems;
    List<String> itemCollection=null;
    List<Integer> itemCostCollection=null;
    long val=0;ArrayAdapter<String> adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvItems=findViewById(R.id.listItems);
        itemCollection=new ArrayList<String>();
        itemCostCollection=new ArrayList<Integer>();
        LibraryDatabase.Library library=new LibraryDatabase.Library(getApplicationContext());
        Cursor cursor= library.getAllItemsList();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // The Cursor is now set to the right position
            itemCollection.add(cursor.getString(0));
            itemCostCollection.add(cursor.getInt(1));
        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,itemCollection);
        lvItems.setAdapter(adapter);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),ViewItemActivity.class);
                intent.putExtra("data", "\t\t"+itemCollection.get(i)+"\t\t\t\t\t\tCost: "+itemCostCollection.get(i)+" CAD");
                startActivity(intent);
            }
        });
        lvItems.setOnItemLongClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getApplicationContext(),ViewItemActivity.class);
        intent.putExtra("data", "\n"+itemCollection.get(i)+"\tCost: "+itemCostCollection.get(i)+" CAD");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
        val=l++;

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true)
                .setMessage("Are you sure to delete?")
                .setTitle("Delete Book")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LibraryDatabase.Library library=new LibraryDatabase.Library(getApplicationContext());
                        int transactionId=library.DeleteItem(itemCollection.get(Integer.valueOf(String.valueOf(val))));
                        String itemName="";
                        if(transactionId>0){
                            itemName=adapter.getItem((int) val);

                            adapter.remove(itemName);
                            adapter.notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(), itemName +" book was deleted successfully", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something went wrong!!!", Toast.LENGTH_LONG).show();
                        }
                    dialogInterface.cancel();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        dialogInterface.dismiss();
                    }
                });

              AlertDialog dialog=builder.create();
                dialog.show();

        return true;
    }
}
