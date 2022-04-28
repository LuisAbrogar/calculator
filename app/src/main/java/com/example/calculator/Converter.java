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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


import java.util.Objects;

public class Converter extends Fragment implements AdapterView.OnItemSelectedListener {
    private Resources res;
    private TextView fromNum, fromUnit, toNum, toUnit;

    Spinner fromSpin, toSpin;
    ArrayAdapter<String> arrayAdapter;

    //units for spinners
    String[] curr, units;
    public Converter() {
        // Required empty public constructor
    }


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
        res = getResources();
        Context context = getContext();
        //tabs
        TabLayout tabLayout = Objects.requireNonNull(getView()).findViewById(R.id.con_tabs);
        //textviews
        fromNum = Objects.requireNonNull(getView()).findViewById(R.id.con_from_num);
        fromUnit = Objects.requireNonNull(getView()).findViewById(R.id.con_from_unit);
        toNum = Objects.requireNonNull(getView()).findViewById(R.id.con_to_num);
        toUnit = Objects.requireNonNull(getView()).findViewById(R.id.con_to_unit);
        //spinners
        fromSpin = Objects.requireNonNull(getView()).findViewById(R.id.con_from_spin);
        toSpin = Objects.requireNonNull(getView()).findViewById(R.id.con_to_spin);
        fromSpin.setOnItemSelectedListener(this);
        toSpin.setOnItemSelectedListener(this);
        curr = res.getStringArray(R.array.con_arr_len);
        units = res.getStringArray(R.array.con_arr_len_u);
        changeSpinner(context, curr);

        //listeners
        //init first tab
        tabLayout.getTabAt(0).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 switch(tab.getPosition()) {
                     case 0:    //length
                         curr = res.getStringArray(R.array.con_arr_len);
                         units = res.getStringArray(R.array.con_arr_len_u);
                         changeSpinner(context, curr);
                         break;
                     case 1:    //mass
                         curr = res.getStringArray(R.array.con_arr_mass);
                         units = res.getStringArray(R.array.con_arr_mass_u);
                         changeSpinner(context, curr);
                         break;
                     case 2:    //temperature
                         curr = res.getStringArray(R.array.con_arr_temp);
                         units = res.getStringArray(R.array.con_arr_temp_u);
                         changeSpinner(context, curr);
                         break;
                     case 3:    //time
                         curr = res.getStringArray(R.array.con_arr_time);
                         units = res.getStringArray(R.array.con_arr_time_u);
                         changeSpinner(context, curr);
                         break;
                     case 4:    //speed
                         curr = res.getStringArray(R.array.con_arr_speed);
                         units = res.getStringArray(R.array.con_arr_speed_u);
                         changeSpinner(context, curr);
                         break;
                 }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case(R.id.con_from_spin): //from
                fromUnit.setText(units[position]);
                break;
            case(R.id.con_to_spin): //to
                toUnit.setText(units[position]);
                break;
        }
        

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void changeSpinner(Context context, String[] arr) {
        arrayAdapter = new ArrayAdapter<>(context, R.layout.spinner_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpin.setAdapter(arrayAdapter);
        toSpin.setAdapter(arrayAdapter);
    }
}