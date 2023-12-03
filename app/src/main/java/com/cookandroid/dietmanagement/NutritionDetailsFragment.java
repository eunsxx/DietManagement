package com.cookandroid.dietmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NutritionDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition_details, container, false);

        // 전달받은 선택된 음식 정보를 사용하여 UI 업데이트
        Bundle bundle = getArguments();
        if (bundle != null) {
            String selectedFoodInfo = bundle.getString("selectedFood");
            TextView foodInfoTextView = view.findViewById(R.id.nutrition_details);

            if (foodInfoTextView != null) {
                foodInfoTextView.setText(selectedFoodInfo);
            } else {
                Log.e("NutritionDetailsFragment", "TextView not found in the layout");
            }
        }

        return view;
    }
}


