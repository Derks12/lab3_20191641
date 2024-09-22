package com.example.lab3_20191641;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pomodoro extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView correoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pomodoro);

        Button buttonPlayReplay = findViewById(R.id.buttonPlayReplay);

        nombreTextView = findViewById(R.id.textViewNombre);
        apellidoTextView = findViewById(R.id.textViewApellido);
        correoTextView = findViewById(R.id.textViewCorreo);
        ImageView imageViewGender = findViewById(R.id.imageViewGender);


        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String email = intent.getStringExtra("email");
        String gender = intent.getStringExtra("gender");

        nombreTextView.setText(firstName);
        apellidoTextView.setText(lastName);
        correoTextView.setText(email);

        if (gender.equalsIgnoreCase("female")) {
            imageViewGender.setImageResource(R.drawable.baseline_female_24);
        } else if (gender.equalsIgnoreCase("male")) {
            imageViewGender.setImageResource(R.drawable.baseline_male_24);
        }

        buttonPlayReplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_pomodoro, menu);
        return super.onCreateOptionsMenu(menu);
    }



}