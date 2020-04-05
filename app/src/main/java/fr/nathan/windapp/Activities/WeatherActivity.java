package fr.nathan.windapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;

import fr.nathan.windapp.Models.Prevision;
import fr.nathan.windapp.Models.Setting;
import fr.nathan.windapp.Models.Weather;
import fr.nathan.windapp.R;

public class WeatherActivity extends AppCompatActivity {

    TextView city, date_weather, temperature_weather, unit_temperature_weather,  speed_wind, position_wind;
    private Button btn_invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        initUI();

        final Prevision prevision = (Prevision) getIntent().getSerializableExtra("prevision");
        final String cityStr = (String) getIntent().getSerializableExtra("city");
        assert prevision != null;

        city.setText(cityStr);
        try {
            date_weather.setText(prevision.getWeatherActivityFormatedDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        temperature_weather.setText(getTextDegree(prevision.getMain().getFeels_like()));
        unit_temperature_weather.setText(Setting.TEMPERATURE_IS_IN_CELSIUS ? "°C" : "°F");
        speed_wind.setText(getTextSpeed(prevision.getWind().getSpeed()));
        position_wind.setText(getDirectionName(prevision.getWind().getDeg()));


        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEmail = new Intent(getApplicationContext(), EmailActivity.class);
                intentEmail.putExtra("city", cityStr);
                try {
                    intentEmail.putExtra("date", prevision.getFormatedDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startActivity(intentEmail);
            }
        });
    }

    private String getTextSpeed(double speed) {
        if(Setting.SPEED_IS_IN_KM_H) return Double.toString(Math.round(speed * 3.6  * 100.0 ) /100.0) + " Km/h";
        return Double.toString(Math.round(speed * 1.94  * 100.0 ) /100.0) + " noeud";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public String getTextDegree(double degree) {
        if(!Setting.TEMPERATURE_IS_IN_CELSIUS)
            return String.valueOf((int) (degree * 1.8 + 32));

        return String.valueOf((int) Math.round(degree));
    }

    public String getDirectionName(int position) {
        if (position > 337) return "Nord";
        if (position > 292) return "Nord-ouest";
        if (position > 247) return "Ouest";
        if (position > 202) return "Sud-ouest";
        if (position > 157) return "Sud";
        if (position > 122) return "Sud Est";
        if (position > 67) return "Est";
        if (position > 22) return "Nord-est";

        return "Nord";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
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

    private void initUI() {
        this.city = findViewById(R.id.city);
        this.date_weather = findViewById(R.id.date_weather);
        this.temperature_weather = findViewById(R.id.temperature_weather);
        this.unit_temperature_weather = findViewById(R.id.unit_temperature_weather);
        this.speed_wind = findViewById(R.id.speed_wind);
        this.position_wind = findViewById(R.id.position_wind);
        this.btn_invite = findViewById(R.id.btn_invite);
    }

}
