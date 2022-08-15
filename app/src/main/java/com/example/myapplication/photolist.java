package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class photolist extends AppCompatActivity {
    String s;
    ArrayList<String>massages=new ArrayList<>();
    ArrayAdapter arrayAdapter;

    public  void clickFn(View view){
        EditText chatEditText=(EditText) findViewById(R.id.editText);
        if(!chatEditText.getText().toString().equals("")) {
            ParseObject mass = new ParseObject("Massage");
            mass.put("sender", ParseUser.getCurrentUser().getUsername());
            mass.put("reciver", s);
            mass.put("massage", chatEditText.getText().toString());
            mass.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        massages.add("<---" + chatEditText.getText().toString());
                        chatEditText.setText("");
                        arrayAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(photolist.this, "Unable to send massage", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(photolist.this, "Message Box is empty", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photolist);
        Intent intent=getIntent();
        s=intent.getStringExtra("name");
        setTitle("Chat with"+" "+s);

        ListView listView=(ListView) findViewById(R.id.chat);
        arrayAdapter =new ArrayAdapter(photolist.this, android.R.layout.simple_list_item_1,massages);
        listView.setAdapter(arrayAdapter);
        ParseQuery query1=new ParseQuery<ParseObject>("Massage");
        query1.whereEqualTo("sender",ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("reciver",s);
        ParseQuery query2=new ParseQuery<ParseObject>("Massage");
        query2.whereEqualTo("sender",s);
        query2.whereEqualTo("reciver",ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> queries=new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);
        ParseQuery<ParseObject> query=ParseQuery.or(queries);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        massages.clear();
                        for (ParseObject massage:objects){
                            String sdk=massage.getString("massage");
                            if(!massage.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                sdk="--->"+sdk;
                            }else{
                                sdk="<---"+sdk;
                            }
                            massages.add(sdk);
                            arrayAdapter.notifyDataSetChanged();


                        }
                    }
                }
            }
        });









    }
}