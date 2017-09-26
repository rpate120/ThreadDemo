package com.example.rohan.threaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //Log.d("demo", "Message Received" + msg.what);

                switch (msg.what){

                    case DoWork.STATUS_START:
                        Log.d("demo", "starting....");
                        break;

                    case DoWork.STATUS_PROGRESS:
                        Log.d("demo", "progress...." + msg.getData().getInt(DoWork.PROGRESS_KEY));
                        break;

                    case DoWork.STATUS_STOP:
                        Log.d("demo", "stopping....");
                        break;
                }
                return false;
            }
        });

        new Thread(new DoWork()).start();

    }

    class DoWork implements Runnable{
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;
        static final String PROGRESS_KEY = "PROGRESS";

        @Override
        public void run() {
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 1000000; j++) {
                }
                Message message = new Message();
                message.what = STATUS_PROGRESS;
                //message.obj = (Integer) i;
                Bundle bundle = new Bundle();
                bundle.putInt(PROGRESS_KEY, (Integer) i);
                message.setData(bundle);
                handler.sendMessage(message);
            }
            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);
        }
    }
}
