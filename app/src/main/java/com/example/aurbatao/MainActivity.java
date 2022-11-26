package com.example.aurbatao;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUser!=null){
                    startActivity(new Intent(MainActivity.this,DashboardActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }

            }
        },2000);
    }
}