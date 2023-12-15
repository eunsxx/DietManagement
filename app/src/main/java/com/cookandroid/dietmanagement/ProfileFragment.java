package com.cookandroid.dietmanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText weightInput, heightInput;
    private TextView bmiResultText, bmiCategoryText;
    private Button saveSettingsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        weightInput = view.findViewById(R.id.weight_input);
        heightInput = view.findViewById(R.id.height_input);
        bmiResultText = view.findViewById(R.id.bmi_result_text); // 이 라인을 추가
        bmiCategoryText = view.findViewById(R.id.bmi_category_text); // 이 라인을 추가
        saveSettingsButton = view.findViewById(R.id.save_settings_button);

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndDisplayBMI();
            }
        });

        return view;

    }
    private void calculateAndDisplayBMI() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100; // cm to m
            float bmi = weight / (height * height);
            displayBMIResult(bmi);
            displayBMICategory(bmi);
        }
    }

    private void displayBMIResult(float bmi) {
        String bmiResult = "Your BMI: " + String.format("%.2f", bmi);
        Toast.makeText(getActivity(), bmiResult, Toast.LENGTH_LONG).show();
        bmiResultText.setBackgroundColor(Color.parseColor("#495057"));
        bmiResultText.setText(bmiResult);
        bmiResultText.setTextColor(Color.WHITE);

    }

    private void displayBMICategory(float bmi) {
        String category;
        int textColor;

        if (bmi < 18.5) {
            category = "저체중";
            textColor = Color.BLUE;
        } else if (bmi < 25) {
            category = "정상";
            textColor = Color.GREEN;
        } else if (bmi < 30) {
            category = "과체중";
            textColor = Color.YELLOW;
        } else {
            category = "비만";
            textColor = Color.RED;
        }

        bmiCategoryText.setBackgroundColor(Color.parseColor("#495057"));
        bmiCategoryText.setText(category);
        bmiCategoryText.setTextColor(textColor);
    }
}
