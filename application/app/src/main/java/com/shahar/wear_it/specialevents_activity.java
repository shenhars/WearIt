package com.shahar.wear_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class specialevents_activity extends AppCompatActivity {
    EditText specialenevtinput;
    Button submitbutton;

    String special;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialevents);

        specialenevtinput= (EditText) findViewById(R.id.specialenevtinput);
        submitbutton=(Button) findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                special=specialenevtinput.getText().toString();
            }
        });
        submitbutton.setOnClickListener(new View.OnClickListener() {
            // Logic for the "SuitMe" button
            // This will be executed when the button is clicked
            // Add your implementation here
            @Override
            public void onClick(View v) {
                opensuitme_activity2();
            }

        });
    }
    public void opensuitme_activity2(){
        Intent intent = new Intent(this, suitme_sctivity.class);
        startActivity(intent);
    }
}