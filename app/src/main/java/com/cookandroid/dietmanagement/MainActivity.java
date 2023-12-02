package com.cookandroid.dietmanagement;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cookandroid.dietmanagement.DashboardFragment;
import com.cookandroid.dietmanagement.FoodLogFragment;
import com.cookandroid.dietmanagement.ProfileFragment;
import com.cookandroid.dietmanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // 앱 시작 시 초기 화면으로 첫 번째 Fragment를 설정합니다.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new DashboardFragment()).commit();
        }
    }

    // BottomNavigationView의 아이템 선택 이벤트 리스너
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    int id = item.getItemId();

                    if (id == R.id.navigation_dashboard) {
                        selectedFragment = new DashboardFragment();
                    } else if (id == R.id.navigation_food_log) {
                        selectedFragment = new FoodLogFragment();
                    } else if (id == R.id.navigation_goal) {
                        selectedFragment = new GoalFragment();
                    } else if (id == R.id.navigation_profile) {
                        selectedFragment = new ProfileFragment();
                    }

                    // 선택된 Fragment로 전환
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
