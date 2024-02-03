package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Cache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    String url = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey = "2d615f3a6ed72c015f28db12e99df6ff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
    }

    public void getweather(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<Example> exampleCall = myapi.getweather(et.getText().toString().trim(), apikey);
         exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "Please Enter a valid city", Toast.LENGTH_SHORT).show();
                } else if (!(response.isSuccessful())) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();

                }
                Example mydata = response.body();
                Main main = mydata.getMain();
                Double temp = main.getTemp();
                Integer temprature = (int) (temp - 273.15);
                tv.setText(String.valueOf(temprature));


            }


            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


    }
}