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
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import fr.nathan.windapp.R;

public class EmailActivity extends AppCompatActivity {

    EditText edit_text_email;
    Button btn_invite_send;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        initUI();


        city = (String) getIntent().getSerializableExtra("city");
        final String date = (String) getIntent().getSerializableExtra("date");

        btn_invite_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(edit_text_email.getText().toString()).find()) {
                    edit_text_email.setError("Format invalide");
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{edit_text_email.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Viens avec moi à " + city + " !");
                    intent.putExtra(Intent.EXTRA_TEXT, "Viens me rejoindre " + getPreposition() + " " + getCity() + " le " + date);

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
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
        edit_text_email = findViewById(R.id.edit_text_email);
        btn_invite_send = findViewById(R.id.btn_invite_send);
    }

    public String getPreposition() {
        return city.equals("Le Bourget-du-Lac") ? "au" : "à";
    }

    public String getCity() {
        return city.equals("Le Bourget-du-Lac") ? "Bourget-du-Lac" : city;
    }
}
