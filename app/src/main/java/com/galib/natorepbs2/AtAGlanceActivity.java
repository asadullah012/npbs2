package com.galib.natorepbs2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.galib.natorepbs2.constants.Category;
import com.galib.natorepbs2.sync.SyncAtAGlance;

public class AtAGlanceActivity extends AppCompatActivity {
    private InformationViewModel informationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_aglance);
        informationViewModel = new ViewModelProvider(this).get(InformationViewModel.class);
        startSync(informationViewModel);
        RecyclerView recyclerView = findViewById(R.id.ataglanceRecyclerView);
        final AtAGlanceAdapter adapter = new AtAGlanceAdapter(new AtAGlanceAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        informationViewModel.getInformationByCategory(Category.atAGlance).observe(this, information -> {
            adapter.submitList(information);
        });

    }
    protected void startSync(InformationViewModel informationViewModel){
        new SyncAtAGlance(informationViewModel).execute();
    }
}