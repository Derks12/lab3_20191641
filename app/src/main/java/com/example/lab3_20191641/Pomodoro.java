package com.example.lab3_20191641;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class Pomodoro extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView correoTextView;
    private TextView temporizador;
    private Button buttonPlayReplay;
    CountDownTimer timer;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pomodoro);

        buttonPlayReplay = findViewById(R.id.buttonPlayReplay);
        temporizador = findViewById(R.id.temporizador);

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
                startTime();
                //Acá le pedí ayuda a chatgpt porque no había manera de cambiar el icono usando un setIcon
                buttonPlayReplay.setBackgroundResource(R.drawable.baseline_replay_24);
            }
        });
    }

    private void startTime() {
        timer = new CountDownTimer(1500000, 1000) {
            @Override
            public void onTick(long milisUntilFinished) {
                long minutes = (milisUntilFinished / 1000) / 60;
                long seconds = (milisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                temporizador.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                temporizador.setText("25:00");
            }
        };
        timer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_pomodoro, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(Pomodoro.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}