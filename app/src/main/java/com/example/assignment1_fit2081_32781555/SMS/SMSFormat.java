package com.example.assignment1_fit2081_32781555.SMS;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.security.Key;
import java.util.StringTokenizer;

public class SMSFormat {

    private static final int TOKEN_INDEX = 0;
    private static final int DATA_INDEX = 1;

    public static final int CATEGORY_AMT_TOKENS = 3;
    public static final int EVENT_AMT_TOKENS = 4;

    public static final int CAT_NAME_INDEX = 0;
    public static final int CAT_EVENT_COUNT_INDEX = 1;
    public static final int CAT_IS_ACTIVE_INDEX = 2;

    public static final int EVENT_NAME_INDEX = 0;
    public static final int EVENT_CAT_ID_INDEX = 1;
    public static final int EVENT_TICKETS_INDEX = 2;
    public static final int EVENT_IS_ACTIVE_INDEX = 3;

    public static boolean isValid(String key,String message){
        //Separate token from data
        String[] s = message.toLowerCase().split(":",-1);
        return (s.length == 2 && s[TOKEN_INDEX].equals(key));
    }
        public static String extractData(String message){
        StringTokenizer st = new StringTokenizer(message,":");
        st.nextToken();
        return st.nextToken();
    }

    public static void updateUI(EditText et,String input){
        et.setText(input);
    }
    public static void updateUIButton(Context context, Switch button, String input){
        if (input.equals("true")){button.setChecked(true);}
        else if (input.equals("false")){button.setChecked(false);}
        else{errorMsg(context);}
    }

    public static void errorMsg(Context context){
        Toast.makeText(context,"Invalid message format!",Toast.LENGTH_SHORT).show();
    }

    public static boolean invalidTrueFalse(String s){
        return !(s.equals("true") || s.equals("false"));
    }

}
