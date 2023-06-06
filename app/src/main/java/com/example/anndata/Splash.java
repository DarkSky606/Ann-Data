package com.example.anndata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.SplashScreen;

public class Splash extends AppCompatActivity {
    private static int SPLASH_SCREEN=2000;

    SharedPreferences sharedPreferences;

    private  static  final String SHARED_PREF_NAME="myref";
    private  static  final String KEY_NAME="name";
    TextView Name,Slogan;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        Name=findViewById(R.id.name);
        Slogan=findViewById(R.id.slogan);
        logo=findViewById(R.id.logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                String name = sharedPreferences.getString(KEY_NAME,null);
                if (name!=null)
                {
                    Intent intent = new Intent(Splash.this,homepage.class);
                    startActivity(intent);
                    finish();
                }else
                {
                Intent intent = new Intent(Splash.this,login.class);
                startActivity(intent);
                finish();
                }
            }
        },SPLASH_SCREEN);
    }
}