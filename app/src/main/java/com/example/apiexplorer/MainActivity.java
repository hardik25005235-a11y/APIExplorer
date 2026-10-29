
package com.example.apiexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    private TextView tvStatus;
    private TextView tvResults;
    private TextView tvResultTitle;
    private ProgressBar progressBar;

    private final ExecutorService executor =
            Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        tvResults = findViewById(R.id.tvResults);
        tvResultTitle = findViewById(R.id.tvResultTitle);
        progressBar = findViewById(R.id.progressBar);

        Button btnUsers = findViewById(R.id.btnUsers);
        Button btnPosts = findViewById(R.id.btnPosts);
        Button btnClear = findViewById(R.id.btnClear);

        btnUsers.setOnClickListener(v -> loadUsers());
        btnPosts.setOnClickListener(v -> loadPosts());

        btnClear.setOnClickListener(v -> {
            tvResultTitle.setText("Results");
            tvResults.setText(
                    "Tap GET USERS or GET POSTS to load online data.");
            tvStatus.setText("Ready to connect");
        });
    }

    private void loadUsers() {
        fetchData(
                "https://jsonplaceholder.typicode.com/users",
                json -> {
                    JSONArray users = new JSONArray(json);
                    StringBuilder result = new StringBuilder();

                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);

                        result.append("USER ")
                                .append(user.optInt("id"))
                                .append("\n")
                                .append("Name: ")
                                .append(user.optString("name"))
                                .append("\nEmail: ")
                                .append(user.optString("email"))
                                .append("\nPhone: ")
                                .append(user.optString("phone"))
                                .append("\nCity: ")
                                .append(user.optJSONObject("address")
                                        .optString("city"))
                                .append("\nCompany: ")
                                .append(user.optJSONObject("company")
                                        .optString("name"))
                                .append("\n\n--------------------\n\n");
                    }

                    runOnUiThread(() -> {
                        tvResultTitle.setText("User Directory");
                        tvResults.setText(result.toString());
                    });
                });
    }

    private void loadPosts() {
        fetchData(
                "https://jsonplaceholder.typicode.com/posts",
                json -> {
                    JSONArray posts = new JSONArray(json);
                    StringBuilder result = new StringBuilder();

                    int count = Math.min(posts.length(), 15);

                    for (int i = 0; i < count; i++) {
                        JSONObject post = posts.getJSONObject(i);

                        result.append("POST #")
                                .append(post.optInt("id"))
                                .append("\n")
                                .append(post.optString("title"))
                                .append("\n\n")
                                .append(post.optString("body"))
                                .append("\n\n--------------------\n\n");
                    }

                    runOnUiThread(() -> {
                        tvResultTitle.setText("Latest Posts");
                        tvResults.setText(result.toString());
                    });
                });
    }

    private interface ApiCallback {
        void onSuccess(String json) throws Exception;
    }

    private void fetchData(String urlString, ApiCallback callback) {
        progressBar.setVisibility(View.VISIBLE);
        tvStatus.setText("Connecting to web service...");
        tvResults.setText("Loading data...");

        executor.execute(() -> {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection)
                        new URL(urlString).openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setRequestProperty(
                        "Accept", "application/json");

                int statusCode = connection.getResponseCode();

                InputStream stream = statusCode >= 200
                        && statusCode < 300
                        ? connection.getInputStream()
                        : connection.getErrorStream();

                if (stream == null) {
                    throw new Exception("No response from server");
                }

                StringBuilder response = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                stream, StandardCharsets.UTF_8))) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                if (statusCode < 200 || statusCode >= 300) {
                    throw new Exception("HTTP error: " + statusCode);
                }

                String json = response.toString();

                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    tvStatus.setText("Connected successfully");

                    try {
                        callback.onSuccess(json);
                    } catch (Exception e) {
                        showError("Unable to process JSON data.");
                    }
                });

            } catch (Exception e) {
                String message = e.getMessage() == null
                        ? "Check your internet connection."
                        : e.getMessage();

                runOnUiThread(() -> showError(message));

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        });
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        tvStatus.setText("Connection failed");
        tvResults.setText("Error: " + message);

        Toast.makeText(this, "API request failed",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}

