package com.example.searchapplication;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button bt_search;
    Button bt_clear;


    private static final int pic_id = 123;
    Button camera_open_id;
    Button btnActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivity = (Button) findViewById(R.id.bt_next);
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });



        camera_open_id = (Button) findViewById(R.id.camera_button);
        camera_open_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, pic_id);
            }
        });

        createNotificationChannel();

        Button button = findViewById(R.id.bt_notif);

        button.setOnClickListener(v -> {

            Toast.makeText(this, "Notification set succesful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long timeAtButtonClick = System.currentTimeMillis();

            long tenSecondsInMillis = 1000 * 10;

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    timeAtButtonClick + tenSecondsInMillis,
                    pendingIntent);
        });

        editText = findViewById(R.id.et_sebar);
        bt_search = findViewById(R.id.bt_search);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                String term = editText.getText().toString();

                if (term.matches("")) {
                    Toast.makeText(MainActivity.this, "You didn`t type anything to search!", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra(SearchManager.QUERY, term);
                startActivity(intent);

            }
        });
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SearchApplicationReminderChannel";
            String description = "Channel for SearchApplication Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyApp", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Button btn = (Button)findViewById(R.id.bt_next);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewActivity.class));
            }
        });
    }


    }

