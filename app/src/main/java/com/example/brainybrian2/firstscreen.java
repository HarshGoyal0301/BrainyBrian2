package com.example.brainybrian2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class firstscreen extends Activity {
    Button login,registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);

        login=findViewById(R.id.firstscreen_login);
        registration=findViewById(R.id.firstscreen_reg);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstscreen.this, chatbot.class);
                startActivity(intent);
            }
        });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstscreen.this, patientdetails.class);
                startActivity(intent);
            }
        });
    }
}
