package com.cookandroid.dietmanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodLogFragment extends Fragment {

    private TextView totalNutritionInfo;
    private FoodLogViewModel viewModel;
    private LinearLayout selectedFoodsContainer;
    private List<CsvReader.FoodItem> selectedFoodItems; // 선택된 음식 목록
    private List<CsvReader.FoodItem> foodItemList; // CSV로부터 로드된 음식 데이터 리스트

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_log, container, false);
        selectedFoodsContainer = view.findViewById(R.id.selected_foods_container);

        viewModel = new ViewModelProvider(requireActivity()).get(FoodLogViewModel.class);

        // 선택된 음식 목록 관찰
        viewModel.getSelectedFoodItems().observe(getViewLifecycleOwner(), new Observer<List<CsvReader.FoodItem>>() {
            @Override
            public void onChanged(List<CsvReader.FoodItem> foodItems) {
                updateFoodItems(foodItems);
            }
        });

        // 총 영양소 정보 관찰
        viewModel.getTotalNutritionInfo().observe(getViewLifecycleOwner(), new Observer<NutritionInfo>() {
            @Override
            public void onChanged(NutritionInfo nutritionInfo) {
                displayTotalNutritionInfo(nutritionInfo);
            }
        });

        if (selectedFoodItems == null) {
            selectedFoodItems = new ArrayList<>();
        } else {
            for (CsvReader.FoodItem foodItem : selectedFoodItems) {
                Log.d("FoodLogFragment", "Food item added[1]: " + foodItem.getName());
                addFoodItemView(foodItem);
            }
        }
        // CSV 데이터 로드
        CsvReader csvReader = new CsvReader();

        foodItemList = csvReader.readCsvFromAssets(getContext(), "foodCalorieInfo.csv"); // CSV 파일 이름을 넣으세요

        // 로그를 출력하여 데이터가 제대로 로드되었는지 확인
        Log.d("FoodLogFragment", "Loaded " + foodItemList.size() + " food items from CSV.");

        Button showFoodListButton = view.findViewById(R.id.show_food_list_button);
        showFoodListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodListFragment foodListFragment = new FoodListFragment();
                foodListFragment.setOnFoodItemClickListener(new FoodItemAdapter.OnFoodItemClickListener() {
                    @Override
                    public void onFoodItemClick(CsvReader.FoodItem foodItem) {
                        Log.d("FoodLogFragment", "Food item added[2]: " + foodItem.getName());
                        viewModel.addFoodItem(foodItem); // ViewModel을 통해 아이템 추가
                    }
                });

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
            String dietaryFiber = getArguments().getString("dietaryFiber");
            String sodium = getArguments().getString("sodium");

            // TextView에 음식 정보를 표시합니다.
            TextView foodInfoTextView = view.findViewById(R.id.food_info);
            String foodInfo = "음식명: " + foodName + "\n칼로리: " + calories + " kcal" +
                    ", 탄수화물: " + carbohydrates + " g" +
                    ", 단백질: " + protein + " g" +
                    ", 지방: " + fat + " g" +
                    ", 콜레스테롤: " + cholesterol + " g" +
                    ", 식이섬유: " + dietaryFiber + " g" +
                    ", 나트륨: " + sodium + " g";

            CsvReader.FoodItem selectedFoodItem = new CsvReader.FoodItem();
            selectedFoodItem.setName(foodName);
            selectedFoodItem.setCalories(calories);
            selectedFoodItem.setCarbohydrates(carbohydrates);
            selectedFoodItem.setProtein(protein);
            selectedFoodItem.setFat(fat);
            selectedFoodItem.setCholesterol(cholesterol);
            selectedFoodItem.setDietaryFiber(dietaryFiber);
            selectedFoodItem.setSodium(sodium);

            // 리스트에 음식 정보 추가
            addSelectedFoodItem(selectedFoodItem);

            foodInfoTextView.setText(foodInfo);
        }

        totalNutritionInfo = view.findViewById(R.id.total_nutrition_info);

        // Inflate the layout for this fragment
        return view;
    }

    // 총 영양소 정보를 화면에 표시하는 메소드
    private void displayTotalNutritionInfo(NutritionInfo nutritionInfo) {
        String nutritionText = "총 칼로리: " + nutritionInfo.getTotalCalories() + " kcal"
                + "\n총 탄수화물: " + nutritionInfo.getTotalCarbohydrates() + " g"
                + "\n총 단백질: " + nutritionInfo.getTotalProtein() + " g"
                + "\n총 지방: " + nutritionInfo.getTotalFat() + " g"
                + "\n총 콜레스테롤: " + nutritionInfo.getTotalCholesterol() + " g"
                + "\n총 식이섬유: " + nutritionInfo.getTotalDietaryFiber() + " g"
                + "\n총 나트륨: " + nutritionInfo.getTotalSodium() + " g";
        totalNutritionInfo.setText(nutritionText);
    }

    // 선택된 음식 목록을 화면에 업데이트하는 메소드
    private void updateFoodItems(List<CsvReader.FoodItem> foodItems) {
        selectedFoodsContainer.removeAllViews(); // 모든 뷰를 제거하여 목록을 새로 고칩니다.
        for (CsvReader.FoodItem foodItem : foodItems) {
            addFoodItemView(foodItem); // 새로운 아이템 뷰를 추가합니다.
        }
    }

    // 음식 아이템 추가 메소드 (예: 사용자가 음식을 선택했을 때 호출)
    public void addFoodItem(CsvReader.FoodItem foodItem) {
        Log.d("FoodLogFragment", "Food item added[3]: " + foodItem.getName());
        viewModel.addFoodItem(foodItem);
    }

    private void addFoodItemView(CsvReader.FoodItem foodItem) {
        Log.d("FoodLog", "addFoodItemView called with item: " + foodItem.getName());

        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView foodInfoView = new TextView(getContext());
        String foodInfoText = foodItem.getName() + ": " + foodItem.getCalories() + " kcal";
        foodInfoView.setText(foodInfoText);
        // Set the weight on the TextView so it takes up the extra space
        foodInfoView.setLayoutParams(new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));

        Button deleteButton = new Button(getContext());
        deleteButton.setText("X");
        // Set the button width to wrap_content to ensure it takes up only the space it needs
        deleteButton.setLayoutParams(new LinearLayout.LayoutParams(
                120, LinearLayout.LayoutParams.WRAP_CONTENT));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeFoodItem(foodItem);
                selectedFoodsContainer.removeView(itemLayout);
            }
        });

        itemLayout.addView(foodInfoView);
        itemLayout.addView(deleteButton);

        Log.d("FoodLog", "Item layout child count: " + itemLayout.getChildCount());
        selectedFoodsContainer.addView(itemLayout);
    }

    // 선택된 음식 아이템을 추가하는 메소드
    void addSelectedFoodItem(CsvReader.FoodItem foodItem) {
        if (foodItem != null) {
            Log.d("FoodLogFragment", "Food item added[4]: " + foodItem.getName());
            viewModel.addFoodItem(foodItem);
        }
    }
}

