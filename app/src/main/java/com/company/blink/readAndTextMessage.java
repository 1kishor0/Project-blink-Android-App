package com.company.blink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class readAndTextMessage extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;
    private static final int REQUEST_CODE_SPEECH_INPUT2 = 1224;

     EditText messageBox2,messageBox3;
    ImageButton sendSmsBtn,msgVoiceBtn;
    TextToSpeech textToSpeech;
    private GestureDetectorCompat gestureDetectorCompat=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_and_text_message);
        swipeGestureForTextMessage gestureListener=new swipeGestureForTextMessage();
        gestureListener.setActivity(this);
        gestureDetectorCompat =new GestureDetectorCompat(this,gestureListener);


        messageBox2=findViewById(R.id.messageBox2);
        messageBox3=findViewById(R.id.messageBox3);
        sendSmsBtn=findViewById(R.id.sendSmsBtn);
        msgVoiceBtn=findViewById(R.id.msgVoiceBtn);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                textToSpeech.setLanguage(Locale.ENGLISH);
                //   textToSpeech.setSpeechRate();

            }
        });

        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        SendSms();
                    }else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });

        msgVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak_number();
            }
        });
        msgVoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak_number2();
            }
        });


    }
    public void TextToSpeechbtn(){

        String s="Hello Im from the Message section.Swipe up to say the Number to send the message, Swipe down to say what you want to text and double click to send the message".toString();
        int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }

     boolean speak_number2() {
        Intent intent =new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak");



        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT2);

        }catch (Exception e)
        {
            Toast.makeText(readAndTextMessage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;
    }



    boolean speak_number() {
        Intent intent =new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak");



        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e)
        {
            Toast.makeText(readAndTextMessage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
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

                            messageBox2.setText(result.get(0));




                }
                break;
            }
            case REQUEST_CODE_SPEECH_INPUT2:{
                if(resultCode==RESULT_OK && null!=data)
                {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    messageBox3.setText(result.get(0));




                }
                break;
            }

        }
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }
    public  void display(String message)
    {
        messageBox2.setText(message);
    }
    void SendSms()
    {
        String phoneNo=messageBox2.getText().toString().trim();
        String SMS=messageBox3.getText().toString().trim();
    try {
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo,null,SMS,null,null);
        Toast.makeText(this, "Message is sent", Toast.LENGTH_SHORT).show();
    }catch (Exception e)
    {
        e.printStackTrace();
        Toast.makeText(this,"Failed to send message",Toast.LENGTH_SHORT).show();

    }


    }
}