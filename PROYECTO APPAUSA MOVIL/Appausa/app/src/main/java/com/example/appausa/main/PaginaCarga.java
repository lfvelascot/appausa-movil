package com.example.appausa.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appausa.R;


public class PaginaCarga extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        Thread logo = new Thread(){
            public void run(){
                try{
                    int tiempo=0;
                    while(tiempo<5000){
                        sleep(100);
                        tiempo=tiempo+100;
                    }
                    Intent i=new Intent(PaginaCarga.this, Login.class);
                    startActivity(i);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    finish();
                }
            }
        };

        logo.start();

    }
}