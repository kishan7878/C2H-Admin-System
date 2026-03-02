package com.c2h.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.c2h.admin.adapters.DeviceAdapter;
import com.c2h.admin.api.ApiClient;
import com.c2h.admin.api.ApiService;
import com.c2h.admin.models.Device;
import com.c2h.admin.models.DevicesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DeviceAdapter.OnDeviceClickListener {

    private RecyclerView rvDevices;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    private TextView tvNoDevices;
    private DeviceAdapter deviceAdapter;
    private List<Device> deviceList;
    private Handler refreshHandler;
    private Runnable refreshRunnable;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getSharedPreferences(Config.PREFS_NAME, MODE_PRIVATE);
        
        initViews();
        setupRecyclerView();
        setupSwipeRefresh();
        setupAutoRefresh();
        
        loadDevices();
    }

    private void initViews() {
        rvDevices = findViewById(R.id.rvDevices);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        progressBar = findViewById(R.id.progressBar);
        tvNoDevices = findViewById(R.id.tvNoDevices);
    }

    private void setupRecyclerView() {
        deviceList = new ArrayList<>();
        deviceAdapter = new DeviceAdapter(this, deviceList, this);
        rvDevices.setLayoutManager(new LinearLayoutManager(this));
        rvDevices.setAdapter(deviceAdapter);
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(this::loadDevices);
        swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );
    }

    private void setupAutoRefresh() {
        refreshHandler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                loadDevices();
                refreshHandler.postDelayed(this, Config.REFRESH_INTERVAL);
            }
        };
    }

    private void loadDevices() {
        if (!swipeRefresh.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        
        apiService.getDevices().enqueue(new Callback<DevicesResponse>() {
            @Override
            public void onResponse(Call<DevicesResponse> call, Response<DevicesResponse> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    DevicesResponse devicesResponse = response.body();
                    
                    if (devicesResponse.isSuccess()) {
                        deviceList.clear();
                        deviceList.addAll(devicesResponse.getData());
                        deviceAdapter.notifyDataSetChanged();
                        
                        tvNoDevices.setVisibility(deviceList.isEmpty() ? View.VISIBLE : View.GONE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load devices", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DevicesResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Connection error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeviceClick(Device device) {
        Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra("device", device);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_refresh) {
            loadDevices();
            return true;
        } else if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        prefs.edit()
            .putBoolean(Config.KEY_IS_LOGGED_IN, false)
            .remove(Config.KEY_AUTH_TOKEN)
            .apply();
        
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHandler.post(refreshRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        refreshHandler.removeCallbacks(refreshRunnable);
    }
}
