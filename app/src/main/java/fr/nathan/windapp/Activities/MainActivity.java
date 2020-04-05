package fr.nathan.windapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.nathan.windapp.Adapters.RecyclerViewAdapter;
import fr.nathan.windapp.Interfaces.RecyclerViewClickListener;
import fr.nathan.windapp.Listeners.RecyclerTouchListener;
import fr.nathan.windapp.Models.GsonRequest;
import fr.nathan.windapp.Models.Prevision;
import fr.nathan.windapp.Models.Previsions;
import fr.nathan.windapp.Models.VolleyHelper;
import fr.nathan.windapp.Models.Weather;
import fr.nathan.windapp.R;

public class MainActivity extends AppCompatActivity {

    private List<Prevision> dataPrevision;
    private ArrayList<String> cities = new ArrayList<>();
    private Spinner spinner_cities;
    private Button btn_map;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView textViewPrevisions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        initUI();
        initCities();

        ArrayAdapter<String> citiesArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        citiesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cities.setAdapter(citiesArrayAdapter);

        openWeatherData(spinner_cities.getSelectedItem().toString());

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                String city = spinner_cities.getSelectedItem().toString().replaceAll(" ", "+");
                Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                        .appendQueryParameter("q", city)
                        .build();
                intent.setData(geoLocation);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        spinner_cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                openWeatherData(spinner_cities.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.rvWeather);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(dataPrevision);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intentWeather = new Intent(getApplicationContext(), WeatherActivity.class);
                intentWeather.putExtra("prevision", dataPrevision.get(position));
                intentWeather.putExtra("city", spinner_cities.getSelectedItem().toString());
                startActivity(intentWeather);
            }
        }));
    }

    private void openWeatherData(String city) {
        //AIDE VOLLEY :
        //https://developer.android.com/training/volley/requestqueue.html
        //https://developer.android.com/training/volley/request-custom.html
        // requestQueue = Volley.newRequestQueue(getApplicationContext());
        dataPrevision = new ArrayList<>();

        final GsonRequest gsonRequest = new GsonRequest(getUrlOpenWeather(city), Previsions.class, null, new Response.Listener<Previsions>() {
            @Override
            public void onResponse(Previsions previsions) {
                for (Prevision prev : previsions.getList()) {
                    dataPrevision.add(new Prevision(prev.getDt_txt(), prev.getMain(), prev.getWeather(), prev.getWind()));
                }

                initializeRecyclerView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) Log.e("MainActivity", volleyError.getMessage());
            }
        });

        VolleyHelper.getInstance(getApplicationContext()).addToRequestQueue(gsonRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_home:
                break;
            case R.id.action_settings:
                Intent intentSetting = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.action_about:
                Intent intentAbout = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intentAbout);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initCities() {
        cities.add("Annecy");
        cities.add("Le Bourget-du-Lac");
        cities.add("Thonon-les-Bains");
    }

    public String getUrlOpenWeather(String city) {
        String city_formated = city.replaceAll(" ", "+");

        return "https://api.openweathermap.org/data/2.5/forecast?q=" + city_formated + "&units=metric&appid=e350a9a737f730d58298ea189ede8287";

    }

    private void initUI() {
        spinner_cities = findViewById(R.id.spinner_cities);
        btn_map = findViewById(R.id.btn_map);
    }

}
