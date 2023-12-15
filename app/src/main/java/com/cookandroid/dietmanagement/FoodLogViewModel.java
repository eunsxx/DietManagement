package com.cookandroid.dietmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodLogViewModel extends ViewModel {
    private MutableLiveData<List<CsvReader.FoodItem>> selectedFoodItems;
    private MutableLiveData<NutritionInfo> totalNutritionInfo;
    public FoodLogViewModel() {
        selectedFoodItems = new MutableLiveData<>(new ArrayList<>());
        totalNutritionInfo = new MutableLiveData<>(new NutritionInfo());

    }

    public LiveData<List<CsvReader.FoodItem>> getSelectedFoodItems() {
        if (selectedFoodItems == null) {
            selectedFoodItems = new MutableLiveData<>();
            selectedFoodItems.setValue(new ArrayList<>());
        }
        return selectedFoodItems;
    }

    public LiveData<NutritionInfo> getTotalNutritionInfo() {
        return totalNutritionInfo;
    }

    public void updateTotalNutritionInfo() {
        List<CsvReader.FoodItem> items = selectedFoodItems.getValue();
        if (items != null) {
            NutritionInfo info = new NutritionInfo();
            for (CsvReader.FoodItem item : items) {
                info.addCalories(Float.parseFloat(item.getCalories()));
                info.addCarbohydrates(Float.parseFloat(item.getCarbohydrates()));
                info.addProtein(Float.parseFloat(item.getProtein()));
                info.addFat(Float.parseFloat(item.getFat()));
                info.addCholesterol(Float.parseFloat(item.getCholesterol()));
                info.addDietaryFiber(Float.parseFloat(item.getDietaryFiber()));
                info.addSodium(Float.parseFloat(item.getSodium()));

            }
            totalNutritionInfo.setValue(info);
        }
    }

    public void addFoodItem(CsvReader.FoodItem foodItem) {
        List<CsvReader.FoodItem> currentItems = selectedFoodItems.getValue();
        if (currentItems == null) {
            currentItems = new ArrayList<>();
        } else if (currentItems != null) {
            currentItems.add(foodItem);
            selectedFoodItems.setValue(currentItems);
            updateTotalNutritionInfo(); // 총 영양소 정보 업데이트
        }
    }

    public void removeFoodItem(CsvReader.FoodItem foodItem) {
        List<CsvReader.FoodItem> items = selectedFoodItems.getValue();
        if (items != null && items.remove(foodItem)) {
            selectedFoodItems.setValue(items);
            updateTotalNutritionInfo(); // 총 영양소 정보 업데이트
        }
    }
}

class NutritionInfo {
    private float totalCalories;
    private float totalCarbohydrates;
    private float totalProtein;
    private float totalFat;
    private float totalCholesterol;
    private float totalDietaryFiber;
    private float totalSodium;

    public NutritionInfo() {
        totalCalories = 0;
        totalCarbohydrates =0;
        totalProtein = 0;
        totalFat = 0;
        totalCholesterol = 0;
        totalDietaryFiber = 0;
        totalSodium = 0;
    }

    public void addCalories(float calories) {
        totalCalories += calories;
    }

    public void addCarbohydrates(float carbs) {
        totalCarbohydrates += carbs;
    }

    public void addProtein(float protein) {
        totalProtein += protein;
    }

    public void addFat(float fat) {
        totalFat += fat;
    }

    public void addCholesterol(float cholesterol) {
        totalCholesterol += cholesterol;
    }

    public void addDietaryFiber(float dietaryFiber) {
        totalDietaryFiber += dietaryFiber;
    }

    public void addSodium(float sodium) {
        totalSodium += sodium;
    }

    // Getters
    public float getTotalCalories() {
        return totalCalories;
    }

    public float getTotalCarbohydrates() {
        return totalCarbohydrates;
    }

    public float getTotalProtein() {
        return totalProtein;
    }

    public float getTotalFat() {
        return totalFat;
    }

    public float getTotalCholesterol() {
        return totalCholesterol;
    }

    public float getTotalDietaryFiber() {
        return totalDietaryFiber;
    }

    public float getTotalSodium() {
        return totalSodium;
    }

}