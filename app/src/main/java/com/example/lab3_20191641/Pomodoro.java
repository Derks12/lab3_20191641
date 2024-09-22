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

import com.example.lab3_20191641.dto.Tareas;
import com.example.lab3_20191641.services.retrofit1;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pomodoro extends AppCompatActivity {

    private TextView nombreTextView;
    private TextView apellidoTextView;
    private TextView correoTextView;
    private TextView temporizador;
    private Button buttonPlayReplay;
    CountDownTimer timer;
    private Integer id;


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
        id = intent.getIntExtra("id",1);

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
                new MaterialAlertDialogBuilder(Pomodoro.this)
                        .setTitle("Tiempo de trabajo terminado")
                        .setMessage("Ya debes dejar de trabajar y descansar.")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            startRestTimer();
                        })
                        .show();
            }

            private void startRestTimer(){
                long restDuration = 300000;
                CountDownTimer restTimer = new CountDownTimer(restDuration , 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long minutes = (millisUntilFinished / 1000) / 60;
                        long seconds = (millisUntilFinished / 1000) % 60;
                        temporizador.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                    }

                    @Override
                    public void onFinish() {
                        temporizador.setText("05:00");
                        getTareas();
                        //acá debería estar la lógica para enviar al nuevo activity donde están las tareas asignadas, falta esa lógica
                    }
                };

            }

            private void getTareas(){
                int userId = id;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://dummyjson.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                retrofit1 tareas = retrofit.create(retrofit1.class);
                Call<List<Tareas>> call = tareas.getTareas(userId);

                call.enqueue(new Callback<List<Tareas>>() {
                    @Override
                    public void onResponse(Call<List<Tareas>> call, Response<List<Tareas>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Tareas> tareas = response.body();
                            if (tareas != null && !tareas.isEmpty()) {
                                Intent intent = new Intent(Pomodoro.this, MostrarTareas.class);
                                startActivity(intent);
                            } else {
                                new MaterialAlertDialogBuilder(Pomodoro.this)
                                        .setTitle("¡Felicidades!")
                                        .setMessage("Empezó el tiempo de descanso!")
                                        .setPositiveButton("Entendido", null)
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Tareas>> call, Throwable t) {

                    }
                });

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