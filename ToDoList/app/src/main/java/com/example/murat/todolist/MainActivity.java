package com.example.murat.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayAdapter<String> adapter, adapter2;
    EditText edit1, edit2;
    Button save, show, delete;
    Spinner sp, sp2;
    private ArrayList<String> saatler;
    private ArrayList<String> sureler;
    Database myDatabase;
    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle nToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);

        save = (Button) findViewById(R.id.button1);
        show = (Button) findViewById(R.id.button2);
        delete = (Button) findViewById(R.id.button3);

        sp = (Spinner) findViewById(R.id.spinner);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp.setOnItemSelectedListener(this);
        sp2.setOnItemSelectedListener(this);

        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open, R.string.close);

        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView na_view = (NavigationView)findViewById(R.id.navmenu);
        na_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.navExit){
                    System.exit(1);
                }
                return true;
            }
        });



    saatler =new ArrayList<String>();
        saatler.add("00:00");
        saatler.add("01:00");
        saatler.add("02:00");
        saatler.add("03:00");
        saatler.add("04:00");
        saatler.add("05:00");
        saatler.add("06:00");
        saatler.add("07:00");
        saatler.add("08:00");
        saatler.add("09:00");
        saatler.add("10:00");
        saatler.add("11:00");
        saatler.add("12:00");
        saatler.add("13:00");
        saatler.add("14:00");
        saatler.add("15:00");
        saatler.add("16:00");
        saatler.add("17:00");
        saatler.add("18:00");
        saatler.add("19:00");
        saatler.add("20:00");
        saatler.add("21:00");
        saatler.add("22:00");
        saatler.add("23:00");
    sureler =new ArrayList<>();
        sureler.add("5 dakika");
        sureler.add("10 dakika");
        sureler.add("15 dakika");
        sureler.add("30 dakika");
        sureler.add("1 saat");
        sureler.add("1.5 saat");
        sureler.add("2 saat");
        sureler.add("3 saat");
        sureler.add("5 saat");

    adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,saatler);
    adapter2 =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sureler);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp2.setAdapter(adapter2);

    myDatabase =new Database(this);
    addData();
    viewAll();
    deleteData();
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(nToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void addData(){
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = sp.getSelectedItem().toString();
                String str2 = sp2.getSelectedItem().toString();
                boolean isInserted = myDatabase.insertData(edit1.getText().toString(),str1,str2);
                if(isInserted==true){
                    Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void viewAll(){
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDatabase.getAllData();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error","Nothing found");
                    return;
                }
                StringBuilder buffer = new StringBuilder();
                while(res.moveToNext()){
                    buffer.append(" ıd: "+res.getString(0)+"\n");
                    buffer.append(" aktivite: "+res.getString(1)+"\n");
                    buffer.append(" saat: "+res.getString(2)+"\n");
                    buffer.append(" sure: "+res.getString(3)+"\n\n");
                }
                // show all data
                showMessage("Data",buffer.toString());
            }
        });
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void deleteData(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDatabase.deleteData(edit2.getText().toString());
                if(deletedRows>0){
                    Toast.makeText(MainActivity.this,"Data deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data not deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}