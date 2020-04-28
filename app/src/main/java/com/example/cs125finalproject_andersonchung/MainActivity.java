package com.example.cs125finalproject_andersonchung;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private String currentBreed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //handle the Breed Change Button/RadioGroup list
        Button changeBreed = findViewById(R.id.changeBreed);
        changeBreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("changeBreed", "Change Breed Clicked");
            }
        });

        //handle the next picture
        Button next = findViewById(R.id.nextDog);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("nextDog", "Next picture requested");
                updatePicture(currentBreed);
            }
        });

        //handle downloading to phone
        Button download = findViewById(R.id.saveDog);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("saveDog", "User Tried Downloading");
            }
        });
    }

    protected void updatePicture(String currentBreed) {
        // handle the incoming image from DogApi
        // if random use basic WebAPI
        // else use breed in api
        ImageView picture = findViewById(R.id.dogView);
    }
}
