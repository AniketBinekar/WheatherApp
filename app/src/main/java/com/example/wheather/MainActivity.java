package com.example.wheather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    ImageView imageView;
    TextView country,city,temp,time;
    TextView lat,longi,huma,sunrise,sunset,pree,wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editTextTextPersonName);
        button=(Button)findViewById(R.id.button);
        imageView=(ImageView)findViewById(R.id.imageButton);
        country=(TextView)findViewById(R.id.count);
        city=(TextView)findViewById(R.id.city);
        time=(TextView)findViewById(R.id.textView2);
        temp=(TextView)findViewById(R.id.textView4);

        lat=(TextView)findViewById(R.id.Latitude);
        longi=(TextView)findViewById(R.id.Longitude);
        huma=(TextView)findViewById(R.id.Humidity);
        sunrise=(TextView)findViewById(R.id.Sunrise);
        sunset=(TextView)findViewById(R.id.Sunset);
        pree=(TextView)findViewById(R.id.Pressure);
        wind=(TextView)findViewById(R.id.WindSpeed);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weather();
            }
        });
    }
    public void weather()
    {
        String city1=editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+city1+"&appid=462f445106adc1d21494341838c10019";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
//                    find country
                    JSONObject object1=jsonObject.getJSONObject("sys");
                    String  country_find=object1.getString("country");
                    country.setText(country_find+" :");
//                    now we have find city
                    String city_find=jsonObject.getString("name");
                    city.setText(city_find);

//                    find tempreature

                    JSONObject object2=jsonObject.getJSONObject("main");
                    double  temp_find=object2.getDouble("temp");
                    temp.setText(temp_find+"°C");

//                    gind image icon
                    JSONArray jsonArray=jsonObject.getJSONArray("weather");
                    JSONObject object4=jsonArray.getJSONObject(0);
                    String icon =object4.getString("icon");
                    Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

//                    find date and time
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat std=new SimpleDateFormat("dd/MM/yyyy\nHH:mm:ss");
                    String date=std.format(calendar.getTime());
                    time.setText(date);

//                    latitude

                    JSONObject object5=jsonObject.getJSONObject("coord");
                    double lati_tude=object5.getDouble("lat");
//                    lat.setText((int) latitude);
                        lat.setText(lati_tude+"° N");

//                        find longitude
                    JSONObject object6=jsonObject.getJSONObject("coord");
                    double long_find=object6.getDouble("lon");
                    longi.setText(long_find+"° E");

//                    find humadity

                    JSONObject object7=jsonObject.getJSONObject("main");
                    int huma_find=object7.getInt("humidity");
                    huma.setText(huma_find+" %");

//                    sunrise
                    JSONObject object8=jsonObject.getJSONObject("sys");
                    String sunrr=object8.getString("sunrise");
                    sunrise.setText(sunrr);

//                    sunset
                    JSONObject object9=jsonObject.getJSONObject("sys");
                    String sunss=object9.getString("sunset");
                    sunset.setText(sunss);

//                    pressure
                    JSONObject object10=jsonObject.getJSONObject("main");
                    String preei=object10.getString("pressure");
                    pree.setText(preei+"  hpa");

//                    wind speed
                    JSONObject object11=jsonObject.getJSONObject("wind");
                    String win=object11.getString("speed");
                    wind.setText(win+" km/hr");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}