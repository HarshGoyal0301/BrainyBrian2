package com.example.brainybrian2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class patientdetails extends Activity {
    String pname,pbg,ppg,ppgrel,paddress;
    String rname1,rrel1,rname2,rrel2,rname3,rrel3,rname4,rrel4;
    String emrel1,emrel2,emrel3;
    String em1,em2,em3;

    File file ;
    File gpxfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //This Line will hide the status bar from the screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.patientdetails);

        file = new File(patientdetails.this.getFilesDir(), "text");
        gpxfile = new File(file, "userinfo.txt");

        TextInputLayout tpname,tpbg,tppg,tppgrel,tpaddress;
        TextInputLayout trname1,trrel1,trname2,trrel2,trname3,trrel3,trname4,trrel4;
        TextInputLayout temrel1,temrel2,temrel3,tem1,tem2,tem3;
        tpname=findViewById(R.id.reg_pname);
        tpbg=findViewById(R.id.reg_pbg);
        tppg=findViewById(R.id.reg_pguardian);
        tppgrel=findViewById(R.id.reg_pguardrel);
        tpaddress=findViewById(R.id.reg_padd);
        trname1=findViewById(R.id.reg_rname1);
        trrel1=findViewById(R.id.reg_rrel1);
        trname2=findViewById(R.id.reg_rname2);
        trrel2=findViewById(R.id.reg_rrel2);
        trname3=findViewById(R.id.reg_rname3);
        trrel3=findViewById(R.id.reg_rrel3);
        trname4=findViewById(R.id.reg_rname4);
        trrel4=findViewById(R.id.reg_rrel4);
        temrel1=findViewById(R.id.reg_emrel1);
        temrel2=findViewById(R.id.reg_emrel2);
        temrel3=findViewById(R.id.reg_emrel3);
        tem1=findViewById(R.id.reg_emno1);
        tem2=findViewById(R.id.reg_emno2);
        tem3=findViewById(R.id.reg_emno3);


        findViewById(R.id.reg_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pname= tpname.getEditText().getText().toString();
                pbg=tpbg.getEditText().getText().toString();
                ppg=tppg.getEditText().getText().toString();
                ppgrel=tppgrel.getEditText().getText().toString();
                paddress=tpaddress.getEditText().getText().toString();
                rname1=trname1.getEditText().getText().toString();
                rrel1=trrel1.getEditText().getText().toString();
                rname2=trname2.getEditText().getText().toString();
                rrel2=trrel2.getEditText().getText().toString();
                rname3=trname3.getEditText().getText().toString();
                rrel3=trrel3.getEditText().getText().toString();
                rname4=trname4.getEditText().getText().toString();
                rrel4=trrel4.getEditText().getText().toString();
                emrel1=temrel1.getEditText().getText().toString();
                emrel2=temrel2.getEditText().getText().toString();
                emrel3=temrel3.getEditText().getText().toString();
                em1=tem1.getEditText().getText().toString();
                em2=tem2.getEditText().getText().toString();
                em3=tem3.getEditText().getText().toString();

                inputinfostorage();

                Intent intent = new Intent(patientdetails.this, chatbot.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    private void inputinfostorage(){
        String fpname="pname "+pname+"\n";
        String fpbg="pbg "+pbg+"\n";
        String fppg="ppg "+ppg+"\n";
        String fpaddress="paddress "+paddress+"\n";
        String fppgrel="ppgrel "+ppgrel+"\n";
        String frname1="rname1 "+rname1+"\n";
        String frrel1="rrel1 "+rrel1+"\n";
        String frname2="rname2 "+rname2+"\n";
        String frrel2="rrel2 "+rrel2+"\n";
        String frname3="rname3 "+rname3+"\n";
        String frrel3="rrel3 "+rrel3+"\n";
        String frname4="rname4 "+rname4+"\n";
        String frrel4="rrel4 "+rrel4+"\n";
        String femrel1="emrel1 "+emrel1+"\n";
        String femrel2="emrel2 "+emrel2+"\n";
        String femrel3="emrel3 "+emrel3+"\n";
        String fem1="em1 "+em1+"\n";
        String fem2="em2 "+em2+"\n";
        String fem3="em3 "+em3+"\n";

        String text = fpname+fpbg+fppg+fpaddress+fppgrel+frname1+frrel1+frname2+frrel2+frname3+frrel3+frname4+frrel4+femrel1+femrel2+femrel3+fem1+fem2+fem3;

        try {
            FileWriter writer = new FileWriter(gpxfile,false);
            writer.append(text);
            writer.flush();
            writer.close();
//            Toast.makeText(chatbot.this,"Apended in"+getFilesDir(),Toast.LENGTH_SHORT).show();
        } catch (Exception e) { }
    }

}
