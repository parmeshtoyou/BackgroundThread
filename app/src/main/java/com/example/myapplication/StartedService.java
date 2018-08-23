package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class StartedService extends Service {

    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        intent.setAction(MainActivity.ACTION);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getBaseContext());

        startBackgroundThread(intent);
        return START_STICKY;
    }

    private void startBackgroundThread(final Intent intent) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.d("Background", "Thread:" + Thread.currentThread().getId() + ", count:" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("COUNTER_KEY", String.valueOf(i));
                    intent.putExtras(bundle);
                    mLocalBroadcastManager.sendBroadcast(intent);
                }
            }
        }.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
