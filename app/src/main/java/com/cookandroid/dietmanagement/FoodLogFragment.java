package com.cookandroid.dietmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodLogFragment extends Fragment {

    private List<CsvReader.FoodItem> foodItemList; // CSV로부터 로드된 음식 데이터 리스트

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_log, container, false);

        // CSV 데이터 로드
        CsvReader csvReader = new CsvReader();
        foodItemList = csvReader.readCsvFromAssets(getContext(), "foodCalorieInfo.csv"); // CSV 파일 이름을 넣으세요

        EditText foodSearchEditText = view.findViewById(R.id.food_search);
        TextView foodInfoTextView = view.findViewById(R.id.food_info);
        Button addFoodButton = view.findViewById(R.id.add_food_button);
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = foodSearchEditText.getText().toString().trim();
                Log.d("Check", searchQuery);
                for (CsvReader.FoodItem item : foodItemList) {
                    if (item.getName().equalsIgnoreCase(searchQuery)) {
                        Log.d("Check", "찾음");
                        // UI에 음식 정보 표시
                        String foodInfo = "Name: " + item.getName() + ", Calories: " + item.getCalories();
                        Log.d("Check", foodInfo);
                        foodInfoTextView.setText(foodInfo);
                        break; // 일치하는 첫 번째 항목을 찾으면 반복 종료
                    }
                }
                Log.d("Check", "찾을 수 없음");
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}



