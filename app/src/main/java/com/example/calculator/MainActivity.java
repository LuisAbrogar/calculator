package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavSelect);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Calculator()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavSelect = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch(item.getItemId()) {
                case(R.id.restaurant):
                    fragment = new Restaurant();
                    break;
                case(R.id.calculator):
                    fragment = new Calculator();
                    break;
                case(R.id.converter):
                    fragment = new Converter();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };


}