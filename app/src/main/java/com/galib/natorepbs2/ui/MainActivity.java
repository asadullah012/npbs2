package com.galib.natorepbs2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.galib.natorepbs2.R;
import com.galib.natorepbs2.sync.SyncAchievement;
import com.galib.natorepbs2.sync.SyncAtAGlance;
import com.galib.natorepbs2.sync.SyncComplainCentre;
import com.galib.natorepbs2.sync.SyncJuniorOfficers;
import com.galib.natorepbs2.sync.SyncOfficerList;
import com.galib.natorepbs2.viewmodel.AchievementViewModel;
import com.galib.natorepbs2.viewmodel.ComplainCentreViewModel;
import com.galib.natorepbs2.viewmodel.EmployeeViewModel;
import com.galib.natorepbs2.viewmodel.InformationViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sync();
    }

    private void sync() {
        new SyncAtAGlance(new ViewModelProvider(this).get(InformationViewModel.class)).execute();
        new SyncAchievement(new ViewModelProvider(this).get(AchievementViewModel.class)).execute();
        new SyncComplainCentre(new ViewModelProvider(this).get(ComplainCentreViewModel.class)).execute();
        new SyncOfficerList(new ViewModelProvider(this).get(EmployeeViewModel.class)).execute();
        new SyncJuniorOfficers(new ViewModelProvider(this).get(EmployeeViewModel.class)).execute();
    }
}