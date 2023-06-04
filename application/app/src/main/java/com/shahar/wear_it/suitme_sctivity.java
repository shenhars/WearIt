package com.shahar.wear_it;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class suitme_sctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitme_sctivity);
        getResponse();
    }

//...


    void getResponse() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://172.27.171.142:5000/generate")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    suitme_sctivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                TextView resultText = findViewById(R.id.userInputEditText);
                                JSONObject jsonObject = new JSONObject(myResponse);

                                // Get the values from the JSON object.
                                String shoes = jsonObject.getString("shoes");
                                String pants = jsonObject.getString("pants");
                                String shirts = jsonObject.getString("shirts");
                                String indexes = jsonObject.getString("indexes");

                                // Combine the values into a single string.
                                String result = shoes + "\n" + pants + "\n" + shirts + "\n" + indexes;

                                // Update your TextView (replace textView with your actual TextView).
                                resultText.setText(result);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
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
                            System.out.println("connected");
                            System.out.flush();
//                                    Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG);
                            TextView resultText = findViewById(R.id.userInputEditText);

                            resultText.setText(response.toString());
                        }
                    });
//                        String responseBody = response.body().string();
//                        System.out.println("Response: " + responseBody);
                }
            });
        }


    }
}