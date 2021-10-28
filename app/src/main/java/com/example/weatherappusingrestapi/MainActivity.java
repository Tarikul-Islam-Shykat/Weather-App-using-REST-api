package com.example.weatherappusingrestapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.weatherappusingrestapi.databinding.ActivityMainBinding;

import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding am;
    String BASE_URL= "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String api_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        am = ActivityMainBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);
        setContentView(am.getRoot());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        am.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissKeyboard(MainActivity.this);
                String city = am.edtEnterCity.getText().toString();
                //String city = "London";

                if(city.equals(""))
                {
                    Toast.makeText(getApplicationContext(), " Blank Input ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    weatherApi myApi  = retrofit.create(weatherApi.class);

                    Call<Example> example = myApi.getWeather(city,api_key );
                    example.enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {
                            /*if(!response.isSuccessful()) // it means error while resoponding
                            {
                                Toast.makeText(getApplicationContext(), "Enter a valid city", Toast.LENGTH_SHORT).show();
                            }*/

                            try {
                                Example myData  = response.body();
                                Main main = myData.getMain();
                                Coord coord = myData.getCoord();

                                // temperature
                                Double temp  = main.getTemp();
                                Integer temperature = (int) (temp - 273.15);
                                am.txtShow.setText(String.valueOf(temperature+"°"));

                                // city name
                                am.txtCityName.setText(" "+city);

                                // Feels like
                                Double feel_like = main.getFeelsLike();
                                Integer feels = (int)(feel_like-273.15);
                                am.txtFeelsLike.setText(String.valueOf(feels+"°"));

                                // max
                                Double max = main.getTempMax();
                                Integer max_temp = (int)(max-273.15);
                                am.txtMaxTemp.setText(String.valueOf(max_temp+"°"));

                                // min
                                Double min = main.getTempMin();
                                Integer min_temp = (int)(min-273.15);
                                am.txtMinTemp.setText(String.valueOf(min_temp+"°"));

                                //humidity
                                Integer humidity = main.getHumidity();
                                am.txtHumidity.setText(String.valueOf(humidity)+"%");

                                // pressure
                                Integer pressure = main.getPressure();
                                am.txtPressure.setText(pressure+"mm");

                                String  lat = coord.getLat().toString();
                                am.txtLat.setText("Lat : "+lat);

                                String  lon = coord.getLon().toString();
                                am.txtLon.setText("Lon : "+lat);




                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Please Enter a valid City", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        am.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                am.edtEnterCity.setText("");
                am.txtShow.setText("_ _");
                am.txtCityName.setText(" - - - - -");
                am.txtFeelsLike.setText("__");
                am.txtMaxTemp.setText("__");
                am.txtMinTemp.setText("__");
                am.txtHumidity.setText("__");
                am.txtPressure.setText("__");
                am.txtLat.setText("Lat : __");
                am.txtLon.setText("Lon : __");
            }
        });

    }

    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}