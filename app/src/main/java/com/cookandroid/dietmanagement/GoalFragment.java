package com.cookandroid.dietmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GoalFragment extends Fragment {

    private LinearLayout weekLayout;
    private EditText goalInput;
    private TextView progressInfo, progressText;
    private Button setGoalButton;
    private ProgressBar progressBar;
    private Button[] dayButtons = new Button[7]; // 7개의 요일 버튼 배열
    private boolean[] isStamped = new boolean[7]; // 각 버튼의 스탬프 여부를 추적하는 배열
    private int[] buttonIds = {R.id.monday_button, R.id.tuesday_button, R.id.wednesday_button, R.id.thursday_button, R.id.friday_button, R.id.saturday_button, R.id.sunday_button}; // 버튼 ID 배열

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal_setting, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        progressText = view.findViewById(R.id.progress_text);

        goalInput = view.findViewById(R.id.goal_input);
        progressInfo = view.findViewById(R.id.progress_info);
        weekLayout = view.findViewById(R.id.week_layout);
        setGoalButton = view.findViewById(R.id.set_goal_button);

        // 요일 버튼 초기화
        // for문을 사용하여 모든 요일 버튼을 초기화
        for (int i = 0; i < dayButtons.length; i++) {
            final int index = i; // 현재 인덱스를 final 변수로 저장
            dayButtons[i] = view.findViewById(buttonIds[i]);
            dayButtons[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // 버튼 상태 업데이트
                    isStamped[index] = !isStamped[index];
                    // 스탬프 찍기/해제
                    v.setBackgroundResource(isStamped[index] ? R.drawable.stamp : R.drawable.ic_circle_button);

                    updateProgressBar(); // 프로그래스 바 업데이트
                    return true; // 롱클릭 이벤트가 처리되었음을 나타냄
                }
            });
        }

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goalText = goalInput.getText().toString();
                if (!goalText.isEmpty()) {
                    progressInfo.setText("목표: " + goalText); // 진행 상태 정보에 목표 표시
                    progressBar.setVisibility(View.VISIBLE);
                    progressText.setVisibility(View.VISIBLE);
                    weekLayout.setVisibility(View.VISIBLE); // 주간 루틴 버튼 표시
                }
                weekLayout.setVisibility(View.VISIBLE); // 주간 루틴 버튼 표시
            }
        });

        return view;
    }

    private void updateProgressBar() {
        int stampedCount = 0;
        for (boolean stamped : isStamped) {
            if (stamped) stampedCount++;
        }
        int progress = (stampedCount * 100) / 7; // 전체 요일 중에서 몇 퍼센트가 완료되었는지 계산
        progressBar.setProgress(progress);
        progressText.setText("진행률: " + progress + "%"); // 진행률 텍스트 업데이트
    }
}