package com.example.calculator;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Locale;
import java.util.Objects;


public class Restaurant extends Fragment {
    //display elements
    private TextView dispAmt, tax_t;
    private EditText total, tax, tip, ppl;
    private ToggleButton split;
    //variables
    private Boolean isEven;
    private double amt_to, amt_tp, amt_tx, amt_pp, ans;
    private String amt;

    public Restaurant() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initializing
        amt = "";
        amt_to = 0;
        amt_tp = 0;
        amt_tx = 0;
        amt_pp = 0;
        ans = 0;
        isEven = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this.getContext();
        //display
        dispAmt = Objects.requireNonNull(getView()).findViewById(R.id.rst_amt);
        tax_t = Objects.requireNonNull(getView()).findViewById(R.id.rst_tax_t);
        //EditTexts
        total = Objects.requireNonNull(getView()).findViewById(R.id.rst_total);
        tax = Objects.requireNonNull(getView()).findViewById(R.id.rst_tax);
        tip = Objects.requireNonNull(getView()).findViewById(R.id.rst_tip);
        ppl = Objects.requireNonNull(getView()).findViewById(R.id.rst_ppl);
        //toggle
        split = Objects.requireNonNull(getView()).findViewById(R.id.rst_split);

        //tax only visible when it is uneven split
        tax_t.setVisibility(View.INVISIBLE);
        tax.setVisibility(View.INVISIBLE);
        //setting listener
        split.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    tax_t.setVisibility(View.INVISIBLE);
                    tax.setVisibility(View.INVISIBLE);
                    isEven = true;
                }
                else {
                    tax_t.setVisibility(View.VISIBLE);
                    tax.setVisibility(View.VISIBLE);
                    isEven = false;
                }
                update();
            }
        });
        total.setOnEditorActionListener(editListener);
        tip.setOnEditorActionListener(editListener);
        tax.setOnEditorActionListener(editListener);
        ppl.setOnEditorActionListener(editListener);
    }

    /*
    TextView listener and main function
    - stores values to be use for calculation
     */
    private final TextView.OnEditorActionListener editListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch(v.getId()) {
                case(R.id.rst_total):
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        if(total.getText().toString().equals(""))
                            break;
                        amt_to = Double.parseDouble(total.getText().toString());
                        update();
                    }
                    break;
                case(R.id.rst_tip):
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        if(tip.getText().toString().equals(""))
                            break;
                        amt_tp = Double.parseDouble(tip.getText().toString()) / 100;
                        update();
                    }
                    break;
                case(R.id.rst_ppl):
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        if(ppl.getText().toString().equals(""))
                            break;
                        amt_pp = Double.parseDouble(ppl.getText().toString());
                        update();
                    }
                    break;
                case(R.id.rst_tax):
                    if(actionId == EditorInfo.IME_ACTION_DONE) {
                        if(tax.getText().toString().equals(""))
                            break;
                        amt_tx = Double.parseDouble(tax.getText().toString()) / 100;
                        update();
                    }
                    break;
            }
            return false;
        }
    };

    /*
    Helper function: updates 'amount per person' textView
    - calculates the amount only when all applicable fields are filled out
     */
    public void update() {
        if(isEven) {
            if(amt_to != 0 && amt_tp != 0 && amt_pp != 0) {
                ans = (amt_to * (1 + amt_tp)) / amt_pp;
            }
            else ans = 0;
            amt = String.format(Locale.ENGLISH,"$ %.2f", ans);
        }
        else {
            if(amt_to != 0 && amt_tp != 0 && amt_pp != 0 & amt_tx != 0) {
                double tip = amt_tp * amt_to;
                double tax = amt_tx * amt_to;
                ans = (tip / amt_pp) + (tax / amt_pp);
            }
            else ans = 0;
            amt = String.format(Locale.ENGLISH,"$ +%.2f", ans);
        }

        dispAmt.setText(amt);

    }
}