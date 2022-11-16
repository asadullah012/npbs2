package com.galib.natorepbs2.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.Utility;
import com.galib.natorepbs2.sync.SyncService;
import com.galib.natorepbs2.ui.AboutUsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        findViewById(R.id.aboutUsBtn).setOnClickListener(v -> Utility.openActivity(this, AboutUsActivity.class));
        //findViewById(R.id.serviceBtn).setOnClickListener(v -> new SyncTariff(this).execute());
    }
}