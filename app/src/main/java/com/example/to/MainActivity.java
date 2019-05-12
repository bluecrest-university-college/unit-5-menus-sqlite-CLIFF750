package com.example.to;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity<ResultCode> extends AppCompatActivity {
    ListView ListView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Button sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sex = findViewById(R.id.FAB);
        ListView = (ListView) findViewById(R.id.ListView);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.simplelist,arrayList);
        ListView.setAdapter(arrayAdapter);
        registerForContextMenu(ListView);

        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,EditMessageClass.class);
                intent.putExtra(Intent_Constants.INTENT_MESSAGE_DATA,arrayList.get(position).toString());
                intent.putExtra(Intent_Constants.INTENT_ITEM_POSISTION, position);
                startActivityForResult();
            }
        });

        try {
            Scanner sc = new Scanner (openFileInput("Todo.txt"));
            while (sc.hasNextLine()){
                String data = sc.nextLine();
                arrayAdapter.add(data);
            }
             sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("What would you like to do");
        String options[]={"Delete","Cancel"};
        for (String option : options) {
            menu.add(option);
        }
    }

    @Override
    public void onBackPressed() {
       try {
           PrintWriter pw = null;
           try {
               pw = new PrintWriter(openFileOutput("Todo,txt", Context.MODE_PRIVATE));
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
           for (String data : arrayList) {
               pw.println(data);
           }

       }pw.close();
            } catch {FileNotFoundException e){
            e.printStackTrace();
            }
            finish();
        }

    public void onClick(View v) {
         Intent intent = new Intent ();
         intent.setClass(MainActivity.this,EditFieldClass.class);
         startActivityForResult(intent,Intent_Constants.INTENT_REQUEST_CODE);
        }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (resultCode == Intent_Constants.INTENT_REQUEST_CODE) ;
                    messageText = data.getStringExtra(Intent_Constants.INTENT_MESSAGE_FIELD);
                    arrayList.add(messageText);
                    arrayAdapter.notifyDataSetChanged();
                }
               else if(resultCode==Intent_Constants.INTENT_REQUEST_CODE_TWO) {
                    messageText = data.getStringExtraIntent_Constants.INTENT_CHANHGED_MESSAGE);
                    position = data.getIntExtra(Intent_Constants.INTENT_ITEM_POSISTION,-1);
                    arrayList.remove (position);
                    arrayList.add( messageText);
                    arrayAdapter.notifyDataSetChanged();
                }
        }