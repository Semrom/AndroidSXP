package com.sxp.androidsxp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aPropos = (Button) findViewById(R.id.activity_main_aboutButton);
        aPropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent aboutActivity = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutActivity);
            }
        });

        Button commencer = (Button) findViewById(R.id.activity_main_startButton);
        commencer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
             Intent webViewActivity = new Intent(MainActivity.this, WebViewActivity.class);
             startActivity(webViewActivity);
                 //process test
             }
         }

        );
    }
}
