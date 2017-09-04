package com.example.taps.mykeyboard;

import android.app.Activity;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyView extends LinearLayout implements View.OnTouchListener ,GestureDetector.OnGestureListener
{

    Boolean x = true;

    Context myContext;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6;
    ImageButton back;
    ImageButton s;

    int a = 0, b = 0;

    DatabaseAccess databaseAccess;
    Vibrator vb;
    TextToSpeech t1;

    String t = new String();

    private GestureDetector gd;
    float sensitivity = 50;

    public MyView(Context c)
    {
        super(c);
        myContext = c;


        inflate(getContext(), R.layout.keyboard, this);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);

        s = (ImageButton) findViewById(R.id.space);
        back = (ImageButton) findViewById(R.id.back);

        iv1.setOnTouchListener(this);
        iv2.setOnTouchListener(this);
        iv3.setOnTouchListener(this);
        iv4.setOnTouchListener(this);
        iv5.setOnTouchListener(this);
        iv6.setOnTouchListener(this);

        s.setOnTouchListener(this);
        back.setOnTouchListener(this);

        vb = (Vibrator) this.myContext.getSystemService(Context.VIBRATOR_SERVICE);

        t1 = new TextToSpeech(myContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        gd = new GestureDetector(myContext, this);


        //database
        databaseAccess = DatabaseAccess.getInstance(myContext);
        databaseAccess.open();

    }

    private MyKeyboard keyboard;

    public void setInputMethodService(MyKeyboard k)
    {
        keyboard = k;
    }



    @Override
    public boolean onTouch(View v, MotionEvent event)
    {


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                {

                {
                    if (v == s)
                    {
                        keyboard.type(" ");
                        t1.speak("Space", TextToSpeech.QUEUE_ADD, null);
                        vb.vibrate(100);
                    }
                    if (v == back)
                    {
                        String s= keyboard.speech_delete(1,0);
                        if(s.equals(""))
                        {
                        }
                        else if(s.equals(" "))
                        {
                            t1.speak("Space" + " deleted", TextToSpeech.QUEUE_ADD, null);
                        }
                        else
                        {
                            t1.speak(s + " deleted", TextToSpeech.QUEUE_ADD, null);
                        }
                        keyboard.delete(1, 0);
                        vb.vibrate(100);
                    }

                    if (v == iv1)
                    {
                        vb.vibrate(100);
                        t = t + "1";
                        a = 1;
                        x = true;
                        display();

                    }

                    if (v == iv2) {
                        vb.vibrate(100);

                        t = t + "2";
                        a = 1;
                        x = true;
                        display();
                    }
                    if (v == iv3) {
                        vb.vibrate(100);
                        t = t + "3";
                        a = 1;
                        x = true;
                        display();
                    }
                    if (v == iv4) {
                        vb.vibrate(100);
                        t = t + "4";
                        a = 1;
                        x = true;
                        display();
                    }
                    if (v == iv5) {
                        vb.vibrate(100);
                        t = t + "5";
                        a = 1;
                        x = true;
                        display();
                    }
                    if (v == iv6) {
                        vb.vibrate(100);
                        t = t + "6";
                        a = 1;
                        x = true;
                        display();
                    }

                    break;
                }

            }

        }

        return true;

    }

    public void display()
    {
        a = 0;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (a == 0)
                {

                    String quotes = databaseAccess.getQuotes(t);
                    if(x == true)
                    {

                        if (quotes.equals(""))
                        {
                            Toast.makeText(myContext, "Not A Valid Braille Code", Toast.LENGTH_SHORT).show();
                            t1.speak("Not A Valid Braille Code", TextToSpeech.QUEUE_ADD, null);
                        }
                        else
                        {
                            keyboard.type(quotes);
                            t1.speak(quotes, TextToSpeech.QUEUE_ADD, null);
                        }
                        x=false;
                    }

                 t = new String();

                }


            }
        }, 1300);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gd.onTouchEvent(event);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        if(e1.getY() - e2.getY() > sensitivity)
        {
            Toast.makeText(myContext, "up", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
