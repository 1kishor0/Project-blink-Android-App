package com.company.blink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class phoneCall extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;
    ImageButton voice_btn;
    ImageButton btn_call;
    EditText et_num;
    TextView textView2;
    TextToSpeech textToSpeech;
    private GestureDetectorCompat gestureDetectorCompat=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_call);


        swipeGestureForPhoneCall gestureListener=new swipeGestureForPhoneCall();
        gestureListener.setActivity(this);
        gestureDetectorCompat =new GestureDetectorCompat(this,gestureListener);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                textToSpeech.setLanguage(Locale.ENGLISH);
                //   textToSpeech.setSpeechRate();

            }
        });

        btn_call=findViewById(R.id.buttonCallId);
        et_num=findViewById(R.id.et_numid);
        voice_btn=findViewById(R.id.voice_btn);


        voice_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                speak();
            }

        });



            btn_call.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    call_the_number();
                }

                private void call_the_number() {
                    cll_phn();

                }
            });


    }
    public void TextToSpeechbtn(){

        String s="Hello Im from the call section.Swipe up to say the number and double click to call".toString();
        int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }
    public  void display(String message)
    {
        textView2.setText(message);
    }
    public void speak() {
        Intent intent =new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hey Speak your number");


        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e)
        {
            Toast.makeText(phoneCall.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQUEST_CODE_SPEECH_INPUT:{
                if(resultCode==RESULT_OK && null!=data)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_num.setText(result.get(0));
                }
                break;
            }
        }
    }


    public void cll_phn() {
        String number = et_num.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(phoneCall.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(phoneCall.this, new String[]{Manifest.permission.CALL_PHONE}, 1234);
        } else {

            if (number.length() > 0) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                startActivity(intent);
            } else {
                et_num.setError("This is a mandatory field");
            }

        }
    }
}


