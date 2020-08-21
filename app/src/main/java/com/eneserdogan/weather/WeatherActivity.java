package com.eneserdogan.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherActivity extends AppCompatActivity {
    TextView txtSehir,txtAciklama,txtWeather,txtSicaklik,timeText;
    ImageView imageView;
    String sehir;
    TimerTask doAsynchronousTask;
    final Handler handler = new Handler();
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        imageView=findViewById(R.id.ımage);
        txtSehir=findViewById(R.id.sehirTv);
        txtAciklama=findViewById(R.id.acıklamaTv);
        txtSicaklik=findViewById(R.id.sicaklikTv);
        txtWeather=findViewById(R.id.weatherTv);
        timeText=findViewById(R.id.timeText);

        Intent intent=getIntent();
        sehir=intent.getStringExtra("sehir");//Gelen veriyi aldık

        doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        Date date=new Date();
                        timeText.setText(date.toString());
                        GetData getData = new GetData();
                        new GetData().execute();
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);

    }

    protected class GetData extends AsyncTask<Void,Void,Void> {
        String result_main="";//hava genel durumu
        String description="";//Açıklama
        String icon="";//ikon
        int result_temp;//Sıcaklık
        String city="";//Şehir
        Bitmap bitImage;


        @Override
        protected Void doInBackground(Void... params) {
            String result="";

            try {

                URL url=new URL("http://api.openweathermap.org/data/2.5/weather?q=+"+sehir+"&appid=apiKey");
                BufferedReader br=null;
                System.out.println("Girdi ");

                br=new BufferedReader(new InputStreamReader(url.openStream()));//url okuma
                String line=null;
                while ((line=br.readLine())!=null){
                    result += line;
                }
                br.close();

                JSONObject jsonObject=new JSONObject(result);//String ifadeyi çevirdik
                JSONArray jsonArray=jsonObject.getJSONArray("weather");//weather adlı diziyi aldık
                JSONObject jsonObject_weather=jsonArray.getJSONObject(0);//ilk indexi aldık

                result_main=jsonObject_weather.getString("main");//ilk indexin main adlı değişkenini çektik
                description=jsonObject_weather.getString("description");
                icon=jsonObject_weather.getString("icon");
                city=jsonObject.getString("name");

                JSONObject jsonObject_main=jsonObject.getJSONObject("main");//main son kısımdaki

                Double temp=jsonObject_main.getDouble("temp");//main içindeki temp
                result_temp=(int) (temp-273);

                URL icon_url=new URL("http://openweathermap.org/img/w/"+icon+".png");
                bitImage= BitmapFactory.decodeStream(icon_url.openConnection().getInputStream());//bitmap formatında çevirdik

            }catch (IOException e){
                e.printStackTrace();
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txtAciklama.setText(description.toUpperCase());
            txtSehir.setText(city.toUpperCase());
            txtSicaklik.setText(String.valueOf(result_temp+"°C"));
            txtWeather.setText(result_main.toUpperCase());
            imageView.setImageBitmap(bitImage);

            super.onPostExecute(aVoid);
        }
    }
}
