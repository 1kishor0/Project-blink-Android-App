package com.company.blink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class readPhoneContact extends AppCompatActivity{

    TextView contactTxt1;
    TextToSpeech textToSpeech;
    ArrayList<String> arrayList;
    private GestureDetectorCompat gestureDetectorCompat=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_phone_contact);

        contactTxt1=findViewById(R.id.contactTxt1);
        swipeGestureForReadContact gestureListener=new swipeGestureForReadContact();
        gestureListener.setActivity(this);
        gestureDetectorCompat =new GestureDetectorCompat(this,gestureListener);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                textToSpeech.setLanguage(Locale.ENGLISH);
                //   textToSpeech.setSpeechRate();

            }
        });

        //to initialize the memory to arraylist
        arrayList=new ArrayList<>();
        //give runtime permission for read contact
            //this problem comes in marshmallow or greater version
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            //request the permission
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        else {
                //for lower than marshmallow version
            //to get the phone book
            getcontact();
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



    public void TextToSpeechbtn2(){

        String s="please double tap to read your contact list!".toString();
        int speech=textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    public void getcontact()
    {
        //to pass all phoneboom to cursor
        Cursor cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        //to fetch all contact from cursor
        while (cursor.moveToNext())
        {
            //Pass thr data into string from cursor
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String mobile=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            //now add the data into arrayList
            arrayList.add("Name:" +name+"\n"+"Number:"+ mobile+"\n\n\n\n");
            //to attatch the arrayList into textView
            contactTxt1.setText(arrayList.toString());
        }

    }
    //to get the output of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //now permission is granted call function again
                getcontact();
            }
        }

    }



}
