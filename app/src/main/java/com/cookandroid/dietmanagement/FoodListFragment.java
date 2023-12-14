package com.cookandroid.dietmanagement;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodListFragment extends Fragment {

    private FoodItemAdapter adapter;
    private List<CsvReader.FoodItem> foodItemList;

    private FoodItemAdapter.OnFoodItemClickListener onFoodItemClickListener; // 인터페이스 인스턴스

    // 콜백 인터페이스 정의
    public interface OnFoodItemClickListener {
        void onFoodItemClick(CsvReader.FoodItem foodItem);
    }

    // 콜백 인터페이스 설정 메소드
    // 콜백 인터페이스 설정 메소드
    public void setOnFoodItemClickListener(FoodItemAdapter.OnFoodItemClickListener listener) {
        this.onFoodItemClickListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_list, container, false);

        // RecyclerView 설정
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // CsvReader로부터 데이터 로드
        CsvReader csvReader = new CsvReader();
        foodItemList = csvReader.readCsvFromAssets(getContext(), "foodCalorieInfo.csv");

        // Adapter 초기화와 클릭 리스너 설정
        onFoodItemClickListener = new FoodItemAdapter.OnFoodItemClickListener() {
            @Override
            public void onFoodItemClick(CsvReader.FoodItem foodItem) {
                // 선택된 음식의 상세 정보를 FoodLogFragment로 전달하는 로직
                // 예를 들어, 인터페이스를 통해 MainActivity에 정보를 전달할 수 있습니다.
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity)getActivity()).showFoodDetails(foodItem);
                }
            }
        };

        adapter = new FoodItemAdapter(foodItemList, new FoodItemAdapter.OnFoodItemClickListener() {
            @Override
            public void onFoodItemClick(CsvReader.FoodItem foodItem) {
                // 여기서 onFoodItemClickListener 콜백을 호출
                if (onFoodItemClickListener != null) {
                    onFoodItemClickListener.onFoodItemClick(foodItem);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        // 검색 EditText 설정
        EditText searchEditText = view.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    private void filter(String text) {
        List<CsvReader.FoodItem> filteredList = new ArrayList<>();
        for (CsvReader.FoodItem item : foodItemList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.updateList(filteredList);
    }

}