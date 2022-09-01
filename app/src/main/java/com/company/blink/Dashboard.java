package com.company.blink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class Dashboard extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;
    TextView textView;
    TextToSpeech textToSpeech;
    ImageButton emergencyBtnCall;
    private GestureDetectorCompat gestureDetectorCompat=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView= findViewById(R.id.textView);
        emergencyBtnCall=findViewById(R.id.emergencyBtnCall);
        emergencyBtnCall.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                call_the_number();
            }

            private void call_the_number() {
                cll_emer_phn();

            }


        });
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                    textToSpeech.setLanguage(Locale.ENGLISH);
                 //   textToSpeech.setSpeechRate();

            }
        });




        SwipeGestureListener gestureListener=new SwipeGestureListener();
        gestureListener.setActivity(this);
        gestureDetectorCompat =new GestureDetectorCompat(this,gestureListener);
    }

    public void cll_emer_phn() {
        String EmergencyNumber = "999";
        if (ContextCompat.checkSelfPermission(Dashboard.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.CALL_PHONE}, 1234);
        } else {

            if (EmergencyNumber.length() > 0) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + EmergencyNumber));
                startActivity(intent);
            } else {

            }

        }

    }

    public void TextToSpeechbtn(){

        String s="Hello I'm your assistant.Please swipe up to message section.swipe down to go map section.swipe left to go contact list section.swipe right to go call section".toString();
        int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    public  void display(String message)
    {
        textView.setText(message);
    }



    public void phoneContact() {
        Intent intent=new Intent(this,readPhoneContact.class);
        startActivity(intent);
    }
    public  void callPhone()
    {
        Intent intent=new Intent(this,phoneCall.class);
        startActivity(intent);
    }
    public void mapOpen()
    {
        Intent intent=new Intent(this,Map.class);
        startActivity(intent);
    }
    public void sendSmss()
    {
        Intent intent=new Intent(this,readAndTextMessage.class);
        startActivity(intent);
    }



}