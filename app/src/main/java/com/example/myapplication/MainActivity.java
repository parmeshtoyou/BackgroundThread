package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private LocalBroadcastManager mLocalBroadcastManager;
    private IntentFilter intentFilter;
    final static String ACTION = "com.android.test.action.LOCAL_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);

        Button startService = findViewById(R.id.btn_start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartedService.class);
                startService(intent);
            }
        });

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter(ACTION);
        registerLocalBroadcast();
    }

    private void registerLocalBroadcast() {
        mLocalBroadcastManager.registerReceiver(mReceiver, intentFilter);
    }

    private void unRegisterLocalBroadcast() {
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("Broadcast", "msg");
            if (intent.getExtras() != null) {
                String text = intent.getExtras().getString("COUNTER_KEY");
                textView.setText(text);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterLocalBroadcast();
    }
}
