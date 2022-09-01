package com.company.blink;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;




public class Map extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1234;
    private FloatingActionButton navigation_btn;
    //initialize variable
    EditText etDestination;
    TextToSpeech textToSpeech;
    ImageButton voice_btn2;
    TextView maptxt1;
    EditText et_destination;
    private GestureDetectorCompat gestureDetectorCompat=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                textToSpeech.setLanguage(Locale.ENGLISH);
                //   textToSpeech.setSpeechRate();

            }
        });
        swipeGestureForMap gestureListener=new swipeGestureForMap();
        gestureListener.setActivity(this);
        gestureDetectorCompat =new GestureDetectorCompat(this,gestureListener);

        //Assign Variabe
        voice_btn2=findViewById(R.id.voice_btn2);

        etDestination=findViewById(R.id.et_destination);

        navigation_btn=findViewById(R.id.launch);
        navigation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {callMap();}
            private void callMap()
            {
                mapcalling();
            }
        });


        voice_btn2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                speak();
            }

        });



    }
    public void TextToSpeechbtn(){

        String s="Hello Im from the map section.Swipe up to say your destination and double click to navigate".toString();
        int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    //source location
    public void speak() {
        Intent intent =new Intent (RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hey Speak source location");


        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);

        }catch (Exception e)
        {
            Toast.makeText(Map.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        etDestination.setText(result.get(0));

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

        //maptxt1.setText(message);
    }
public void mapcalling()
{

    String sDestination=etDestination.getText().toString();
    //check Condition
    if(sDestination.equals(""))
    {
        //when both value blank

        Toast.makeText(getApplicationContext(), "Enter destination ", Toast.LENGTH_SHORT).show();
    }else {
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("google.navigation:q="+sDestination+"&mode=w"));
        intent.setPackage("com.google.android.apps.maps");

        startActivity(intent);

    }
}

}
