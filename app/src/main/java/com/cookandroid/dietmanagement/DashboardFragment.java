package com.cookandroid.dietmanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private CalendarView calendarView;
    private EditText diaryEditText;
    private Button actionButton;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
    private String selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        diaryEditText = view.findViewById(R.id.diaryEditText);
        actionButton = view.findViewById(R.id.saveButton);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dateFormat.format(new Date(year - 1900, month, dayOfMonth));
                loadDiary(selectedDate);
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(selectedDate);
            }
        });

        return view;
    }

    private void loadDiary(String date) {
        File diaryFile = new File(getActivity().getFilesDir(), "diary_" + date + ".txt");
        if (diaryFile.exists()) {
            try {
                String content = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    content = new String(Files.readAllBytes(diaryFile.toPath()));
                }
                diaryEditText.setText(content);
                actionButton.setText("수정");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            diaryEditText.setText("");
            actionButton.setText("저장");
        }
    }

    private void saveDiary(String date) {
        try {
            FileOutputStream fos = getActivity().openFileOutput("diary_" + date + ".txt", Context.MODE_PRIVATE);
            fos.write(diaryEditText.getText().toString().getBytes());
            fos.close();
            Toast.makeText(getActivity(), "일기가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "일기 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
