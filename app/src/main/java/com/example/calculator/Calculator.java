
package com.example.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/*
Basic Calculator Fragment
 */
public class Calculator extends Fragment {
    //display
    private TextView mainDisp, subDisp;
    private ForegroundColorSpan purple;
    private CharSequence toastText;
    private int toastDuration;
    private Toast toast;
    //variables
    private double hold, helper;
    private String curr, op;
    private boolean decimal, eq;

    public Calculator() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curr = "";
        op = "";
        hold = 0;
        helper = 0;
        decimal = false;
        eq = false;
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        Context context = this.getContext();
        //displays ----------------------------------------------------------
        mainDisp = Objects.requireNonNull(getView()).findViewById(R.id.cal_disp2);
        subDisp = Objects.requireNonNull(getView()).findViewById(R.id.cal_disp1);
        if(context != null)
            purple = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.purple_200));
        toastText = "Cannot enter numbers with more than 10 digits";
        toastDuration = Toast.LENGTH_SHORT;
        toast = Toast.makeText(context, toastText, toastDuration);
        //numpad ------------------------------------------------------------
        //numbers
        v.findViewById(R.id.cal_button_0).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_1).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_2).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_3).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_4).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_5).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_6).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_7).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_8).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_9).setOnClickListener(CalcListener);
        //operations
        v.findViewById(R.id.cal_button_clr).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_pct).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_sign).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_dot).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_add).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_sub).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_div).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_mul).setOnClickListener(CalcListener);
        v.findViewById(R.id.cal_button_equ).setOnClickListener(CalcListener);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    /*
    onClick and main function
    - performs basic calculator functions with positive/negative values
    - String curr holds what user types in, and its value is stored into Double hold for operations
    - String op holds the most recent arithmetic operation pressed
     */
    private final View.OnClickListener CalcListener = new View.OnClickListener() {
        public void onClick(View v) {
            if(eq) {
                curr = "";
                eq = false;
            }
            switch (v.getId()) {
                case (R.id.cal_button_0):
                    if(curr.length() <= 10)
                        curr += "0";
                    else
                        warn();
                    break;
                case (R.id.cal_button_1):
                    if(curr.length() <= 10)
                        curr += "1";
                    else
                        warn();
                    break;
                case (R.id.cal_button_2):
                    if(curr.length() <= 10)
                        curr += "2";
                    else
                        warn();
                    break;
                case (R.id.cal_button_3):
                    if(curr.length() <= 10)
                        curr += "3";
                    else
                        warn();
                    break;
                case (R.id.cal_button_4):
                    if(curr.length() <= 10)
                        curr += "4";
                    else
                        warn();
                    break;
                case (R.id.cal_button_5):
                    if(curr.length() <= 10)
                        curr += "5";
                    else
                        warn();
                    break;
                case (R.id.cal_button_6):
                    if(curr.length() <= 10)
                        curr += "6";
                    else
                        warn();
                    break;
                case (R.id.cal_button_7):
                    if(curr.length() <= 10)
                        curr += "7";
                    else
                        warn();
                    break;
                case (R.id.cal_button_8):
                    if(curr.length() <= 10)
                        curr += "8";
                    else
                        warn();
                    break;
                case (R.id.cal_button_9):
                    if(curr.length() <= 10)
                        curr += "9";
                    else
                        warn();
                    break;
                case(R.id.cal_button_dot):
                    if(!decimal)
                        curr += ".";
                    decimal = true;
                    break;
                case (R.id.cal_button_sign):
                    helper = Double.parseDouble(curr);
                    curr = String.valueOf(helper * -1);
                    break;
                case (R.id.cal_button_pct):
                    helper = Double.parseDouble(curr);
                    curr = String.valueOf(helper / 100);
                    break;
                case (R.id.cal_button_clr):
                    curr = "";
                    hold = 0;
                    decimal = false;
                    break;
                case (R.id.cal_button_add):
                    help();
                    op = "+";
                    break;
                case (R.id.cal_button_sub):
                    help();
                    op = "-";
                    break;
                case(R.id.cal_button_div):
                    help();
                    op = "/";
                    break;
                case(R.id.cal_button_mul):
                    help();
                    op = "*";
                    break;
                case (R.id.cal_button_equ):
                    if(!curr.equals(""))
                        helper = Double.parseDouble(curr);
                    switch(op) {
                        case("+"):
                            curr = String.valueOf(hold + helper);
                            break;
                        case("-"):
                            curr = String.valueOf(hold - helper);
                            break;
                        case("/"):
                            if(helper == 0)
                                curr = "ERROR";
                            else
                                curr = String.valueOf(hold / helper);
                            break;
                        case("*"):
                            curr = String.valueOf(hold * helper);
                            break;
                        default:
                            break;
                    }
                    hold = 0;
                    helper = 0;
                    eq = true;
                    break;
            }
            update();
        }
    };

    /*
    Helper function: updates display textViews
    - spannableString added for aesthetic purposes, allowing operation to be clearly seen
     */
    public void update() {
        if(!curr.equals(""))
            mainDisp.setText(curr);
        else
            mainDisp.setText("0");
        if(hold != 0) {
            SpannableString spannableString = new SpannableString(String.valueOf(hold) + " " + op + " ");
            spannableString.setSpan(purple, spannableString.length() -2, spannableString.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            subDisp.setText(spannableString);
        }
        else
            subDisp.setText("");
    }

    /*
    Helper function: updates current value for stacked operations
    - used by arithmetic operations (+,-,/,*)
     */
    public void help() {
        if(!curr.equals("")) {
            hold += Double.parseDouble(curr);
            curr = "";
        }
        decimal = false;
    }
    /*
    Helper function: prevents user from inputting more than 10 digits
     */
    public void warn() {
        toast.show();
    }



}