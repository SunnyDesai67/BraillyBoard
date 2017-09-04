package com.example.taps.mykeyboard;

import android.app.Activity;
import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.ImageButton;


public class MyKeyboard extends InputMethodService {

    private MyView myView;


    @Override
    public View onCreateInputView()
    {

        myView = new MyView(this);
        myView.setInputMethodService(this);
        myView.setMinimumHeight(600);

        return myView;



    }

    public void type(String text)
    {
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(text, 1);

    }
    public void delete(int a,int b)
    {
        InputConnection ic = getCurrentInputConnection();
        ic.deleteSurroundingText(a, b);
    }
    public String speech_delete(int a,int flag)
    {
        InputConnection ic = getCurrentInputConnection();
        String s = (String) ic.getTextBeforeCursor(a,flag);
        return s;
    }
}