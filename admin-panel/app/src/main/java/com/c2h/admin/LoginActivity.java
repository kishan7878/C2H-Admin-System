package com.c2h.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.c2h.admin.api.ApiClient;
import com.c2h.admin.api.ApiService;
import com.c2h.admin.models.LoginRequest;
import com.c2h.admin.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPassword;
    private Button btnLogin;
    private TextView tvChangePassword;
    private ProgressBar progressBar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        prefs = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);
        
        // Check if already logged in
        if (prefs.getBoolean(Config.KEY_IS_LOGGED_IN, false)) {
            navigateToMain();
            return;
        }
        
        setContentView(R.layout.activity_login);
        
        initViews();
        setupListeners();
    }

    private void initViews() {
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvChangePassword = findViewById(R.id.tvChangePassword);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());
        
        tvChangePassword.setOnClickListener(v -> {
            // TODO: Implement change password dialog
            Toast.makeText(this, "Change password feature coming soon", Toast.LENGTH_SHORT).show();
        });
    }

    private void attemptLogin() {
        String password = etPassword.getText().toString().trim();
        
        if (password.isEmpty()) {
            etPassword.setError("Password required");
            return;
        }
        
        setLoading(true);
        
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        LoginRequest request = new LoginRequest(password);
        
        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                setLoading(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    
                    if (loginResponse.isSuccess()) {
                        // Save login state
                        prefs.edit()
                            .putBoolean(Config.KEY_IS_LOGGED_IN, true)
                            .putString(Config.KEY_AUTH_TOKEN, loginResponse.getToken())
                            .apply();
                        
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        navigateToMain();
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!loading);
        etPassword.setEnabled(!loading);
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
