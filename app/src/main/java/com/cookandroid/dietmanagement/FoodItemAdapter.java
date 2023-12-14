package com.cookandroid.dietmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.dietmanagement.CsvReader;

import java.util.List;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.FoodItemViewHolder> {

    private List<CsvReader.FoodItem> foodItemList;
    private OnFoodItemClickListener listener;

    public FoodItemAdapter(List<CsvReader.FoodItem> foodItemList, OnFoodItemClickListener listener) {
        this.foodItemList = foodItemList;
        this.listener = listener;

    }

    public interface OnFoodItemClickListener {
        void onFoodItemClick(CsvReader.FoodItem foodItem);
    }

    @NonNull
    @Override
    public FoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FoodItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodItemViewHolder holder, int position) {
        CsvReader.FoodItem foodItem = foodItemList.get(position);
        holder.foodName.setText(foodItem.getName());
    }

    @Override
    public int getItemCount() {
        return foodItemList.size();
    }

    // ViewHolder에서 클릭 이벤트를 처리합니다.
    public class FoodItemViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        public FoodItemViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onFoodItemClick(foodItemList.get(getAdapterPosition()));
                }
            });
        }

    }

    // 데이터셋을 업데이트하고 RecyclerView를 갱신하는 메소드
    public void updateList(List<CsvReader.FoodItem> newList) {
        foodItemList = newList;
        notifyDataSetChanged(); // 어댑터에 데이터셋이 변경되었다는 것을 알립니다.
    }
}
