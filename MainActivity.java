package com.example.sony.sts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import android.os.Build;
import android.os.Bundle;

import android.service.carrier.CarrierMessagingService;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.drive.query.internal.InFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.plus.model.people.Person;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {
   /* SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText name;
    EditText num;
    Button submit;*/
    ImageButton img;
    ImageButton call;
    ImageButton location;
    Camera camera=null;
    Camera.Parameters parameters=null;
    boolean isFlashOn=false;
    boolean hasFlash;
    ImageButton get_place;
    int PLACE_PICKER_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageButton) findViewById(R.id.imageButton);
        for (int i = 0; i < 4; i++) {
            Toast.makeText(getApplicationContext(), "Welcome to Save The Society App.\nYour safety is our priority.\nThere are five safety features in the app.\n1.Emergency dialer\n2.Current Location Detector.\n3.Android Place Picker\n4.Siren\n5.Flashlight", Toast.LENGTH_LONG).show();
        }
        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(!hasFlash)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Error...");
            builder.setMessage("Flashlight is not available on this device");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            return;
        }

        getCamera();
        toggleButtonImage();

        img.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (isFlashOn) {
                    turnOffFlash();
                } else {
                    turnOnFlash();
                }
            }
        });

        call = (ImageButton) findViewById(R.id.call);
        location = (ImageButton) findViewById(R.id.location);
        get_place = (ImageButton) findViewById(R.id.textView);

        get_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(getApplicationContext());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
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
       private void getCamera()
       {
           if(camera==null)
           {
               try{
                   camera = Camera.open();
                   parameters = camera.getParameters();
               }
               catch (RuntimeException e)
               {
                   Log.e("Camera error.",e.getMessage());
               }
           }
       }
    private void toggleButtonImage()
    {
     try{
         if(isFlashOn)
         {
             img.setImageResource(R.drawable.on);
         }
         else
         {
             img.setImageResource(R.drawable.off);
         }
     }
     catch (RuntimeException e)
     {
         Log.e("Could not toggle image",e.getMessage());
     }
    }
    private void turnOnFlash() {
        try {
            if (!isFlashOn) {
                if (camera == null || parameters == null)
                    return;
                parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(parameters);
                camera.startPreview();
                isFlashOn = true;
                toggleButtonImage();
            }
        } catch (RuntimeException e) {
            Log.e("Could not turn on flash", e.getMessage());
        }
    }
    private void turnOffFlash() {
        try {
            if (isFlashOn) {
                if (camera == null || parameters == null)
                    return;

                parameters = camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                isFlashOn = false;
                toggleButtonImage();
            }
        } catch (RuntimeException e) {
            Log.e("Cant turn off flash", e.getMessage());
        }
    }

    @Override
    protected  void onDestroy()
    {
        super.onDestroy();
    }
    @Override
    protected  void onPause()
    {
        super.onPause();
        turnOffFlash();
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
    }
   @Override
    protected void onStart()
    {
        super.onStart();
        getCamera();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
           if(requestCode==PLACE_PICKER_REQUEST)
           {
               if(resultCode==RESULT_OK)
               {
                   Place place=PlacePicker.getPlace(data,this);
                   String address=String.format("Place:%s",place.getAddress());
                   //get_place.setText(address);
               }
           }
    }
    @Override
    public void  onBackPressed()
    {
        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();}}).setNegativeButton("No",null).show();
     }

    }

