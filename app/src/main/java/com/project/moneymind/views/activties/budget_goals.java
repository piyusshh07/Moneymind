package com.project.moneymind.views.activties;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.moneymind.R;
import com.project.moneymind.adapters.ViewPager_adpater_budGoal;

public class budget_goals extends AppCompatActivity {
 TabLayout tab;
 ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget_goals);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            viewPager= findViewById(R.id.viewpager);
            tab=findViewById(R.id.tabLayout);

            ViewPager_adpater_budGoal adapter=new ViewPager_adpater_budGoal(getSupportFragmentManager());

            viewPager.setAdapter(adapter);
            tab.setupWithViewPager(viewPager);
            return insets;

        });
    }
}