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
import android.widget.CheckBox;
import android.widget.Toast;

import fr.nathan.windapp.Models.Setting;
import fr.nathan.windapp.R;

public class SettingActivity extends AppCompatActivity {

    private CheckBox checkbox_temperature, checkbox_speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);

        checkbox_temperature = findViewById(R.id.checkbox_temperature);
        checkbox_speed = findViewById(R.id.checkbox_speed);

        //checkbox_temperature.setChecked(Setting.TEMPERATURE_IS_IN_CELSIUS);

        checkbox_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.TEMPERATURE_IS_IN_CELSIUS = !Setting.TEMPERATURE_IS_IN_CELSIUS;
            }
        });

        checkbox_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting.SPEED_IS_IN_KM_H = !Setting.SPEED_IS_IN_KM_H;
            }
        });

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
                Intent intentMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
                break;
            case R.id.action_settings:
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

}
