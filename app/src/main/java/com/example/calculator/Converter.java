package com.example.calculator;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;


import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Converter extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final int MAX_DIGITS = 8;
    private Resources res;
    private TextView fromNum, fromUnit, toNum, toUnit;
    private Spinner fromSpin, toSpin;
    private boolean decimal;
    private String[] curr, units;
    private String numFrom;
    private int indexFrom, indexTo;

    //CONVERSION VALUES
    private double[] selected;
    private final double[] lenTable = {1000, 100, 1, 0.001, 39.3701, 3.2808, 1.0936, 0.0006};   //base: meter   | mm, cm, m, km, in, ft, yd, mi
    private final double[] massTable = {0.0022, 0.0353, 1, 0.001, 0.000001};                    //base: gram    | lb, oz, g, kg, t
    private final double[] tempTable = {1.8};
    private final double[] timeTable = {60000, 60, 1, 0.0167, 0.00069, 0.0000092};              //base: min     | ms, s, min, h, d, wk
    private final double[] speedTable = {0.447, 1.467, 1.609, 1, 0.869};                        //base: mi/h    | m/s, ft/s, km/h, mi/h, kt
    public Converter() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numFrom = "";
        decimal = false;
        res = getResources();
        Context context = getContext();
        //tabs      -----------------------------------
        TabLayout tabLayout = Objects.requireNonNull(getView()).findViewById(R.id.con_tabs);
        //textviews -----------------------------------
        fromNum = Objects.requireNonNull(getView()).findViewById(R.id.con_from_num);
        fromUnit = Objects.requireNonNull(getView()).findViewById(R.id.con_from_unit);
        toNum = Objects.requireNonNull(getView()).findViewById(R.id.con_to_num);
        toUnit = Objects.requireNonNull(getView()).findViewById(R.id.con_to_unit);
        //spinners  -----------------------------------
        fromSpin = Objects.requireNonNull(getView()).findViewById(R.id.con_from_spin);
        toSpin = Objects.requireNonNull(getView()).findViewById(R.id.con_to_spin);
        fromSpin.setOnItemSelectedListener(this);
        toSpin.setOnItemSelectedListener(this);
        curr = res.getStringArray(R.array.con_arr_len);
        units = res.getStringArray(R.array.con_arr_len_u);
        changeSpinner(context, curr);
        //buttons   ----------------------------------
        v.findViewById(R.id.con_pad_0).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_1).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_2).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_3).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_4).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_5).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_6).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_7).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_8).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_9).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_clr).setOnClickListener(convListener);
        v.findViewById(R.id.con_pad_dot).setOnClickListener(convListener);
        /*
        Tab Selection Listener
         */
        tabLayout.getTabAt(0).select();     //select first tab at start
        selected = lenTable;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                decimal = false;
                numFrom = "";
                update();
                switch(tab.getPosition()) {
                     case 0:    //length
                         curr = res.getStringArray(R.array.con_arr_len);
                         units = res.getStringArray(R.array.con_arr_len_u);
                         changeSpinner(context, curr);
                         selected = lenTable;
                         break;
                     case 1:    //mass
                         curr = res.getStringArray(R.array.con_arr_mass);
                         units = res.getStringArray(R.array.con_arr_mass_u);
                         changeSpinner(context, curr);
                         selected = massTable;
                         break;
                     case 2:    //temperature
                         curr = res.getStringArray(R.array.con_arr_temp);
                         units = res.getStringArray(R.array.con_arr_temp_u);
                         changeSpinner(context, curr);
                         selected = tempTable;
                         break;
                     case 3:    //time
                         curr = res.getStringArray(R.array.con_arr_time);
                         units = res.getStringArray(R.array.con_arr_time_u);
                         changeSpinner(context, curr);
                         selected = timeTable;
                         break;
                     case 4:    //speed
                         curr = res.getStringArray(R.array.con_arr_speed);
                         units = res.getStringArray(R.array.con_arr_speed_u);
                         changeSpinner(context, curr);
                         selected = speedTable;
                         break;
                 }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    /*
    Spinner selection listener
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case(R.id.con_from_spin): //from
                fromUnit.setText(units[position]);
                indexFrom = position;
                break;
            case(R.id.con_to_spin): //to
                toUnit.setText(units[position]);
                indexTo = position;
                break;
        }
        update();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /*
    Helper function - changes spinner values
     */
    public void changeSpinner(Context context, String[] arr) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpin.setAdapter(arrayAdapter);
        toSpin.setAdapter(arrayAdapter);
    }

    /*
    Button OnClick Listener
     */
    private final View.OnClickListener convListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (numFrom.length() <= MAX_DIGITS) {
                switch (v.getId()) {
                    case (R.id.con_pad_0):
                        numFrom += "0";
                        break;
                    case (R.id.con_pad_1):
                        numFrom += "1";
                        break;
                    case (R.id.con_pad_2):
                        numFrom += "2";
                        break;
                    case (R.id.con_pad_3):
                        numFrom += "3";
                        break;
                    case (R.id.con_pad_4):
                        numFrom += "4";
                        break;
                    case (R.id.con_pad_5):
                        numFrom += "5";
                        break;
                    case (R.id.con_pad_6):
                        numFrom += "6";
                        break;
                    case (R.id.con_pad_7):
                        numFrom += "7";
                        break;
                    case (R.id.con_pad_8):
                        numFrom += "8";
                        break;
                    case (R.id.con_pad_9):
                        numFrom += "9";
                        break;
                    case (R.id.con_pad_clr):
                        numFrom = "";
                        decimal = false;
                        break;
                    case (R.id.con_pad_dot):
                        if(!decimal)
                            numFrom += ".";
                        decimal = true;
                        break;
                }   //end switch
                update();
            }
            else if (v.getId() == R.id.con_pad_clr) {
                numFrom = "";
                update();
            }
            else
                warn();
        }
    };

    /*
    Helper Function - performs calculations and displays output
     */
    public void update() {
        if(!numFrom.equals("")) {
            double helper = Double.parseDouble(numFrom);
            if(!Arrays.equals(selected, tempTable)) {
                helper /= selected[indexFrom];
                helper *= selected[indexTo];
            }
            else {
                //temperature has a longer formula
                if(indexFrom ==  indexTo) {
                    fromNum.setText(numFrom);
                    toNum.setText(numFrom);
                }
                else if(indexFrom == 0) {
                    //C to F
                    if(indexTo == 1) {
                        helper *= 1.8;
                        helper += 32;
                    }
                    //C to K
                    else
                        helper += 273.15;
                }
                else if(indexFrom == 1) {
                    //F to C
                    helper -= 32;
                    helper /= 1.8;
                    //F to K
                    if(indexTo == 2)
                        helper += 273.15;
                }

                else if(indexFrom == 2) {
                    //K to F
                    if(indexTo == 1) {
                        helper -= 273.15;
                        helper *= 1.8;
                        helper += 32;
                    }
                    //K to C
                    else
                        helper -= 273.15;
                }
            }
            fromNum.setText(numFrom);
            toNum.setText(String.format(Locale.ENGLISH, helper % 1 == 0 ? "%8s" : "%.3f", helper));
        }
        else {
            fromNum.setText("0");
            toNum.setText("0");
        }
    }

    /*
    Helper Function - displays warning when MAX_DIGITS is reached during input
     */
    public void warn() {
        CharSequence toastText = "Cannot enter numbers with more than " + MAX_DIGITS + " digits";
        int toastDuration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), toastText, toastDuration);
        toast.show();
    }
}