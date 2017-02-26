package com.example.sony.sts;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
   /* SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText name;
    EditText num;
    Button submit;*/
    ImageButton img;
    ImageButton call;
    ImageButton location;
    Camera camera;
    Camera.Parameters parameters;
    boolean isflash = false;
    boolean ison = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageButton) findViewById(R.id.imageButton);
        call = (ImageButton) findViewById(R.id.call);
       location = (ImageButton) findViewById(R.id.location) ;

// intent call
       call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             Intent i = new Intent(MainActivity.this,call.class);
              startActivity(i);
            }
        });
// intent location
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this,location.class);
                Log.d("intent","intent created") ;
                startActivity(j);
            }
        });

        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            camera = Camera.open();
            parameters = camera.getParameters();
            isflash = true;
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (isflash) {
                    if (!ison) {
                        img.setImageResource(R.drawable.on);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        ison = true;
                    } else {
                        img.setImageResource(R.drawable.off);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        ison = false;
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error...");
                    builder.setMessage("Flash light is not available on this device");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

        final MediaPlayer SirenMP = MediaPlayer.create(this, R.raw.song);
        ImageButton siren = (ImageButton) this.findViewById(R.id.siren);

        siren.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (SirenMP.isPlaying()) {
                    SirenMP.pause();
                } else {
                    SirenMP.start();
                }
            }
        });
    }

        public void onStop()
        {
            super.onStop();
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }
    }
