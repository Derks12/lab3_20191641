package com.example.lab3_20191641;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab3_20191641.Pomodoro;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20191641.dto.User;
import com.example.lab3_20191641.services.retrofit1;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Retrofit retrofit1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            EditText editText = findViewById(R.id.editTextText);
            EditText editPassword = findViewById(R.id.editTextTextPassword);
            HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();

            @Override
            public void onClick(View view) {

                String user = editText.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                loggin.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(loggin);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://dummyjson.com/auth/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                retrofit1 login = retrofit.create(retrofit1.class);
                Call<User> call = login.LOGIN_CALL(user, password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(response.isSuccessful() && response.body() != null){
                            editText.getText().clear();
                            editPassword.getText().clear();

                            Intent intent = new Intent(MainActivity.this,Pomodoro.class);
                            startActivity(intent);

                            Toast.makeText(MainActivity.this, "Bienvenido",Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


    }


}