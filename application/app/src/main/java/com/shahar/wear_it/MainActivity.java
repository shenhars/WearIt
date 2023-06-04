package com.shahar.wear_it;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//package com.example.dell.androidflask;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button suitMeButton;
    private Button specialEventsButton;
    private Button addClothButton;
    private final int REQUEST_IMAGE_CAPTURE = 1;

    private ActivityResultLauncher<Intent> takePictureContract = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 99, out);
                        byte[] byteArray = out.toByteArray();
                        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        JSONObject json = new JSONObject();
                        try {
                            json.put("image", encoded);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        RequestBody body = RequestBody.create(
                                json.toString(),MediaType.parse("application/json; charset=utf-8")
                        );
//                        RequestBody body = RequestBody.create(("{'image':'" + encoded + "'}"), MediaType.parse("application/json; charset=utf-8"));
                        String postUrl = "http://172.27.171.142:5000/parse";

                        postRequest(postUrl, body);
                        // Save the image or perform further processing
                        // ...
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to capture image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    void connectServer() {
        String postUrl = "http://172.27.171.142:5000/";

        String postBodyText = "Hello";
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(postBodyText, mediaType);

        postRequest(postUrl, postBody);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        suitMeButton = findViewById(R.id.openButton);
        specialEventsButton = findViewById(R.id.specialEventsButton);
        addClothButton = findViewById(R.id.addClothButton);

        suitMeButton.setOnClickListener(new View.OnClickListener() {
            // Logic for the "SuitMe" button
            // This will be executed when the button is clicked
            // Add your implementation here
            @Override
            public void onClick(View v) {
                opensuitme_activity();
            }
        });

        specialEventsButton.setOnClickListener(new View.OnClickListener() {
            // Logic for the "Special Events" button
            // This will be executed when the button is clicked
            // Add your implementation here
            @Override
            public void onClick(View v) {
                openspecialevents_activity();
            }
        });


        addClothButton.setOnClickListener(v -> {
            // Logic for the "Add a Cloth" button
            // This will be executed when the button is clicked

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                takePictureContract.launch(takePictureIntent);
            } else {
                Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void openwardrobe_activity() {
//        Intent intent = new Intent(this, wardrobe_activity.class);
//        startActivity(intent);
//    }

    public void opensuitme_activity(){
        Intent intent = new Intent(this, suitme_sctivity.class);
        startActivity(intent);
    }

    public void openspecialevents_activity(){
        Intent intent = new Intent(this, specialevents_activity.class);
        startActivity(intent);
    }

    void postRequest(String postUrl, RequestBody postBody) {
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .post(postBody)
                    .url(postUrl)
                    .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, final IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Failed");
                                call.cancel();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println("connected");
                                    System.out.flush();
//                                    Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG);
                                    Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                                } catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
//                        String responseBody = response.body().string();
//                        System.out.println("Response: " + responseBody);
                    }
                });
            }

        }
    }

