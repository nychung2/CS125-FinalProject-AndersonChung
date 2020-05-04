package com.example.cs125finalproject_andersonchung;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private String currentBreed;
    private String imageURL;
    public static final String TAG = "Tag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentBreed = "random";
        requestQueue = Volley.newRequestQueue(this);
        updatePicture();

        //handle the Breed Change Button/RadioGroup list
        Button changeBreed = findViewById(R.id.changeBreed);
        changeBreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("changeBreed", "Change Breed Clicked");
                // currentBreed = set the current breed to what the user requested
                showBreedList();
            }
        });

        //handle the next picture
        Button next = findViewById(R.id.nextDog);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("nextDog", "Next picture requested");
                updatePicture();
            }
        });

        //handle downloading to phone
        Button download = findViewById(R.id.saveDog);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("saveDog", "User Tried Downloading");
                String useHere = imageURL;
            }
        });
    }

    protected void updatePicture() {
        // handle the incoming image from DogApi
        // if random use basic WebAPI
        // else use breed in api

        Log.i("method", "method updatePicture has been called");
        final ImageView picture = findViewById(R.id.dogView);
        String url = "";

        switch(currentBreed) {
            case "random":
                url = "https://dog.ceo/api/breeds/image/random";
                break;
            default:
                url = "https://dog.ceo/api/breed/" + currentBreed + "/images/random";
        }
        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // parse the response;
                        Log.i("request", "URL is valid");
                        JsonElement element = JsonParser.parseString(response);
                        JsonObject object = element.getAsJsonObject();
                        Picasso.get().load(object.get("message").getAsString()).into(picture);
                        imageURL = object.get("message").getAsString();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // ERROR: Not a valid breed
                Log.i("request", "URL is invalid");
            }
        });
        requestQueue.add(request);
    }

    protected void showBreedList() {
        //Gives a Alert Dialog of the Available Breeds

        Log.i("method", "method showBreedList has been called");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Your Breed").setItems(R.array.breeds_array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] choices = getResources().getStringArray(R.array.breeds_array);
                currentBreed = choices[which];
                updatePicture();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}
