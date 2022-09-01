package com.company.blink;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {

    private static int MIN_SWIPE_DISTANCE_X=100;
    private static int MIN_SWIPE_DISTANCE_Y=100;

    private static int MAX_SWIPE_DISTANCE_X=1000;
    private static int MAX_SWIPE_DISTANCE_Y=1000;

    //activity the displays the message

    private Dashboard activity=null;


    public Dashboard getActivity(){
        return  activity;
    }

    public void setActivity(Dashboard activity)
    {
        this.activity=activity;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float deltaX=e1.getX()-e2.getX();
        float deltaY=e1.getY()-e2.getY();

        float deltaXAbs=Math.abs(deltaX);
        float deltaYAbs=Math.abs(deltaY);
        if(deltaXAbs>=MIN_SWIPE_DISTANCE_X && deltaXAbs<=MAX_SWIPE_DISTANCE_X)
        {
            if(deltaX>0)
            {
                this.activity.phoneContact();
            }else{
                this.activity.callPhone();
            }
        }
        if(deltaYAbs>=MIN_SWIPE_DISTANCE_Y && deltaYAbs<=MAX_SWIPE_DISTANCE_Y)
        {
            if(deltaY>0)
            {
                this.activity.sendSmss();
            }else{
                this.activity.mapOpen();
            }
        }
        return true;

    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        this.activity.TextToSpeechbtn();
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.activity.cll_emer_phn();
        return true;
    }


}

