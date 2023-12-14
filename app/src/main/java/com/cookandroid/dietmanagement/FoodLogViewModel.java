package com.cookandroid.dietmanagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodLogViewModel extends ViewModel {
    private MutableLiveData<List<CsvReader.FoodItem>> selectedFoodItems;

    public LiveData<List<CsvReader.FoodItem>> getSelectedFoodItems() {
        if (selectedFoodItems == null) {
            selectedFoodItems = new MutableLiveData<>();
            selectedFoodItems.setValue(new ArrayList<>());
        }
        return selectedFoodItems;
    }

    public void addFoodItem(CsvReader.FoodItem foodItem) {
        if (selectedFoodItems.getValue() != null) {
            List<CsvReader.FoodItem> items = selectedFoodItems.getValue();
            items.add(foodItem);
            selectedFoodItems.setValue(items);
        }
    }
}
