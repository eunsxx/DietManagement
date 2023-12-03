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

        // 로그를 출력하여 데이터가 제대로 로드되었는지 확인
        Log.d("FoodLogFragment", "Loaded " + foodItemList.size() + " food items from CSV.");

        Button showFoodListButton = view.findViewById(R.id.show_food_list_button);
        showFoodListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FoodListFragment로 화면 전환
                FoodListFragment foodListFragment = new FoodListFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, foodListFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        if (getArguments() != null) {
            String foodName = getArguments().getString("foodName");
            String calories = getArguments().getString("calories");
            String carbohydrates = getArguments().getString("carbohydrates");
            String protein = getArguments().getString("protein");
            String fat = getArguments().getString("fat");
            String cholesterol = getArguments().getString("cholesterol");
            String dietaryFier = getArguments().getString("dietaryFiber");
            String sodium = getArguments().getString("sodium");

            // TextView에 음식 정보를 표시합니다.
            TextView foodInfoTextView = view.findViewById(R.id.food_info);
            String foodInfo = "음식명: " + foodName + "\n칼로리: " + calories + " kcal" +
                    ", 탄수화물: " + carbohydrates + " g" +
                    ", 단백질: " + protein + " g" +
                    ", 지방: " + fat + " g" +
                    ", 콜레스테롤: " + cholesterol + " g" +
                    ", 식이섬유: " + dietaryFier + " g" +
                    ", 나트륨: " + sodium + " g";

            foodInfoTextView.setText(foodInfo);
        }

        // Inflate the layout for this fragment
        return view;
    }
}



