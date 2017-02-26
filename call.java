package com.example.sony.sts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class call extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText name1,name2,name3,name4;
    EditText num1,num2,num3,num4;
    Button submit1,submit2,submit3,submit4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        name1 = (EditText) findViewById(R.id.name1);
        submit1 = (Button) findViewById(R.id.submit1);
        num1 =(EditText) findViewById(R.id.num1);
        name2 = (EditText) findViewById(R.id.name2);
        submit2 = (Button) findViewById(R.id.submit2);
        num2 =(EditText) findViewById(R.id.num2);
        name3 = (EditText) findViewById(R.id.name3);
        submit3 = (Button) findViewById(R.id.submit3);
        num3 =(EditText) findViewById(R.id.num3);
        name4 = (EditText) findViewById(R.id.name4);
        submit4 = (Button) findViewById(R.id.submit4);
        num4 =(EditText) findViewById(R.id.num4);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("My Pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String str1 = sharedPreferences.getString("name1", "");
        name1.setText(str1);
        String a1 = sharedPreferences.getString("num1","");
        num1.setText(a1);

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = name1.getText().toString();
                String a1 = num1.getText().toString();
                editor.putString("name1", str1);
                editor.apply();
                editor.putString("num1", a1);
                editor.apply();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+a1));

                startActivity(callIntent);

            }
        });
        String str2 = sharedPreferences.getString("name2", "");
        name2.setText(str2);
        String a2 = sharedPreferences.getString("num2","");
        num2.setText(a2);
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str2 = name2.getText().toString();
                String a2 = num2.getText().toString();
                editor.putString("name2", str2);
                editor.apply();
                editor.putString("num2", a2);
                editor.apply();
                Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                callIntent2.setData(Uri.parse("tel:"+a2));

                startActivity(callIntent2);

            }
        });
        String str3 = sharedPreferences.getString("name3", "");
        name3.setText(str3);
        String a3 = sharedPreferences.getString("num3","");
        num3.setText(a3);
        submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str3 = name3.getText().toString();
                String a3 = num3.getText().toString();
                editor.putString("name3", str3);
                editor.apply();
                editor.putString("num3", a3);
                editor.apply();
                Intent callIntent3 = new Intent(Intent.ACTION_CALL);
                callIntent3.setData(Uri.parse("tel:"+a3));

                startActivity(callIntent3);

            }
        });
        String str4 = sharedPreferences.getString("name4", "");
        name4.setText(str4);
        String a4 = sharedPreferences.getString("num4","");
        num4.setText(a4);
        submit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str4 = name4.getText().toString();
                String a4 = num4.getText().toString();
                editor.putString("name4", str4);
                editor.apply();
                editor.putString("num4", a4);
                editor.apply();
                Intent callIntent4 = new Intent(Intent.ACTION_CALL);
                callIntent4.setData(Uri.parse("tel:"+a4));

                startActivity(callIntent4);

            }
        });
    }
}
