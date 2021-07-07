package com.example.brainybrian2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.example.brainybrian2.MainActivity.chat;

public class chatbot extends Activity {
    String pname,pbg,ppg,ppgrel,paddress;
    String rname1,rrel1,rname2,rrel2,rname3,rrel3,rname4,rrel4;
    String emrel1,emrel2,emrel3;
    String em1,em2,em3;

    private ListView listView;
    private FloatingActionButton btnSend;
    private EditText editTextMsg;
    private ImageView imageView;

//
    private ChatMessageAdapter adapter;

    File file,store ;
    File gpxfile,rpxfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot);

        listView = (ListView) findViewById(R.id.conversation);
        btnSend = (FloatingActionButton) findViewById(R.id.btn_send);
        editTextMsg = (EditText) findViewById(R.id.et_message);
        imageView = (ImageView) findViewById(R.id.iv_image);

        adapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        listView.setAdapter(adapter);

        file = new File(chatbot.this.getFilesDir(), "text");
        gpxfile = new File(file, "PermanentStorageofChats.txt");
        rpxfile = new File(file, "userinfo.txt");

        readsavedfile();

//code for sending the message
        btnSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String message = editTextMsg.getText().toString();
                String response = chat.multisentenceRespond(editTextMsg.getText().toString());

                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(chatbot.this, "Please enter a query", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMessage(message);
                if(userdefinp(message)){
                    botsReply(response);
                    storage(message, response);
                }
                editTextMsg.setText("");
                listView.setSelection(adapter.getCount() - 1);
            }
        });

    }

    private void botsReply(String message){
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        adapter.add(chatMessage);
    }

    private boolean userdefinp(String message){
        String result=null;
        boolean goaiml=true;
        message=message.toUpperCase();

        if(message.contains("I LIVE")||message.contains("MY ADDRESS")||message.contains("MY HOUSE")||message.contains("MY HOME")||message.contains("I AM LOST")){
            goaiml=false;
            result="Your address is "+ paddress;
        }
        else if(message.contains("WHO AM I")||message.contains("MY NAME")){
            goaiml=false;
            result="Your name is "+pname;
        else if(message.contains("PRIMARY GUARDIAN")||message.contains("WHO TAKES CARE OF ME")){
            goaiml=false;
            result="Your "+ppgrel+", "+ppg+" takes care of you and is your primary guardian";
        }
        else if(message.contains("EMERGENCY")){
            goaiml=false;
            result="Your emergency contacts are\n"+emrel1+" "+em1+"\n"+emrel2+" "+em2+"\n"+emrel3+" "+em3+"\n";
        }
        else if(message.contains("MYSELF")){
            goaiml=false;
            result="You are "+pname+" and your blood group is "+pbg;
        }
        else if(message.contains("FAMILY")){
            goaiml=false;
            result="Apart from your primary guardian, you have your "+rrel1+", "+rname1+"; "+rrel2+", "+rname2+"; "+rrel3+", "+rname3+"; "+rrel4+", "+rname4+"; ";
        }
        else if(message.contains("BLOOD GROUP")){
            goaiml=false;
            result="Your blood group is "+pbg;
        }
        else if(message.contains("DIRECTIONS")){
            goaiml=false;
            result="Opening Maps";
            openmap();
        }
        
        if(!goaiml){
            ChatMessage chatMessage = new ChatMessage(result, false, false);
            adapter.add(chatMessage);
            storage(message.toLowerCase(),result);
        }

        return goaiml;
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        adapter.add(chatMessage);
    }

    private void storage(String userwrit, String botwrit){
        String text = "User: "+ userwrit+"\n"+"Bot: "+ botwrit +"\n";

        try {
            FileWriter writer = new FileWriter(gpxfile,true);
            writer.append(text);
            writer.flush();
            writer.close();
//          Toast.makeText(chatbot.this,"Apended in"+getFilesDir(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) { }
}

    private void readsavedfile(){
        try{
            FileReader fr=new FileReader(rpxfile);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            String line,arr[];

            while((line=br.readLine())!=null){
                arr = line.split(" ",2);
                Log.d("+++++++",arr[0]+" @ "+arr[1]);
                if(arr[0].equalsIgnoreCase("pname"))            pname=arr[1];
                else if(arr[0].equalsIgnoreCase("pbg"))         pbg=arr[1];
                else if(arr[0].equalsIgnoreCase("ppg"))         ppg=arr[1];
                else if(arr[0].equalsIgnoreCase("paddress"))    paddress=arr[1];
                else if(arr[0].equalsIgnoreCase("ppgrel"))      ppgrel=arr[1];
                else if(arr[0].equalsIgnoreCase("rname1"))      rname1=arr[1];
                else if(arr[0].equalsIgnoreCase("rrel1"))       rrel1=arr[1];
                else if(arr[0].equalsIgnoreCase("rname2"))      rname2=arr[1];
                else if(arr[0].equalsIgnoreCase("rrel2"))       rrel2=arr[1];
                else if(arr[0].equalsIgnoreCase("rname3"))      rname3=arr[1];
                else if(arr[0].equalsIgnoreCase("rrel3"))       rrel3=arr[1];
                else if(arr[0].equalsIgnoreCase("rname4"))      rname4=arr[1];
                else if(arr[0].equalsIgnoreCase("rrel4"))       rrel4=arr[1];
                else if(arr[0].equalsIgnoreCase("emrel1"))      emrel1=arr[1];
                else if(arr[0].equalsIgnoreCase("emrel2"))      emrel2=arr[1];
                else if(arr[0].equalsIgnoreCase("emrel3"))      emrel3=arr[1];
                else if(arr[0].equalsIgnoreCase("em1"))         em1=arr[1];
                else if(arr[0].equalsIgnoreCase("em2"))         em2=arr[1];
                else if(arr[0].equalsIgnoreCase("em3"))         em3=arr[1];
            }
            fr.close();    //closes the stream and release the resources
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void openmap(){
        Uri gmmIntentUri = Uri.parse("geo:18.1124,79.0193?q="+paddress);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}

