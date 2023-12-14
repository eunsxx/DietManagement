package com.cookandroid.dietmanagement;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<FoodItem> readCsvFromAssets(Context context, String fileName) {
        List<FoodItem> foodItems = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try (InputStream is = assetManager.open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            // 첫 줄은 헤더이므로 건너뜁니다. (필요한 경우)
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                // CSV는 쉼표로 구분되어 있으며, 쉼표가 포함된 데이터는 큰따옴표로 묶일 수 있습니다.
                // 간단한 파싱을 위해 쉼표로 분할합니다. 데이터에 따라 파싱 로직이 변경될 수 있습니다.
                String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                // 등록일이 누락된 경우를 처리합니다.
                String registrationDate = tokens.length > 8 ? tokens[8] : "No Date Provided";

                // FoodItem 객체를 생성합니다.
                // tokens 배열에서 필요한 데이터를 추출하여 FoodItem의 생성자에 전달합니다.
                FoodItem foodItem = new FoodItem(tokens[0], // 음식명
                        tokens[1], // 1인분칼로리(kcal)
                        tokens[2], // 탄수화물(g)
                        tokens[3], // 단백질(g)
                        tokens[4], // 지방(g)
                        tokens[5], // 콜레스트롤(g)
                        tokens[6], // 식이섬유(g)
                        tokens[7], // 나트륨(g)
                        registrationDate); // 등록일
                foodItems.add(foodItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("CsvReader", "Loaded " + foodItems.size() + " food items.");

        return foodItems;
    }

    public static class FoodItem {
        private String name;
        private String calories;
        private String carbohydrates;
        private String protein;
        private String fat;
        private String cholesterol;
        private String dietaryFiber;
        private String sodium;
        private String registrationDate;

        public FoodItem(String name, String calories, String carbohydrates, String protein,
                        String fat, String cholesterol, String dietaryFiber, String sodium,
                        String registrationDate) {
            this.name = name;
            this.calories = calories;
            this.carbohydrates = carbohydrates;
            this.protein = protein;
            this.fat = fat;
            this.cholesterol = cholesterol;
            this.dietaryFiber = dietaryFiber;
            this.sodium = sodium;
            this.registrationDate = registrationDate;
        }

        public FoodItem() {

        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getCalories() {
            return calories;
        }
        public void setCalories(String calories) {
            this.calories = calories;
        }
        public String getCarbohydrates() {
            return carbohydrates;
        }
        public void setCarbohydrates(String carbohydrates) {
            this.carbohydrates = carbohydrates;
        }

        public String getProtein() {
            return protein;
        }

        public void setProtein(String protein) {
            this.protein = protein;
        }

        public String getFat() {
            return fat;
        }
        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getCholesterol() {
            return cholesterol;
        }
        public void setCholesterol(String cholesterol) {
            this.cholesterol = cholesterol;
        }

        public String getDietaryFiber() {
            return dietaryFiber;
        }
        public void setDietaryFiber(String dietaryFiber) {
            this.dietaryFiber = dietaryFiber;
        }
        public String getSodium() {
            return sodium;
        }
        public void setSodium(String sodium) {
            this.sodium = sodium;
        }
        public String getRegistrationDate() {
            return registrationDate;
        }
        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }
    }
}
