package com.example.vazquez.proyectouaq;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    public void nextActivity0(View view){
        startActivity(new Intent(this, MenuOne.class));
    }

    public void nextActivity3(View view){
        startActivity(new Intent(this, MenuTwo.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
