package com.shixm.commonlib.launcherbadgeutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        final EditText edt1 = (EditText) findViewById(R.id.edit1);
        final EditText edt2 = (EditText) findViewById(R.id.edit2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int current = Integer.parseInt(edt1.getText().toString());
                    int total = Integer.parseInt(edt2.getText().toString());
                    Util.showNotification(MainActivity.this, 100, current, total);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "e:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int current = Integer.parseInt(edt1.getText().toString());
                    int total = Integer.parseInt(edt2.getText().toString());
                    Util.showNotification(MainActivity.this, (int) (System.currentTimeMillis()/1000), current, total);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "e:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
